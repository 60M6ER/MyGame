package ru.larionov.sprite.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.io.PipedOutputStream;

import ru.larionov.math.Rect;
import ru.larionov.sprite.Sprite;

public class Logo extends Sprite {

    private float v; // Скорость
    private Vector2 dist; // Расстояние между точкой нажатия и лого

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        setSize(0.15f, 0.15f);
        dist = new Vector2();
        v = 0.01f;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        this.pos.set(worldBounds.pos);
    }

    public void update(float delta, Vector2 posTouch) {
        super.update(delta);
        dist.set(posTouch).sub(pos);
        float len = dist.len();
        if (len != 0){
            if (len < v * 2){
                pos.set(posTouch);
            }else {
                pos.add(dist.nor().scl(v));
            }
        }
    }
}
