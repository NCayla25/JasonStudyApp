import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application
{
    @Override
    public void start(Stage primaryStage)
            throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor.fxml"));
        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);

        primaryStage.setTitle("COMP 2522 & 2721 Study App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void main(String[] args)
    {
        launch(args);
    }
}
