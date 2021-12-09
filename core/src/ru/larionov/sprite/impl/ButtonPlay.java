package ru.larionov.sprite.impl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.larionov.math.Rect;
import ru.larionov.screen.impl.GameScreen;
import ru.larionov.sprite.BaseButton;


public class ButtonPlay extends BaseButton {

    private static final float HEIGHT = 0.25f;
    private static final float MARGIN = 0.03f;

    private final Game game;
    private Music music;

    public ButtonPlay(TextureAtlas atlas, Game game, Music music) {
        super(atlas.findRegion("btPlay"));
        this.music = music;
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setLeft(worldBounds.getLeft() + MARGIN);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(music));
    }
}
