import java.util.List;

public class Question
{
    private String course;
    private String topic;
    private String difficulty;
    private String questionText;
    private List<String> hints;
    private String solution;

    public Question() {}

    public String getCourse() { return course; }
    public String getTopic() { return topic; }
    public String getDifficulty() { return difficulty; }
    public String getQuestionText() { return questionText; }
    public List<String> getHints() { return hints; }
    public String getSolution() { return solution; }

    public void setCourse(String course) { this.course = course; }
    public void setTopic(String topic) { this.topic = topic; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setHints(List<String> hints) { this.hints = hints; }
    public void setSolution(String solution) { this.solution = solution; }
}
