package com.hislightgamestudio.sirbouncealot.screens;

/**
 * SirBounce-ALot
 * Created by john on 10/30/15.
 * <p/>
 * Copyright His Light Game Studio 2015
 */
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
    private Array<Body> bodies = new Array<Body>();

    private Window pause;
    private boolean visible = false;
    private boolean updateable = true;

    private Vector3 downLeft, downRight;
    private TiledMapRenderer tileMapRenderer;
    private MapBodyManager mapBodyManager;
    private Sprite console;

    @Override
    public void show() {
        Texture tex = new Texture("data/console.png");
        console = new Sprite(tex);
        console.setSize(Gdx.graphics.getWidth() / 2, 256);
        console.setOrigin(0, 0);

        world = new World(new Vector2(0.0f, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 4);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        String level = Levels.getLevel().getSelected().toString();
        tiledMap = new TmxMapLoader().load("Levels/Level" + level + ".tmx");
        tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //Player
        player = new Player(world, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4, 16);
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
        //debugRenderer.render(world, camera.combined);

        camera.position.y = player.getBody().getPosition().y > camera.position.y ? player.getBody().getPosition().y : camera.position.y;
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.draw(batch);
            }
        }

        /*if(Gdx.app.getType() == Application.ApplicationType.Android){
            console.setPosition(0, 0);
            console.draw(batch);
        }*/

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
        //view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        super.resize(width, height);
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
