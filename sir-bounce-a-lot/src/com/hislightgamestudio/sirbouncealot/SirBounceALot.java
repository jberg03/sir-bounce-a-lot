package com.hislightgamestudio.sirbouncealot;

import com.badlogic.gdx.Game;
import com.hislightgamestudio.sirbouncealot.screens.SplashScreen;

public class SirBounceALot extends Game {
	public static final String TITLE = "SirBounce-A-Lot";
	public static final String VERSION = "v.0.0.3";
	
	@Override
	public void create() {		
		setScreen(new SplashScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
