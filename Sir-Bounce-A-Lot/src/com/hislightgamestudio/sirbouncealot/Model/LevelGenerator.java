package com.hislightgamestudio.sirbouncealot.Model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class LevelGenerator {
	private Body environment;
	private float leftEdge, rightEdge;
	
	public LevelGenerator(Body environment, float leftEdge, float rightEdge){
		this.environment = environment;
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
	}
	
	public void generate(){
		float width = 1.5f;
		float height = 1.0f;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		
		environment.createFixture(shape, 0);
		
		shape.dispose();
	}

	public Body getEnvironment() {
		return environment;
	}

	public void setEnvironment(Body environment) {
		this.environment = environment;
	}

	public float getLeftEdge() {
		return leftEdge;
	}

	public void setLeftEdge(float leftEdge) {
		this.leftEdge = leftEdge;
	}

	public float getRightEdge() {
		return rightEdge;
	}

	public void setRightEdge(float rightEdge) {
		this.rightEdge = rightEdge;
	}	
}
