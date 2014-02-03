/************************************************************************
* Copyright 2013 His Light Game Studio
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
**************************************************************************
* Player.java is where all body, fixture, and sprite are handled
*************************************************************************/
package com.hislightgamestudio.sirbouncealot.Model;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
	private float movementForce = 100;
	private Vector2 velocity = new Vector2();
	private float jumpPower = 19f;
	private Sprite sirBounceALot;
	private TextureAtlas atlas;
	
	public Player(World world, float x, float y, float width){
		this.width = width;
		height = width;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set(x, y);
		
		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2(x, y));
		shape.setRadius(width / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 3f;
		fixtureDef.restitution = 0f;
		fixtureDef.friction = 0.8f;
		
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		
		atlas = new TextureAtlas("Game/GameAtlas.pack");
		sirBounceALot = atlas.createSprite("SirBounce_A_Lot");
		sirBounceALot.setSize(width, height);
		sirBounceALot.setOrigin(x, y);
		
		body.setUserData(sirBounceALot);
		
		shape.dispose();
	}
	
	public void Update(){
		body.applyForceToCenter(velocity, true);
	}
	
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		if(fixtureA == fixture || fixtureB == fixture){
			return (body.getLinearVelocity().y < 0);
		}
		
		return false;
	}
	
	@Override
	public void beginContact(Contact contact) {
		
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		//make the player jump when it lands on a platform
		if(contact.getFixtureA() == fixture || contact.getFixtureB() == fixture){
			if(contact.getWorldManifold().getPoints()[0].y <= body.getPosition().y){
				body.applyLinearImpulse(0, jumpPower, body.getWorldCenter().x, body.getWorldCenter().y, true);
			}
		}	
	}
	
	@Override
	public void endContact(Contact contact) {
		
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
	
	public void dispose(){
		atlas.dispose();
		sirBounceALot.getTexture().dispose();
	}
}
