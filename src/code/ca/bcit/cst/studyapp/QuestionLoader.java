package ca.bcit.cst.studyapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for loading questions from a JSON file.
 * It uses Gson to parse the JSON and convert it into a list of Question objects.
 * If the file is not found or is invalid,
 * it returns an empty list and prints an error message.
 *
 * @author Nichola Cayla
 * @author Samien Munwar
 * @version 1.0
 */
public class QuestionLoader
{

    /**
     * Loads questions from a JSON file. If the file does not exist or is invalid,
     *
     * @param filename the path to the JSON file containing the questions
     * @return a list of questions loaded from the file,
     *         or an empty list if the file is not found or invalid
     */
    public static List<Question> load(final String filename)
    {
        try
        {
            final Gson gson;
            final Type listType;

            gson = new Gson();
            listType = new TypeToken<List<Question>>(){}.getType();

            return gson.fromJson(new FileReader(filename),
                                 listType);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            System.out.println("No file found, starting fresh.");
            return new ArrayList<>();
        }
    }
}
