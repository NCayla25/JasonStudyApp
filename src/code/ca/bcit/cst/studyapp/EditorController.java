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
import java.util.Optional;

/**
 * Controller for the question editor interface.
 *
 * @author Nicholas Cayla
 * @author Samien Munwar
 * @version 1.0
 */
public class EditorController
{
    public ComboBox<String> courseDropdown;
    public TextField topicField;
    public TextArea questionArea;
    public TextArea solutionArea;

    @FXML
    public TextField hintInputField;

    public ListView<String> hintListView;

    public Button addHintButton;
    public Button removeHintButton;
    public Button saveButton;
    public Button deleteButton;

    public ComboBox<Question> questionListView;

    private ObservableList<String> hintList;
    private List<Question> questions;
    private int selectedIndex = -1;

    private static final String FILE = System.getProperty("user.dir") +
                                        "/questions.json";

    /*
     * Private constructor to prevent instantiation.
     */
    private EditorController()
    {

    }

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

        saveButton.setOnAction(e -> saveQuestion());

        addHintButton.setOnAction(e -> addHint());

        removeHintButton.setOnAction(e -> removeHint());

        questionListView.setItems(FXCollections.observableArrayList(questions));
        questionListView.
                setCellFactory(lv ->
                                       new ListCell<>() {
                                            @Override
                                            public void updateItem(final Question q,
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
                                        });

        questionListView.
                getSelectionModel().
                selectedIndexProperty().
                addListener((obs,
                             oldIndex,
                             newIndex) ->
                            {
                                if (newIndex != null &&
                                        newIndex.intValue() >= 0)
                                {
                                    selectedIndex = newIndex.intValue();
                                    Question q = questions.get(selectedIndex);

                                    courseDropdown.setValue(q.getCourse());
                                    topicField.setText(q.getTopic());
                                    questionArea.setText(q.getQuestionText());
                                    solutionArea.setText(q.getSolution());

                                    hintList.setAll(q.getHints());
                                }
                            });

        deleteButton.setDisable(true);

        questionListView.
                getSelectionModel().
                selectedIndexProperty().
                addListener((obs,
                             oldVal,
                             newVal) ->
                            {
                                deleteButton.setDisable(newVal.intValue() < 0);
                            });
    }

    /*
     * Saves the current question.
     * If a question is selected, it updates that question;
     * otherwise, it adds a new question to the list.
     * After saving, it updates the question list view
     * and clears the input fields.
     */
    private void saveQuestion()
    {
        final Question q;
        q = new Question();

        q.setCourse(courseDropdown.getValue());
        q.setTopic(topicField.getText());
        q.setQuestionText(questionArea.getText());
        q.setHints(new ArrayList<>(hintList));
        q.setSolution(solutionArea.getText());

        if (selectedIndex >= 0)
        {
            questions.set(selectedIndex, q);
        }
        else
        {
            questions.add(q);
        }

        QuestionSaver.save(questions, FILE);

        clearFields();

        selectedIndex = -1;
        questionListView.getSelectionModel().clearSelection();
        questionListView.setItems(FXCollections.observableArrayList(questions));
    }

    /**
     * Deletes the currently selected question after confirming with the user.
     */
    public void deleteQuestion()
    {
        if (selectedIndex < 0)
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
            selectedIndex = -1;

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

        if (selectedIndex >= 0)
        {
            hintList.remove(selectedIndex);
        }
    }

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
                add(getClass().
                            getResource("/style.css").
                            toExternalForm());

        stage = (Stage) saveButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
