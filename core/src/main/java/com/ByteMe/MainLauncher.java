package com.ByteMe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MainLauncher extends Game {
    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }
}
