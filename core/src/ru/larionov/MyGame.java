package ru.larionov;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.larionov.screen.impl.MenuScreen;

public class MyGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}

}
