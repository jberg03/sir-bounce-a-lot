package com.hislightgamestudio.sirbouncealot.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends MenuAbstractScreen{
	private Image splash;
	private Texture splashTexture;
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		//tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		//set preferences
		Gdx.graphics.setVSync(SettingsScreen.vSync());
		
		splashTexture = new Texture("data/His Light Game Studio Logo(2).jpg");
		splash = new Image(splashTexture);
		splash.setX(Gdx.graphics.getWidth() / 2 - splashTexture.getWidth() / 2);
		splash.setY(Gdx.graphics.getHeight() / 2 - splashTexture.getHeight() / 2);
		
		stage.addActor(splash);
		
		splash.addAction(sequence(alpha(0), fadeIn(1.5f), delay(1.5f), fadeOut(1.5f), run(new Runnable(){

			@Override
			public void run() {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());				
			}
			
		})));
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
	}

}
