package com.hislightgamestudio.sirbouncealot.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class MenuAbstractScreen implements Screen {
    protected Stage stage;
    protected TextureAtlas atlas;
    protected Skin menuSkin;
    protected Table table;

    MenuAbstractScreen() {
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        atlas = new TextureAtlas("Menu/menuAtlas.pack");
        menuSkin = new Skin(Gdx.files.internal("Menu/menuSkin.json"), atlas);
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
