package ru.larionov.sprite.impl;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.larionov.math.Rect;
import ru.larionov.pool.impl.BulletPool;
import ru.larionov.sprite.Sprite;

public class BattleShip extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;

    private final BulletPool bulletPool;
    private final TextureRegion bulletRegion;
    private Rect worldBounds;
    private final Vector2 v;
    private Vector2 direction;
    private final Vector2 bulletV;
    private final float bulletHeight;
    private final int damage;
    private float speed;
    int pointer;

    public BattleShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(null);
        this.bulletPool = bulletPool;
        TextureAtlas.AtlasRegion region = atlas.findRegion("main_ship");
        TextureRegion[][] split = region.split(region.getRegionWidth() / 2, region.getRegionHeight());
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        regions = new TextureRegion[2];
        for (int i = 0; i < split.length; i++) {
            regions[i] = split[i][0];
        }
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.damage = 1;
        speed = 0.4f;
        pointer = -1;
        v = new Vector2();
        direction = new Vector2(0, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT * worldBounds.getHeight());
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
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
            case Input.Keys.A:
            case Input.Keys.LEFT:
                direction.set(-1, 0);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                direction.set(1, 0);
            case Input.Keys.UP:
                shoot();
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                if (direction.x == -1)
                    direction.set(0, 0);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
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

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
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
