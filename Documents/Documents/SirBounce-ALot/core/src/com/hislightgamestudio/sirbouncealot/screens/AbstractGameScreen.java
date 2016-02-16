package com.hislightgamestudio.sirbouncealot.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * SirBounce-ALot
 * Created by john on 10/30/15.
 * <p/>
 * Copyright His Light Game Studio 2015
 */
public class AbstractGameScreen implements Screen {
    protected TiledMap tiledMap;
    protected Table table;
    protected Stage stage;
    protected TextureAtlas atlas;
    protected Skin menuSkin;
    protected OrthographicCamera camera;
    protected Viewport view;
    protected SpriteBatch batch;
    protected float AspectRatio;

    public AbstractGameScreen() {
        //Initialize all the stuff that can be used across the board
        atlas = new TextureAtlas("Menu/menuAtlas.pack");
        menuSkin = new Skin(Gdx.files.internal("Menu/menuSkin.json"), atlas);

        table = new Table(menuSkin);
        table.setFillParent(true);

        AspectRatio = (Gdx.graphics.getWidth() / Gdx.graphics.getHeight());

        camera = new OrthographicCamera();
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            view = new FillViewport(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 1.5f, camera);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            view = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(view);
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        System.out.println(stage.getViewport().getViewportWidth());
        table.invalidateHierarchy();
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
        batch.dispose();
    }
}
