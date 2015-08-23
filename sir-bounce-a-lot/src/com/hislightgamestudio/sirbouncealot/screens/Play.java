/**
 * *********************************************************************
 * Copyright 2013 His Light Game Studio
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * *************************************************************************
 * Play.java is my initial attempt at gameplay and is most likely not going
 * to be used in the game when finished. It is more for the prototype than
 * anything
 * ***********************************************************************
 */
package com.hislightgamestudio.sirbouncealot.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.hislightgamestudio.sirbouncealot.Model.Player;
import com.hislightgamestudio.sirbouncealot.control.InputController;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.hislightgamestudio.sirbouncealot.utils.MapBodyManager;

public class Play extends AbstractGameScreen {
    private final float TIMESTEP = 1 / 30f;
    private final int VELOCITYITERATIONS = 4;
    private final int POSITIONITERATIONS = 2;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private InputMultiplexer inputMulti;

    private Player player;
    private SpriteBatch batch;
    private Array<Body> bodies = new Array<>();

    private Window pause;
    private boolean visible = false;
    private boolean updateable = true;

    private Vector3 downLeft, downRight;
    private TiledMapRenderer tileMapRenderer;
    private MapBodyManager mapBodyManager;

    @Override
    public void show() {
        batch = new SpriteBatch();
        world = new World(new Vector2(0.0f, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tiledMap = new TmxMapLoader().load("Levels/Level" + list.getSelection() + ".tmx");
        tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //Player
        player = new Player(world, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3, 16);
        world.setContactFilter(player);
        world.setContactListener(player);

        mapBodyManager = new MapBodyManager(world, 1, Gdx.files.internal("Levels/materials.json"), 2);
        mapBodyManager.createPhysics(tiledMap, "PhysicsLayer");

        pauseMenu();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(inputMulti = new InputMultiplexer(new InputController() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Keys.ESCAPE:
                        InputController.playing = false;
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
                        break;
                    case Keys.ENTER:
                        updateable = false;
                        visible = true;
                        Gdx.input.setInputProcessor(stage);
                        break;
                    case Keys.BACK:
                        updateable = false;
                        visible = true;
                        Gdx.input.setInputProcessor(stage);
                        break;
                }
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount / 25f;
                return true;
            }
        }, player));

        downLeft = new Vector3(0, Gdx.graphics.getHeight() - 16, 0);
        downRight = new Vector3(Gdx.graphics.getWidth(), downLeft.y, 0);
        camera.unproject(downLeft);
        camera.unproject(downRight);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        debugRenderer.render(world, camera.combined);

        camera.position.y = player.getBody().getPosition().y > camera.position.y ? player.getBody().getPosition().y : camera.position.y;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.draw(batch);
            }
        }

        batch.end();

        tileMapRenderer.setView(camera);
        tileMapRenderer.render();

        //if player goes off screen on one side make them come back in on the other side
        if (player.getBody().getPosition().x < downLeft.x)
            player.getBody().setTransform(downRight.x, player.getBody().getPosition().y,
                    player.getBody().getAngle());
        if (player.getBody().getPosition().x > downRight.x)
            player.getBody().setTransform(downLeft.x, player.getBody().getPosition().y,
                    player.getBody().getAngle());


        player.Update();

        pause.setVisible(visible);

        if (updateable)
            world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    private void pauseMenu() {
        //Pause Menu
        pause = new Window("PAUSE", menuSkin);
        pause.setMovable(false);

        //create the resume button
        TextButton resume = new TextButton("RESUME", menuSkin);
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateable = true;
                visible = false;
                Gdx.input.setInputProcessor(inputMulti);
            }
        });
        resume.pad(5f);

				/*//create the settings button
				TextButton settings = new TextButton("SETTINGS", menuSkin);
				settings.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
					}
				});
				settings.pad(5f);*/

        //creating exit button
        TextButton exit = new TextButton("EXIT", menuSkin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
        exit.pad(5f);


        pause.pad(64);
        pause.add(resume).row();
        pause.add().row();
        pause.add(exit);
        pause.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        pause.setPosition(Gdx.graphics.getWidth() / 2 - pause.getWidth() / 2, Gdx.graphics.getHeight() / 2 - pause.getHeight() / 2);

        stage.addActor(pause);
    }

    @Override
    public void hide() {
        if (!InputController.playing) {
            super.dispose();
            dispose();
        }
    }

    @Override
    public void pause() {
        super.resume();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        world.dispose();
        player.dispose();
        mapBodyManager.destroyPhysics();
        debugRenderer.dispose();
        //gameAtlas.dispose();
        //groundSprite.getTexture().dispose();
        //for(int i = 0; i < groundSpriteArray.length; i++)
        //groundSpriteArray[i] = null;
    }
}