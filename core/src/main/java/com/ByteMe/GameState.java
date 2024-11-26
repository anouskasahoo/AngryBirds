package com.ByteMe;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for version control

    private Level level;
    private int score;
    private Player player;

    public GameState(Level level, int score, Player player) {
        this.level = level;
        this.score = score;
        this.player = player;
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
