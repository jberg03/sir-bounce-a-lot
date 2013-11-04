package com.hislightgamestudio.sirbouncealot.Model;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.hislightgamestudio.sirbouncealot.control.InputController;

public class Player extends InputController implements ContactFilter, ContactListener{
	private Body body;
	private Fixture fixture;
	public final float width, height;
	private float movementForce = 500;
	private Vector2 velocity = new Vector2();
	private float jumpPower = 45;
	
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
	
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		if(fixtureA == fixture || fixtureB == fixture)
			return (body.getLinearVelocity().y < 0);
		
		return false;
	}
	
	@Override
	public void beginContact(Contact contact) {
		
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
	
	@Override
	public void endContact(Contact contact) {
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		//make the player jump when it lands on a platform
		if(contact.getFixtureA() == fixture || contact.getFixtureB() == fixture)
			if(contact.getWorldManifold().getPoints()[0].y <= body.getPosition().y - height / 2)
				body.applyLinearImpulse(0, jumpPower , body.getWorldCenter().x, body.getWorldCenter().y, true);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.A:
			velocity.x = -movementForce;
			break;
		case Keys.D:
			velocity.x = movementForce;
			break;
		default:
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.A || keycode == Keys.D)
			velocity.x = 0;
		return true;
	}	
	
	public float getRestitution(){
		return fixture.getRestitution();
	}
	
	public void setRestituion(float restitution){
		fixture.setRestitution(restitution);
	}
	
	public Body getBody(){
		return body;
	}
	
	public Fixture getFixture(){
		return fixture;
	}
}
