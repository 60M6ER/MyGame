package ru.larionov.screen.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.larionov.math.Rect;
import ru.larionov.screen.BaseScreen;
import ru.larionov.sprite.impl.Background;
import ru.larionov.sprite.impl.BattleShip;
import ru.larionov.sprite.impl.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;
    private BattleShip battleShip;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        battleShip = new BattleShip(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        battleShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        battleShip.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        battleShip.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        battleShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        battleShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    private void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        battleShip.update(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        battleShip.draw(batch);
        batch.end();
    }
}
