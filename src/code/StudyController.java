import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class StudyController
{
    public Label questionLabel;
    public Label hintLabel;
    public TextArea solutionArea;

    private List<Question> questions;
    private Question currentQuestion;
    public ComboBox<String> courseFilterDropdown;
    private int hintIndex;

    private final String FILE = System.getProperty("user.dir") + "/questions.json";

    public void initialize()
    {
        courseFilterDropdown.getItems().addAll("All", "Java", "Computer Architecture");
        courseFilterDropdown.setValue("All");

        courseFilterDropdown.setOnAction(e -> loadNewQuestion());

        questions = QuestionLoader.load(FILE);
        loadNewQuestion();
    }

    private void loadNewQuestion()
    {
        if (questions.isEmpty())
        {
            questionLabel.setText("No questions available.");
            return;
        }

        final String selectedCourse;
        List<Question> filtered;
        final Random rand;

        selectedCourse = courseFilterDropdown.getValue();
        filtered = questions;
        rand = new Random();

        if (!selectedCourse.equals("All"))
        {
            filtered = questions.stream()
                    .filter(q -> q.getCourse().equals(selectedCourse))
                    .toList();
        }

        if (filtered.isEmpty())
        {
            questionLabel.setText("No questions for this course.");
            return;
        }

        currentQuestion = filtered.get(rand.nextInt(filtered.size()));

        questionLabel.setText(currentQuestion.getQuestionText());
        hintLabel.setText("");
        solutionArea.setText("");

        hintIndex = 0;
    }

    public void showHint()
    {
        if (currentQuestion.getHints() == null) return;

        if (hintIndex < currentQuestion.getHints().size())
        {
            hintLabel.setText(currentQuestion.getHints().get(hintIndex));
            hintIndex++;
        }
        else
        {
            hintLabel.setText("No more hints.");
        }
    }

    public void showSolution()
    {
        solutionArea.setText(currentQuestion.getSolution());
    }

    public void nextQuestion()
    {
        loadNewQuestion();
    }

    @FXML
    public void switchToEditor()
        throws Exception
    {
        final FXMLLoader loader;
        final Scene scene;
        final Stage stage;

        loader = new FXMLLoader(getClass().getResource("/editor.fxml"));
        scene = new Scene(loader.load());
        stage = (Stage) questionLabel.getScene().getWindow();

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }
}
