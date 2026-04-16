package ca.bcit.cst.studyapp;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.util.List;

/**
 * Utility class for saving a list of questions to a JSON file.
 * It uses Gson to convert the list of Question objects into JSON format
 * and writes it to the specified file. If an error occurs during the saving process,
 * it prints the stack trace of the exception.
 *
 * @author Nicholas Cayla
 * @author Samien Munwar
 * @version 1.2
 */
public class QuestionSaver
{
    /**
     * Saves a list of questions to a JSON file.
     * It converts the list of Question objects into
     * JSON format using Gson and writes it to the specified file.
     *
     * @param questions the list of questions to be saved
     * @param filename the path to the JSON file where the questions will be saved
     */
    public static void save(final List<Question> questions,
                            final String filename)
    {
        try (final FileWriter writer = new FileWriter(filename))
        {
            final Gson gson;
            gson = new Gson();

            gson.toJson(questions, writer);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
