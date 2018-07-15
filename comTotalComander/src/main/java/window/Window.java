package window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Window extends Application {

    private static final int WEIGHT = 1120;
    private static final int HEIGHT = 630;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Total Commander");
        primaryStage.getIcons().add(new Image("icon.jpg"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        primaryStage.setScene(new Scene(root, WEIGHT, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
