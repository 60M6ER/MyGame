package ru.larionov.sprite.impl;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.larionov.math.Rect;
import ru.larionov.sprite.Sprite;

public class BattleShip extends Sprite {

    private Rect worldBounds;
    private final Vector2 v;
    private Vector2 direction;
    private float speed;
    int pointer;

    public BattleShip(TextureAtlas atlas) {
        super(null);
        TextureAtlas.AtlasRegion region = atlas.findRegion("main_ship");
        TextureRegion[][] split = region.split(region.getRegionWidth() / 2, region.getRegionHeight());
        regions = new TextureRegion[2];
        for (int i = 0; i < split.length; i++) {
            regions[i] = split[i][0];
        }
        speed = 0.4f;
        pointer = -1;
        v = new Vector2();
        direction = new Vector2(0, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.1f * worldBounds.getHeight());
        this.pos.set(0, worldBounds.getBottom() + 0.1f);
    }

    @Override
    public void update(float delta) {
        if (direction.len() > 0){
            v.set(direction).scl(speed);
            if (v.x < 0 && this.worldBounds.getLeft() < getLeft()
                    || v.x > 0 && this.worldBounds.getRight() > getRight())
                this.pos.mulAdd(v, delta);
        }
        //this.pos.mulAdd(v, delta);
        //checkBounds();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 21:
                direction.set(-1, 0);
                break;
            case 22:
                direction.set(1, 0);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case 21:
                if (direction.x == -1)
                    direction.set(0, 0);
                break;
            case 22:
                if (direction.x == 1)
                    direction.set(0, 0);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x > 0)
            direction.set(1, 0);
        else if (touch.x < 0)
            direction.set(-1, 0);
        if (this.pointer == -1)
            this.pointer = pointer;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer == pointer){
            direction.set(0, 0);
            this.pointer = -1;
        }
        return false;
    }

    private void checkBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }
}
