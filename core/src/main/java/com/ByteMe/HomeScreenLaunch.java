package com.ByteMe;

import com.badlogic.gdx.Game;

///** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class HomeScreenLaunch extends Game {
    @Override
    public void create() {
        setScreen(new HomeScreen());
    }
}
