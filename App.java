import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * JavaFX App
 * This is the main class that launches the GUI and runs the python script
 */
public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * Launches the GUI with a starting screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        primaryStage.setTitle("Stock Prediction Launcher");
        Scene baseScene = new Scene(root, 800, 500);
        primaryStage.setScene(baseScene);
        primaryStage.setResizable(true);
        Label stock_name = new Label("Stock Name");
        Button submitButton = new Button("Submit");
        TextField stock_name_input = new TextField();
        VBox vbox = new VBox(stock_name, stock_name_input, submitButton);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        submitButton.setOnAction(e -> { // When the submit button is pressed, the python script is run
            String inputText = stock_name_input.getText();
            primaryStage.close();
            loading_screen(inputText);
            Thread thread = new Thread(() -> {
                runPythonScript(inputText);
            });
            thread.start();
        });

        root.getChildren().add(vbox);
        primaryStage.show();
    }

    /**
     * Creates a loading screen while the python script is running
     * @param inputText the stock name
     */
    private void loading_screen(String inputText) {
        StackPane root = new StackPane();
        Stage loading_screen = new Stage();
        loading_screen.setTitle("Loading...");
        Label loading_label = new Label("Loading...");
        Label stock_name = new Label(inputText + " Prediction");
        ProgressIndicator progressIndicator = new ProgressIndicator(); // Doesn't update with Python progress
        VBox vbox = new VBox(loading_label, progressIndicator, stock_name);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        root.getChildren().add(vbox);
        loading_screen.setScene(new Scene(root, 800, 500));
        loading_screen.show();
    }

    /**
     * Runs the python script
     * @param inputText the stock name
     */
    private void runPythonScript(String inputText) {
        try {
            Process p = Runtime.getRuntime().exec("python Run_Prediction.py " + inputText);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = in.readLine();
            while (ret != null) {
                System.out.println(ret);
                ret = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
