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
    private int hintIndex;

    private final String FILE = System.getProperty("user.dir") + "/questions.json";

    public void initialize()
    {
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

        Random rand = new Random();
        currentQuestion = questions.get(rand.nextInt(questions.size()));

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor.fxml"));
        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        Stage stage = (Stage) questionLabel.getScene().getWindow();
        stage.setScene(scene);
    }
}
