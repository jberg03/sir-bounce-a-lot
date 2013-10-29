package com.hislightgamestudio.sirbouncealot.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.hislightgamestudio.sirbouncealot.tween.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SplashScreen extends AbstractScreen{
	private Sprite splash;
	private Texture splashTexture;
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
		
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		splashTexture = new Texture("data/His Light Game Studio Logo(2).jpg");
		splash = new Sprite(splashTexture);
		splash.setX(Gdx.graphics.getWidth() / 2 - splashTexture.getWidth() / 2);
		splash.setY(Gdx.graphics.getHeight() / 2 - splashTexture.getHeight() / 2);
	
		//tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new Fader());
		Tween.set(splash, Fader.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, Fader.ALPHA, 2).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback(){

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		}).start(tweenManager);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		super.dispose();
		splash.getTexture().dispose();
	}

}
