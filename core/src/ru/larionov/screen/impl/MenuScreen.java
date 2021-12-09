package ru.larionov.screen.impl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import javax.annotation.processing.Completion;

import ru.larionov.math.Rect;
import ru.larionov.screen.BaseScreen;
import ru.larionov.sprite.impl.Background;
import ru.larionov.sprite.impl.ButtonExit;
import ru.larionov.sprite.impl.ButtonPlay;
import ru.larionov.sprite.impl.Star;

public class MenuScreen extends BaseScreen implements Music.OnCompletionListener {

    private static final int STAR_COUNT = 256;

    private final Game game;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/menuAtlas.tpack");

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setOnCompletionListener(this);
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game, music);

        music.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        if (!music.isPlaying())
            music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
//        batch.setColor(1f, 1f, 1f, 1f);
        background.draw(batch);
        for (Star star : stars) {
//            batch.setColor(Color.YELLOW);
            star.draw(batch);
//            batch.setColor(Color.CLEAR);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public void onCompletion(Music music) {
        music.play();
    }
}
