package Fxml;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LobbyView extends Application {
    private byte[][] maze = new byte[10][8];
    private int max;
    private String title;

    public LobbyView(Lobby lobby) {
        this.maze = lobby.getMaze();
        title = lobby.getName();
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        Canvas canvas = new Canvas(270, 240);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pane root = new Pane();
        drawLines(gc);
        drawTanks(root);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 260, 200, Color.WHITESMOKE);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private void drawLines(GraphicsContext gc) {
        gc.beginPath();
        int cursorX = 0;
        int cursorY = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 7; j >= 0; j--) {
                if (maze[i][j] == 1) {
                    gc.moveTo(cursorX, cursorY);
                    gc.lineTo(cursorX + 30, cursorY);
                    cursorY = cursorY + 30;
                }
                if (maze[i][j] == 3) {
                    gc.moveTo(cursorX, cursorY);
                    gc.lineTo(cursorX, cursorY - 30);
                    gc.moveTo(cursorX, cursorY);
                    gc.lineTo(cursorX + 30, cursorY);
                    cursorY = cursorY + 30;
                }
                if (maze[i][j] == 2) {
                    gc.moveTo(cursorX, cursorY);
                    gc.lineTo(cursorX, cursorY - 30);
                    cursorY = cursorY + 30;
                }
                if (maze[i][j] == 0) {
                    cursorY = cursorY + 30;
                }
                if (j == 0) {
                    cursorY = 0;
                    cursorX = cursorX + 30;
                }
            }
        }
        gc.stroke();
    }

    private void drawTanks(Pane root) {
        for (int i = 0; i < max; i++) {
            Rectangle t = new Rectangle();
            if (i == 0) {
                t = new Rectangle(10, 10, 20, 20);
                t.setFill(Color.RED);
            }
            if (i == 1) {
                t = new Rectangle(240, 10, 20, 20);
                t.setFill(Color.BLUE);
            }
            if (i == 2) {
                t = new Rectangle(240, 180, 20, 20);
                t.setFill(Color.YELLOW);
            }
            if (i == 3) {
                t = new Rectangle(10, 180, 20, 20);
                t.setFill(Color.GREEN);
            }
            root.getChildren().add(t);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//public class LobbyView extends Application {
//    private int[][] a = new int[8][11];
//    private int max;
//    private String title;
//
//    public LobbyView(Lobby lobby) {
//        this.a = lobby.getA();
//        max = lobby.getMax();
//        title = lobby.getName();
//    }
//
//    @Override
//    public void start(Stage stage) {
//        initUI(stage);
//    }
//
//    private void initUI(Stage stage) {
//
//        Canvas canvas = new Canvas(290, 240);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
////        for (int i = 0; i < 8; i++) {
////            for (int j = 0; j < 11; j++) {
////                a[i][j] = 0;
////            }
////        }
////        a[0][2] = 10;
////        a[1][2] = 10;
////        a[7][4] = 10;
////        a[7][7] = 10;
////        a[1][5] = 10;
////        a[1][7] = 1;
////        a[1][8] = 1;
////        a[2][0] = 1;
////        a[2][1] = 1;
////        a[2][2] = 1;
////        a[2][7] = 10;
////        a[3][2] = 1;
////        a[3][3] = 1;
////        a[3][5] = 11;
////        a[3][7] = 11;
////        a[3][8] = 1;
////        a[4][4] = 10;
////        a[4][9] = 1;
////        a[5][1] = 1;
////        a[5][4] = 10;
////        a[5][8] = 1;
////        a[5][9] = 10;
////        a[6][5] = 10;
////        a[7][5] = 10;
////        a[7][9] = 10;
//        Pane root = new Pane();
//        drawLines(gc);
//        drawTanks(root);
//        root.getChildren().add(canvas);
//        Scene scene = new Scene(root, 280, 230, Color.WHITESMOKE);
//        stage.setResizable(false);
//        stage.setTitle("fx.Lobby " + title);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    private void drawLines(GraphicsContext gc) {
//        gc.beginPath();
//        int cursorX = 0;
//        int cursorY = 30;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 11; j++) {
//                if (a[i][j] == 1) {
//                    gc.moveTo(cursorX, cursorY);
//                    gc.lineTo(cursorX + 30, cursorY);
//                    cursorX = cursorX + 30;
//                }
//                if (a[i][j] == 11) {
//                    gc.moveTo(cursorX, cursorY);
//                    gc.lineTo(cursorX, cursorY - 30);
//                    gc.moveTo(cursorX, cursorY);
//                    gc.lineTo(cursorX + 30, cursorY);
//                    cursorX = cursorX + 30;
//                }
//                if (a[i][j] == 10) {
//                    gc.moveTo(cursorX, cursorY);
//                    gc.lineTo(cursorX, cursorY - 30);
//                    cursorX = cursorX + 30;
//                }
//                if (a[i][j] == 0) {
//                    cursorX = cursorX + 30;
//                }
//                if (j == 10) {
//                    cursorX = 0;
//                    cursorY = cursorY + 30;
//                }
//            }
//        }
//        gc.stroke();
//    }
//
//    private void drawTanks(Pane root) {
//        for (int i = 0; i < max; i++) {
//            Rectangle t = new Rectangle();
//            if (i == 0) {
//                t = new Rectangle(10, 10, 20, 20);
//                t.setFill(Color.RED);
//            }
//            if (i == 1) {
//                t = new Rectangle(270, 10, 20, 20);
//                t.setFill(Color.BLUE);
//            }
//            if (i == 2) {
//                t = new Rectangle(270, 210, 20, 20);
//                t.setFill(Color.YELLOW);
//            }
//            if (i == 3) {
//                t = new Rectangle(10, 210, 20, 20);
//                t.setFill(Color.GREEN);
//            }
//            root.getChildren().add(t);
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}