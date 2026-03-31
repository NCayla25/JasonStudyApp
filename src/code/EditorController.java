import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditorController
{
    public ComboBox<String> courseDropdown;
    public ComboBox<String> difficultyDropdown;
    public TextField topicField;
    public TextArea questionArea;
    public TextArea solutionArea;
    @FXML
    public TextField hintInputField;
    public ListView<String> hintListView;
    public Button addHintButton;
    public Button removeHintButton;
    public Button saveButton;

    private ObservableList<String> hintList;
    private List<Question> questions;

    private final String FILE = System.getProperty("user.dir") + "/questions.json";

    public void initialize()
    {
        // Load existing questions
        questions = QuestionLoader.load(FILE);

        // Setup dropdowns
        courseDropdown.getItems().addAll("Java", "Computer Architecture");
        difficultyDropdown.getItems().addAll("Easy", "Medium", "Hard");

        // Bind hints list
        hintList = FXCollections.observableArrayList();
        hintListView.setItems(hintList);

        // Button actions
        saveButton.setOnAction(e -> saveQuestion());

        addHintButton.setOnAction(e -> addHint());

        removeHintButton.setOnAction(e -> removeHint());
    }

    private void saveQuestion()
    {
        Question q = new Question();

        q.setCourse(courseDropdown.getValue());
        q.setTopic(topicField.getText());
        q.setDifficulty(difficultyDropdown.getValue());
        q.setQuestionText(questionArea.getText());
        q.setHints(new ArrayList<>(hintList));
        q.setSolution(solutionArea.getText());

        questions.add(q);

        QuestionSaver.save(questions, FILE);

        clearFields();
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
