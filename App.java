import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        primaryStage.setTitle("Stock Prediction Launcher");
        primaryStage.setScene(new Scene(root, 80, 60));
        Label stock_name = new Label("Stock Name");
         Button submitButton = new Button("Submit");
        TextField stock_name_input = new TextField();
        VBox vbox = new VBox(stock_name, stock_name_input, submitButton);

        submitButton.setOnAction(e -> {
            String inputText = stock_name_input.getText();
            primaryStage.close();
            runPythonScript(inputText);
        });

        root.getChildren().add(vbox);
        primaryStage.show();
    }

    private void runPythonScript(String inputText) {
            try {
                Process p = Runtime.getRuntime().exec("python kaggle_prediction.py " + inputText);
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String ret = in.readLine();
                System.out.println("value is : " + ret);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
