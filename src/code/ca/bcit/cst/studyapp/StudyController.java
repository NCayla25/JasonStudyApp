package ca.bcit.cst.studyapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Controller for the study interface of the CST Study App.
 * It manages the display of questions, hints, and solutions based on user interactions.
 * The controller loads questions from a JSON file, allows filtering by course,
 * and provides functionality to show hints and solutions for the current question.
 *
 * @author Nicholas Cayla
 * @author Samien Munwar
 * @version 1.0
 */
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

    /**
     * Initializes the study interface by loading questions
     * from a JSON file and setting up the course filter dropdown.
     */
    public void initialize()
    {
        courseFilterDropdown.getItems().addAll("All",
                                               "Java",
                                               "Computer Architecture");
        courseFilterDropdown.setValue("All");

        courseFilterDropdown.setOnAction(e -> loadNewQuestion());

        questions = QuestionLoader.load(FILE);
        loadNewQuestion();
    }

    /*
     * Loads a new question based on the selected course filter.
     * If there are no questions available, it displays an appropriate message.
     */
    private void loadNewQuestion()
    {
        if (questions.isEmpty())
        {
            questionLabel.setText("No questions available.");
            return;
        }

        final String selectedCourse;
        final List<Question> filtered;
        final Random rand;

        selectedCourse = courseFilterDropdown.getValue();
        rand = new Random();

        if (!selectedCourse.equals("All"))
        {
            filtered = questions.stream()
                    .filter(q -> q.getCourse().equals(selectedCourse))
                    .toList();
        }
        else
        {
            filtered = questions;
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

    /**
     * Displays the next hint for the current question.
     * If there are no more hints available,
     * it informs the user that there are no more hints.
     */
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

    /**
     * Displays the solution for the current question in the solution area.
     */
    public void showSolution()
    {
        solutionArea.setText(currentQuestion.getSolution());
    }

    /**
     * Loads the next question based on the current course filter.
     */
    public void nextQuestion()
    {
        loadNewQuestion();
    }

    /**
     * Switches to the editor interface by loading the editor
     * FXML file and setting it as the current scene.
     *
     * @throws Exception if there is an error loading the
     *                   editor FXML file or setting the scene.
     */
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

        scene.
                getStylesheets().
                add(Objects.
                            requireNonNull(getClass().
                                           getResource("/style.css")).
                            toExternalForm());

        stage.setScene(scene);
    }
}
