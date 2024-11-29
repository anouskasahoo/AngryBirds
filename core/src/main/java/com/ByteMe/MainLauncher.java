package com.ByteMe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainLauncher extends Game implements Serializable {
    private static final long serialVersionUID = 1L;

    static Map<Integer, String> levelsBg = new HashMap<>();
    static ArrayList<Level> levels;
    static ArrayList<Player> players = new ArrayList<>();
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
        loadPlayers();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bg_music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        super.dispose();
    }

    public void loadPlayers(){
        try (FileInputStream fileIn = new FileInputStream("saved.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn)) {

            players = (ArrayList<Player>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }

}
