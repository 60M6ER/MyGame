package ru.larionov.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.larionov.screen.BaseScreen;

public class MenuScreen extends BaseScreen {

    TextureRegion background;
    private Texture img;
    private Vector2 imgPosition;
    private Vector2 course;
    private Vector2 len;
    private Vector2 touch;
    private Vector2 v;

    @Override
    public void show() {
        super.show();
        background = new TextureRegion(new Texture("background_picture.jpg"), 0, 0, 750, 700);
        img = new Texture("badlogic.jpg");
        imgPosition = new Vector2(1,1);
        touch = new Vector2();
        course = new Vector2();
        len = new Vector2();
        v = new Vector2(1, 1);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(img, imgPosition.x, imgPosition.y);
        batch.end();
        len.set(touch.x, touch.y).sub(imgPosition);
        if (len.len() != 0){
            if (len.len() < v.len() * 2){
                imgPosition.set(touch.x, touch.y);
                System.out.println("img position: x-" + imgPosition.x + ", y-" + imgPosition.y);
            }else {
                imgPosition.add(new Vector2(v.x * course.x, v.y * course.y));
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        course.set(touch.x, touch.y).sub(imgPosition).nor();
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
