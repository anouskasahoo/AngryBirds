//package com.ByteMe;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.Vector2;
//
//import java.util.ArrayList;
//
//public class Level3 extends Level implements Screen {
//
//    private final Texture slingshot2;
//    private final Texture backgroundTexture;
//    private final MainLauncher game;
//    private final Player player;
//
//    public Level3(MainLauncher game, Player player) {
//        super(game,"slingshot1.png",100,70,50,150);
//        slingshot2 = new Texture("slingshot2.png");
//        backgroundTexture = new Texture("level3_bg.png");
//        this.game = game;
//        this.player = player;
//
//        // Initialize birds
//        birds = new ArrayList<>();
//        Bombird b1 = new Bombird();
//        b1.position.add(0);
//        b1.position.add(70);
//        birds.add(b1);
//
//        TeleBird b2 = new TeleBird();
//        b2.position.add(90);
//        b2.position.add(160);
//        birds.add(b2);
//
//        ClassicBird b3 = new ClassicBird();
//        b3.position.add(60);
//        b3.position.add(70);
//        birds.add(b3);
//
//
//        //Initialize pigs
//        pigs = new ArrayList<>();
//        ClassicPig cp1 = new ClassicPig();
//        cp1.position.add(677);
//        cp1.position.add(70);
//        pigs.add(cp1);
//
//        ClassicPig cp2 = new ClassicPig();
//        cp2.position.add(628);
//        cp2.position.add(115);
//        pigs.add(cp2);
//
//        ClassicPig cp3 = new ClassicPig();
//        cp3.position.add(628);
//        cp3.position.add(205);
//        pigs.add(cp3);
//
//        ClassicPig cp4 = new ClassicPig();
//        cp4.position.add(677);
//        cp4.position.add(253);
//        pigs.add(cp4);
//
//        KingPig kp1 = new KingPig();
//        kp1.position.add(720);
//        kp1.position.add(205);
//        pigs.add(kp1);
//
//        PrettyPig pp1 = new PrettyPig();
//        pp1.position.add(585);
//        pp1.position.add(65);
//        pigs.add(pp1);
//
//        obstacles.add(new Wood(new Vector2(532, 70), Wood.Orientation.BOX));
//        obstacles.add(new Wood(new Vector2(585, 110), Wood.Orientation.HORIZONTAL));
//        obstacles.add(new Wood(new Vector2(580, 70), Wood.Orientation.VERTICAL));
//        obstacles.add(new TNT(new Vector2(720, 70)));
//        obstacles.add(new TNT(new Vector2(630, 70)));
//        obstacles.add(new TNT(new Vector2(580, 115)));
//        obstacles.add(new TNT(new Vector2(720, 160)));
//        obstacles.add(new Wood(new Vector2(625, 155), Wood.Orientation.HORIZONTAL));
//        obstacles.add(new Wood(new Vector2(670, 115), Wood.Orientation.VERTICAL));
//        obstacles.add(new Wood(new Vector2(580, 160), Wood.Orientation.BOX));
//        obstacles.add(new Wood(new Vector2(672, 205), Wood.Orientation.BOX));
//        obstacles.add(new Stone(new Vector2(628, 160), Stone.Orientation.H_BOX));
//        obstacles.add(new Stone(new Vector2(672, 115), Stone.Orientation.HORIZONTAL));
//        obstacles.add(new Stone(new Vector2(720, 115), Stone.Orientation.VERTICAL));
//        obstacles.add(new Stone(new Vector2(760, 115), Stone.Orientation.VERTICAL));
//    }
//
//    @Override
//    public void show() {
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//            float mouseX = Gdx.input.getX();
//            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//            if (mouseX >= pauseButton_x && mouseX <= pauseButton_x+pauseButton_w && mouseY >= pauseButton_y && mouseY <= pauseButton_y+pauseButton_h){
//                game.setScreen(new PauseGame(game,3, player));
//            }
//            if (mouseX >= winButton_x && mouseX <= winButton_x+winButton_w && mouseY >= winButton_y && mouseY <= winButton_y+winButton_h){
//                game.setScreen(new Win(game, player));
//            }
//            if (mouseX >= lossButton_x && mouseX <= lossButton_x+lossButton_w && mouseY >= lossButton_y && mouseY <= lossButton_y+lossButton_h){
//                game.setScreen(new Loss(game,3, player));
//            }
//
//        }
//
//        batch.begin();
//        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
//        for (Bird bird : birds) {
//            batch.draw(bird.texture, bird.position.get(0), bird.position.get(1), bird.size.get(0), bird.size.get(1));
//        }
//
//        for (Obstacle obstacle : obstacles) {
//            obstacle.render(batch);
//        }
//
//        for (Pig pig : pigs) {
//            batch.draw(pig.texture, pig.position.get(0), pig.position.get(1), pig.size.get(0), pig.size.get(1));
//        }
//
//        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);
//        batch.draw(winButton, winButton_x, winButton_y, winButton_w, winButton_h);
//        batch.draw(lossButton, lossButton_x, lossButton_y, lossButton_w, lossButton_h);
//        batch.draw(slingshot2, slingshot.position.get(0) - 5, slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
//        batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void hide() {
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//        batch.dispose();
//        slingshot2.dispose();
//        backgroundTexture.dispose();
//        for (Bird bird : birds) {
//            if (bird.texture != null) {
//                bird.texture.dispose();
//            }
//        }
//    }
//}
