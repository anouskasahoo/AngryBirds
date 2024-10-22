package com.ByteMe.lwjgl3;

import com.ByteMe.MainLauncher;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

    public class Lwjgl3Launcher {
        public static void main(String[] args) {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Angry Birds");
            config.setWindowedMode(800, 480);
            config.setResizable(false);

            new Lwjgl3Application(new MainLauncher(), config);
        }
    }
