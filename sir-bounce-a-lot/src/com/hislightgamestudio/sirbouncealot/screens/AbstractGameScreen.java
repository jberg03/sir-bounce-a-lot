package com.hislightgamestudio.sirbouncealot.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractGameScreen implements Screen {
    protected TiledMapRenderer tileMapRenderer;
    protected TiledMap tiledMap;
    protected Table table;
    protected Stage stage;
    protected TextureAtlas atlas;
    protected Skin menuSkin;
    protected List list;

    public AbstractGameScreen() {
        stage = new Stage(new StretchViewport(640, 960));
        atlas = new TextureAtlas("Menu/menuAtlas.pack");
        menuSkin = new Skin(Gdx.files.internal("Menu/menuSkin.json"), atlas);
        list = new List(new String[]{"ONE", "TWO", "THREE", "FOUR", "FIVE"}, menuSkin);
        table = new Table(menuSkin);
        table.setFillParent(true);

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
        stage.setViewport(width, height, false);
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
    }
}
