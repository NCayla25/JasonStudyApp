package ca.bcit.cst.studyapp;

import java.util.List;

/**
 * Represents a question with its associated course,
 * topic, question text, hints, and solution.
 * This class provides getters and setters for each of its fields,
 * allowing for encapsulation and data management.
 *
 * @author Nicholas Cayla
 * @author Samien Munwar
 * @version 1.2
 */
public class Question
{
    private final String course;
    private final String topic;
    private final String questionText;
    private final List<String> hints;
    private final String solution;

    /**
     * Constructor for the Question.
     *
     * @param course the course name as a String
     * @param topic the topic of the course as a String
     * @param questionText the question text as a String
     * @param hints the list of hints for each question
     * @param solution the solution of the question as a String
     */
    public Question(final String course,
                    final String topic,
                    final String questionText,
                    final List<String> hints,
                    final String solution)
    {
        this.course = course;
        this.topic = topic;
        this.questionText = questionText;
        this.hints = hints;
        this.solution = solution;
    }

    /**
     * Getter for course
     *
     * @return the course associated with this question
     */
    public String getCourse()
    {
        return course;
    }

    /**
     * Getter for topic
     *
     * @return the topic associated with this question
     */
    public String getTopic()
    {
        return topic;
    }

    /**
     * Getter for question text
     *
     * @return the text of the question
     */
    public String getQuestionText()
    {
        return questionText;
    }

    /**
     * Getter for hints
     *
     * @return a list of hints for this question
     */
    public List<String> getHints()
    {
        return hints;
    }

    /**
     * Getter for solution
     *
     * @return the solution to the question
     */
    public String getSolution()
    {
        return solution;
    }
}
