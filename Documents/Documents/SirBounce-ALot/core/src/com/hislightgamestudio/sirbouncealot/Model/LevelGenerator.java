package com.hislightgamestudio.sirbouncealot.Model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Json;

public class LevelGenerator {
    private Body environment;
    private float leftEdge, rightEdge;
    private ArrayList<Platforms> platforms;

    public LevelGenerator(Body environment, float leftEdge, float rightEdge) {
        this.environment = environment;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
    }

    public void generate() {
        Json json = new Json();
        LevelGenerator lg = json.fromJson(LevelGenerator.class, Gdx.files.internal("Levels/Level1.json"));

        PolygonShape shape = new PolygonShape();


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
