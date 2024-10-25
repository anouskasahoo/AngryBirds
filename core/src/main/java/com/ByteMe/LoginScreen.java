package com.ByteMe;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginScreen implements Screen {
    private final MainLauncher game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture exitButton;
    private final Texture playButton;
    Player player;
    private Stage stage;
    private TextField playerNameField;
    private Skin skin;
    private String playerName = "";


    public LoginScreen(MainLauncher game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("login.png");
        exitButton = new Texture("ExitButton.png");
        playButton = new Texture("PlayButton.png");
        this.player = new Player();

        // Create a stage and a skin for UI components
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create a TextField for player name input
        playerNameField = new TextField("", skin);
        playerNameField.setPosition(210, 122); // Adjust position as per your UI layout
        playerNameField.setSize(200, 40);      // Adjust size
        playerNameField.setMessageText("Enter your name"); // Placeholder text

        // Add the TextField to the stage
        stage.addActor(playerNameField);

        // Set input processor to stage for UI interaction
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= 15 && mouseX <= 15+80 && mouseY >= 15 && mouseY <= 15+30) {
                Gdx.app.exit();
            }

            if (mouseX >= 325 && mouseX <= 325+150 && mouseY >= 50 && mouseY <= 50+60){
                player.setName(playerNameField.getText());
                game.setScreen(new HomeScreen(game, player));
            }

        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(exitButton, 15, 15, 80, 30);
        batch.draw(playButton, 325, 50, 150, 60);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        exitButton.dispose();
        playButton.dispose();
        stage.dispose();
        skin.dispose();
    }
}
