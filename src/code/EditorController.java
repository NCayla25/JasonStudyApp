import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private final String FILE = System.getProperty("user.dir") + "/questions.json";

    public void initialize()
    {
        // Load existing questions
        questions = QuestionLoader.load(FILE);

        // Setup dropdowns
        courseDropdown.getItems().addAll("Java", "Computer Architecture");

        // Bind hints list
        hintList = FXCollections.observableArrayList();
        hintListView.setItems(hintList);

        // Button actions
        saveButton.setOnAction(e -> saveQuestion());

        addHintButton.setOnAction(e -> addHint());

        removeHintButton.setOnAction(e -> removeHint());

        questionListView.setItems(FXCollections.observableArrayList(questions));
        questionListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Question q, boolean empty)
            {
                super.updateItem(q, empty);
                if (empty || q == null)
                {
                    setText(null);
                }
                else
                {
                    setText(q.getCourse() + " - " + q.getTopic());
                }
            }
        });

        questionListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if (newIndex != null && newIndex.intValue() >= 0)
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

        questionListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            deleteButton.setDisable(newVal.intValue() < 0);
        });
    }

    private void saveQuestion()
    {
        Question q;
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

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            questions.remove(selectedIndex);

            QuestionSaver.save(questions, FILE);

            clearFields();
            selectedIndex = -1;

            questionListView.setItems(FXCollections.observableArrayList(questions));
        }
    }

    private void clearFields()
    {
        topicField.clear();
        questionArea.clear();
        solutionArea.clear();
        hintList.clear();
    }

    public void addHint()
    {
        final String text;
        text = hintInputField.getText();

        if (text != null && !text.isEmpty())
        {
            hintList.add(text);
            hintInputField.clear();
        }
    }

    public void removeHint()
    {
        int selectedIndex = hintListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0)
        {
            hintList.remove(selectedIndex);
        }
    }

    @FXML
    public void switchToStudy()
        throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/study.fxml"));
        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
