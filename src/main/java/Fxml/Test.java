package Fxml;


import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Test extends Application {

    private AnchorPane anchorPane = new AnchorPane();
    public static Test test;

    public static void initTest() throws Exception {
        if (test != null) throw new Exception("windows");
        test = new Test();

    }

    public void getElement(){
        anchorPane.getChildren().add(new Label("sdfsdf"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initTest();
        primaryStage.setTitle("Список");
        Scene scene = new Scene(anchorPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
