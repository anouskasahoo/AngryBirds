package com.ByteMe;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for version control

    private Level level;
    private int score;
    private Player player;
    private ArrayList<Bird> birds;
    private ArrayList<Pig> pigs;
    private ArrayList<Obstacle> obstacles;

    public GameState(Level level, int score, Player player, ArrayList<Bird> birds, ArrayList<Pig> pigs, ArrayList<Obstacle> obstacles) {
        this.level = level;
        this.score = score;
        this.player = player;
        this.birds = birds;
        this.pigs = pigs;
        this.obstacles = obstacles;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}
