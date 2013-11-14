package com.hislightgamestudio.sirbouncealot.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class AbstractGameScreen implements Screen{
	protected Stage stage;
	protected TextureAtlas atlas;
	protected Skin menuSkin;
	
	public AbstractGameScreen(){
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		atlas = new TextureAtlas("Menu/menuAtlas.pack");
		menuSkin = new Skin(Gdx.files.internal("Menu/menuSkin.json"), atlas);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f,  0.0f,  0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void show() {
		
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
		stage.dispose();
		menuSkin.dispose();
		atlas.dispose();
	}
}
