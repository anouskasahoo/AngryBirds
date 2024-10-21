package com.ByteMe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public class MainLauncher extends Game {

//    static Map<Integer, Level> levels = new HashMap<>();
//
//    {
//        levels.put(1, new Level1(this));
//    }


    @Override
    public void create() {
        //setScreen(new LoadingScreen(this));
        setScreen(new Level1(this));
    }
}
