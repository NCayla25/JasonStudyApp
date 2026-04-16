package ca.bcit.cst.studyapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the CST Study App.
 *
 * @author Nicholas Cayla
 * @author Samien Munwar
 * @version 1.2
 */
public class MainApp
        extends Application
{
    private static final int MINIMUM_WIDTH = 800;
    private static final int MINIMUM_HEIGHT = 600;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    /**
     * Initializes the JavaFX application by loading the main
     * FXML layout and setting up the primary stage.
     *
     * @param primaryStage The primary stage for this application,
     *                     onto which the application scene can be set.
     * @throws Exception If an error occurs during loading of the FXML file
     *                   or setting up the scene.
     */
    @Override
    public void start(final Stage primaryStage)
            throws Exception
    {
        final FXMLLoader loader;
        final Scene scene;

        loader = new FXMLLoader(getClass().getResource("/editor.fxml"));
        scene = new Scene(loader.load());

        scene.
                getStylesheets().
                add(getClass().
                            getResource("/style.css").
                            toExternalForm());

        primaryStage.setMinWidth(MINIMUM_WIDTH);
        primaryStage.setMinHeight(MINIMUM_HEIGHT);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        primaryStage.setTitle("CST term 2 Study App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(final String[] args)
    {
        launch(args);
    }
}
