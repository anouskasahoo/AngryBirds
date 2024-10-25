package com.ByteMe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainLauncher extends Game {
    static Map<Integer, String> levelsBg = new HashMap<>();
    static ArrayList<Level> levels;
    static ArrayList<Player> players;
    private Music backgroundMusic;


    static {
        levelsBg.put(1, "Hand1.png");
        levelsBg.put(2, "Hand2.png");
        levelsBg.put(3, "Hand3.png");
        levelsBg.put(4, "Hand4.png");
        levelsBg.put(5, "Hand5.png");
        levelsBg.put(6, "Hand6.png");
    }

    @Override
    public void create() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bg_music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        setScreen(new LoadingScreen(this));
//        Player player = new Player();
//        setScreen(new Win(this, player));
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        super.dispose();
    }

}
