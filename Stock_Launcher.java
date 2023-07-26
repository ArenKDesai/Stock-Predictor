import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.Scene;

public class Stock_Launcher extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane firstPane = new BorderPane();
        Scene scene = new Scene(firstPane, 800, 600);
    }

    public static void main(String[] args){
        Application.launch();
    }
    
}