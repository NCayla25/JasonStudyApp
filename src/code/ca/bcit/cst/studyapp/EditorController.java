package ca.bcit.cst.studyapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for the question editor interface.
 *
 * @author Nicholas Cayla
 * @author Samien Munwar
 * @version 1.2
 */
public class EditorController
{
    @FXML
    private ComboBox<String> courseDropdown;
    @FXML
    private TextField topicField;
    @FXML
    private TextArea questionArea;
    @FXML
    private TextArea solutionArea;
    @FXML
    private TextField hintInputField;

    @FXML
    private ListView<String> hintListView;

    @FXML
    private Button addHintButton;
    @FXML
    private Button removeHintButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox<Question> questionListView;

    private ObservableList<String> hintList;
    private List<Question> questions;
    private int selectedIndex = RESET_QUESTION_INDEX;

    private static final String FILE = System.getProperty("user.dir") +
            "/questions.json";
    private static final int FIRST_QUESTION_INDEX = 0;
    private static final int RESET_QUESTION_INDEX = -1;

    /**
     * Initializes the editor by loading existing questions,
     * setting up dropdowns, and configuring button actions.
     */
    public void initialize()
    {
        questions = QuestionLoader.load(FILE);

        courseDropdown.
                getItems().
                addAll("Java",
                       "Computer Architecture");

        hintList = FXCollections.observableArrayList();
        hintListView.setItems(hintList);

        questionListView.setItems(FXCollections.observableArrayList(questions));
        questionListView.
                setCellFactory(lv -> createQuestionCell());

        questionListView.
                getSelectionModel().
                selectedIndexProperty().
                addListener((obs,
                             oldVal,
                             newVal) ->
                            {
                                final int index = newVal.intValue();
                                deleteButton.setDisable(index < FIRST_QUESTION_INDEX);

                                if (index >= FIRST_QUESTION_INDEX &&
                                        index < questions.size())
                                {
                                    selectedIndex = index;
                                    final Question q = questions.get(selectedIndex);
                                    courseDropdown.setValue(q.getCourse());
                                    topicField.setText(q.getTopic());
                                    questionArea.setText(q.getQuestionText());
                                    solutionArea.setText(q.getSolution());
                                    hintList.setAll(q.getHints());
                                }
                            });

        deleteButton.setDisable(true);
    }

    /*
     * Creates a custom ListCell for displaying questions in the question list view.
     *
     * @return a ListCell that formats each question as
     * "Course - Topic" for display in the list view
     */
    private ListCell<Question> createQuestionCell()
    {
        return new ListCell<>()
        {
            @Override
            protected void updateItem(final Question q,
                                      final boolean empty)
            {
                super.updateItem(q, empty);
                if (empty || q == null)
                {
                    setText(null);
                }
                else
                {
                    setText(q.getCourse() +
                                    " - " +
                                    q.getTopic());
                }
            }
        };
    }

    /*
     * Saves the current question.
     * If a question is selected, it updates that question;
     * otherwise, it adds a new question to the list.
     * After saving, it updates the question list view
     * and clears the input fields.
     */
    @FXML
    private void saveQuestion()
    {
        final Question q;
        q = new Question(courseDropdown.getValue(),
                topicField.getText(),
                questionArea.getText(),
                new ArrayList<>(hintList),
                solutionArea.getText());

        if (selectedIndex >= FIRST_QUESTION_INDEX &&
                selectedIndex < questions.size())
        {
            questions.set(selectedIndex, q);
        }
        else
        {
            questions.add(q);
        }

        QuestionSaver.save(questions, FILE);

        clearFields();

        selectedIndex = RESET_QUESTION_INDEX;
        questionListView.getSelectionModel().clearSelection();
        questionListView.setItems(FXCollections.observableArrayList(questions));
    }

    /**
     * Deletes the currently selected question after confirming with the user.
     */
    public void deleteQuestion()
    {
        if (selectedIndex < FIRST_QUESTION_INDEX ||
                selectedIndex >= questions.size())
        {
            return;
        }

        final Alert alert;
        final Optional<ButtonType> result;

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Question");
        alert.setHeaderText("Are you sure you want to delete this question?");
        alert.setContentText("This action cannot be undone.");

        result = alert.showAndWait();

        if (result.isPresent() &&
                result.get() == ButtonType.OK)
        {
            questions.remove(selectedIndex);

            QuestionSaver.save(questions, FILE);

            clearFields();
            selectedIndex = RESET_QUESTION_INDEX;

            questionListView.
                    setItems(FXCollections.
                                     observableArrayList(questions));
        }
    }

    /*
     * Clears all input fields and resets the hint list.
     * This is called after saving or deleting a question
     * to prepare the editor for a new entry.
     */
    private void clearFields()
    {
        topicField.clear();
        questionArea.clear();
        solutionArea.clear();
        hintList.clear();
    }

    /**
     * Adds a new hint to the hint list.
     */
    public void addHint()
    {
        final String text;
        text = hintInputField.getText();

        if (text != null &&
                !text.isEmpty())
        {
            hintList.add(text);
            hintInputField.clear();
        }
    }

    /**
     * Removes the selected hint from the hint list.
     */
    public void removeHint()
    {
        final int selectedIndex;
        selectedIndex = hintListView.
                getSelectionModel().
                getSelectedIndex();

        if (selectedIndex >= FIRST_QUESTION_INDEX &&
                selectedIndex < hintList.size())
        {
            hintList.remove(selectedIndex);
        }
    }

    /**
     * Switches to the study interface by loading the study FXML layout
     * and setting it as the current scene.
     *
     * @throws Exception if there is an error loading the
     *                   FXML file or setting up the scene
     */
    @FXML
    public void switchToStudy()
            throws Exception
    {
        final FXMLLoader loader;
        final Scene scene;
        final Stage stage;

        loader = new FXMLLoader(getClass().getResource("/study.fxml"));
        scene = new Scene(loader.load());

        scene.
                getStylesheets().
                add(Objects.requireNonNull(getClass().
                                                   getResource("/style.css")).
                            toExternalForm());

        stage = (Stage) saveButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
