package Fxml;

import Servers.MenuServer;

public class Lobby {
    private String name;
    private int currentCount = 1;
    private byte[][] maze = new byte[10][8];

    public Lobby(String name) {
        this.name = name;
    }

    public void setCurrentCount() {
        this.currentCount++;
    }

    public byte[][] getMaze() {
        return MenuServer.getMaze();
    }
    //    public Lobby(String name, int currentCount, int q) {
//        this.name = name;
//        this.currentCount = currentCount;
//
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 11; j++) {
//                a[i][j] = 0;
//            }
//        }
//        if (q == 0) {
//            a[0][2] = 10;
//            a[1][2] = 10;
//            a[7][4] = 10;
//            a[7][7] = 10;
//            a[1][5] = 10;
//            a[1][7] = 1;
//            a[1][8] = 1;
//            a[2][0] = 1;
//            a[2][1] = 1;
//            a[2][2] = 1;
//            a[2][7] = 10;
//            a[3][2] = 1;
//            a[3][3] = 1;
//            a[3][5] = 11;
//            a[3][7] = 11;
//            a[3][8] = 1;
//            a[4][4] = 10;
//            a[4][9] = 1;
//            a[5][1] = 1;
//            a[5][4] = 10;
//            a[5][8] = 1;
//            a[5][9] = 10;
//            a[6][5] = 10;
//            a[7][5] = 10;
//            a[7][9] = 10;
//        }
//    }

    public String getName() {
        return name;
    }

    public int getCurrentCount() {
        return currentCount;
    }


}