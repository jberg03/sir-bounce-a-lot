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

import com.badlogic.gdx.Gdx;
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
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.hislightgamestudio.sirbouncealot.control.InputController;

public class Player extends InputController implements ContactFilter, ContactListener{
	private Body body;
	private Fixture fixture;
	public final float width, height;
	private float movementForce = 20;
	private Vector2 velocity = new Vector2();
	private float jumpPower = 10f;
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
		
		atlas = new TextureAtlas("game/gameAtlas.pack");
		sirBounceALot = atlas.createSprite("SirBounce_A_Lot");
		sirBounceALot.setSize(width, height);
		sirBounceALot.setOrigin(x, y);
		
		body.setUserData(sirBounceALot);

		shape.dispose();
    }
	
	public void Update(){
        velocity.y = body.getLinearVelocity().y;

        System.out.println(body.getLinearVelocity());
        body.setLinearVelocity(velocity);
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
		if (keycode == Keys.A)
            velocity.x = -movementForce;
        if (keycode == Keys.D)
            velocity.x = movementForce;

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
        if(Gdx.input.isKeyPressed(Keys.D))
            velocity.x = movementForce;
        else if(Gdx.input.isKeyPressed(Keys.A))
            velocity.x = -movementForce;
        else
            velocity.x = 0f;

        return false;
	}	

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < Gdx.graphics.getWidth() / 2){
            velocity.x = -movementForce;
        }

        if(screenX > Gdx.graphics.getWidth() / 2){
            velocity.x = movementForce;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(Gdx.input.getX() < Gdx.graphics.getWidth() / 2){
            velocity.x = -movementForce;
        }
        else if(Gdx.input.getX() > Gdx.graphics.getWidth() / 2){
            velocity.x = movementForce;
        }
        else
            velocity.x = 0;

        return false;
    }

    public float getJumpPower(){
        return jumpPower;
    }
    public void setJumpPower(float jump){
        this.jumpPower = jump;
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
