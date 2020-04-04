package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        View view = new View();
        Scene scene = new Scene(view, 310, 410);
        primaryStage.setMinHeight(410);
        primaryStage.setMaxHeight(410);
        primaryStage.setMinWidth(310);
        primaryStage.setMaxWidth(310);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
