package com.hislightgamestudio.sirbouncealot.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.hislightgamestudio.sirbouncealot.Model.Player;

public class Play implements Screen{
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8;
	private final int POSITIONITERATIONS = 3;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	
	private Player player;
	
	private Vector3 downLeft, downRight;
	
	private final float BOX_TO_WORLD = 100f;
	private final float WORLD_TO_BOX = 0.01f;
	@Override
	public void render(float delta) {
		debugRenderer.render(world, camera.combined);
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		
	}
	@Override
	public void resize(int width, int height) {
		
	}
	@Override
	public void show() {
		
	}
	@Override
	public void hide() {
		
	}
	@Override
	public void pause() {
		
	}
	@Override
	public void resume() {
		
	}
	@Override
	public void dispose() {
		
	}
	
	
}
