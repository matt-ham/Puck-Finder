package handin.projects.assignment3_276.model;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

import handin.projects.assignment3_276.Options;
import handin.projects.assignment3_276.R;
/*
Game variables and functions for values in the game
 */
public class GameLogic {


    private int gameCol;
    private int gameRow;
    private int numPucks;
    private int puckFoundCount;

    private int scanCounter;


    private int[][] gameGrid = new int[getGameRow()][getGameCol()];

    //singleton

    private static GameLogic instance;

    private GameLogic(){

    }
    public static GameLogic getInstance(){
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void resetPuckFoundCount(){
        puckFoundCount = 0;
    }


    public int getGameCol() {
        return gameCol;
    }

    public void setGameCol(int gameCol) {
        this.gameCol = gameCol;
    }

    public int getGameRow() {
        return gameRow;
    }

    public void setGameRow(int gameRow) {
        this.gameRow = gameRow;
    }

    public int getNumPucks() {
        return numPucks;
    }

    public void setNumPucks(int numPucks) {
        this.numPucks = numPucks;
    }

    public int getPuckFoundCount() {
        return puckFoundCount;
    }

    public void updatePuckFoundCount(){
        puckFoundCount++;
    }

    public void randomizePuckLocation(){
        gameGrid = new int[getGameRow()][getGameCol()];
        int numPucksToPlace = numPucks;
        int row;
        int col;
        Random random = new Random();
        while (numPucksToPlace>0){
            row = random.nextInt(gameRow);
            col = random.nextInt(gameCol);
            if(gameGrid[row][col] != 1){
                gameGrid[row][col] = 1;
                numPucksToPlace--;
             }

        }
    }

    public boolean checkPuckLocation(int row, int col){
        if(gameGrid[row][col] == 1){
            gameGrid[row][col] = 0; // =0 so it doesn't get 'found' and counted twice, can be now scanned
            updatePuckFoundCount();
            return true;
        }
        else{
            gameGrid[row][col] = 2;
            return false;
        }
    }

    public boolean buttonHasBeenChecked(int row, int col){
        return gameGrid[row][col] == 2;
    }

    public int scanForPucks(int row, int col){
        int pucksScanned = 0;

        // row
        for (int c = 0; c < gameCol; c++){
            if (gameGrid[row][c] == 1){
                pucksScanned++;
            }
        }

        // col
        for (int r = 0; r < gameRow; r++){
            if (gameGrid[r][col] == 1){
                pucksScanned++;
            }
        }
        return pucksScanned;
    }

    public int getScanCounter() {
        return scanCounter;
    }
    public void incrementScanCount(){
        scanCounter++;
    }
    public void resetScanCounter(){
        scanCounter = 0;
    }

}
