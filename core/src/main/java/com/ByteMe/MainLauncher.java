package com.ByteMe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainLauncher extends Game {
    static Map<Integer, String> levelsBg = new HashMap<>();
    static ArrayList<Level> levels;
    static ArrayList<Player> players;

    static {
        levelsBg.put(1, "Level1_bg.png");
        levelsBg.put(2, "Level2_bg.png");
        levelsBg.put(3, "Level3_bg.png");
        levelsBg.put(4, "level4_bg.png");
        levelsBg.put(5, "level5_bg.png");
        levelsBg.put(6, "level6_bg.png");
    }

    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
        //setScreen(new LevelsScreen(this));
    }
}
