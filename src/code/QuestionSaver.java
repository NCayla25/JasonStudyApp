import com.google.gson.Gson;
import java.io.FileWriter;
import java.util.List;

public class QuestionSaver
{
    public static void save(List<Question> questions, String filename)
    {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(filename);

            gson.toJson(questions, writer);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
