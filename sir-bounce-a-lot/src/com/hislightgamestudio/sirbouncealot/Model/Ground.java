package com.hislightgamestudio.sirbouncealot.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.*;

/**
 * sir-bounce-a-lot
 * Created by john on 8/14/15.
 * <p>
 * Copyright His Light Game Studio 2015
 */
public class Ground {
    private Body body;
    private Fixture fixture;
    public final float width, height, x, y;
    private TextureAtlas atlas;
    private Sprite groundSprite;
    private Sprite[] groundSpriteArray = new Sprite[50];

    public Ground(World world, float x, float y, float width, float height, float[] vertices) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        ChainShape shape = new ChainShape();
        shape.createChain(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        atlas = new TextureAtlas("game/gameAtlas.pack");
        groundSprite = atlas.createSprite("Level1_platform");
        Body ground = world.createBody(bodyDef);
        ground.createFixture(fixtureDef);

        for(int i = 0; i < groundSpriteArray.length; i++){
            groundSpriteArray[i] = groundSprite;
            groundSpriteArray[i].setSize(width, height);
        }

        shape.dispose();
    }

    public void dispose(){
        atlas.dispose();
        groundSprite.getTexture().dispose();
        for(int i = 0; i < groundSpriteArray.length; i++)
            groundSpriteArray[i] = null;
    }

    public Sprite[] getGroundSpriteArray(){
        return groundSpriteArray;
    }
}
