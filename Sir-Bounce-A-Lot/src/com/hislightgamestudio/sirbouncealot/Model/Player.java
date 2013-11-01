package com.hislightgamestudio.sirbouncealot.Model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
	private Body body;
	private Fixture fixture;
	public final float width, height;
	private float movementForce = 500;
	private Vector2 velocity = new Vector2();
	
	public Player(World world, float x, float y, float width){
		this.width = width;
		height = width;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set(x, y);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(width / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 3;
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0.8f;
		
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
	}
	
	public void Update(){
		body.applyForceToCenter(velocity, true);
	}
}
