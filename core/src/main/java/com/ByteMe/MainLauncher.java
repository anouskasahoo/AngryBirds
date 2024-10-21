package com.ByteMe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;


public class MainLauncher extends Game {
    static Map<Integer, String> levelsBg = new HashMap<>();

    static {
        levelsBg.put(1, "Level_bg.png");
        levelsBg.put(2, "level2_bg");
        levelsBg.put(3, "level3_bg");
        levelsBg.put(4, "level4_bg");
        levelsBg.put(5, "level5_bg");
        levelsBg.put(6, "level6_bg");
    }

    static Map<Integer, String> Levels = new HashMap<>();

    static {
        levelsBg.put(1, "Level_bg.png");
        levelsBg.put(2, "level2_bg.png");
        levelsBg.put(3, "level3_bg.png");
        levelsBg.put(4, "level4_bg.png");
        levelsBg.put(5, "level5_bg.png");
        levelsBg.put(6, "level6_bg.png");
    }

    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }
}
