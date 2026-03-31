import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionLoader
{
    public static List<Question> load (String filename)
    {
        try
        {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Question>>(){}.getType();

            return gson.fromJson(new FileReader(filename), listType);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No file found, starting fresh.");
            return new ArrayList<>();
        }
    }
}
