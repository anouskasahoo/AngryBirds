package com.ByteMe;

import java.io.*;
import java.util.Map;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    private transient GameState loadedGame = null;

    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
        //this.loadedGame = null;

        File savedFile = new File("saved.ser");
        if (savedFile.exists() && savedFile.length() > 0) {
            try (FileInputStream fileIn = new FileInputStream("saved.ser");
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                GameState gameState = (GameState) in.readObject();
                this.setLoadedGame(gameState);
                System.out.println("Game state loaded from saved.ser");
                System.out.println(gameState.getLevel().levelNumber);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading game: " + e.getMessage());
            }
        }
    }
    public Player(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Map<Level, Integer> getProgressLog() {
        return progressLog;
    }

    public void setProgressLog(Map<Level, Integer> progressLog) {
        this.progressLog = progressLog;
    }

    Level currentLevel;
    int totalScore;
    Map<Level, Integer> progressLog;

    public GameState getLoadedGame() {
        return loadedGame;
    }

    public void setLoadedGame(GameState loadedGame) {
        this.loadedGame = loadedGame;
    }
}
