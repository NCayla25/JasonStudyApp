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
    private String course;
    private String topic;
    private String questionText;
    private List<String> hints;
    private String solution;

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

    /**
     * Setter for course
     *
     * @param course the course to set for this question
     */
    public void setCourse(final String course)
    {
        this.course = course;
    }

    /**
     * Setter for topic
     *
     * @param topic the topic to set for this question
     */
    public void setTopic(final String topic)
    {
        this.topic = topic;
    }

    /**
     * Setter for question text
     *
     * @param questionText the text of the question to set
     */
    public void setQuestionText(final String questionText)
    {
        this.questionText = questionText;
    }

    /**
     * Setter for hints
     *
     * @param hints a list of hints to set for this question
     */
    public void setHints(final List<String> hints)
    {
        this.hints = hints;
    }

    /**
     * Setter for solution
     *
     * @param solution the solution to set for this question
     */
    public void setSolution(final String solution)
    {
        this.solution = solution;
    }
}
