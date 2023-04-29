package com.example.mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    private static Stage stage;
    @Override
    public void start(Stage stage) {
        HelloApplication.stage=stage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Media Player");
            stage.setScene(scene);
            stage.show();

            String style = getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(style);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}