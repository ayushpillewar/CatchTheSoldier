package com.gamoflaskcatchthesoldier.game;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

public class Soldier extends Sprite implements Disposable{
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    Body body;
    PolygonShape shape;
    float soldierX;
    float soldierY;
    float soldierSizeX;
    float soldierSizeY;
    boolean isDistroyAble = false;
    Animation soldierAnimationWithParachute;
    Animation soldierAnimation;
    boolean drawSprite = true;
    boolean isParachuteOn = true;


    Soldier(World world,PlayScreen screen){
        super(screen.getAtlas().findRegion("player"));
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        shape = new PolygonShape();
        soldierSizeX = (PulbicVariables.SCREEN_WIDTH/10)/PulbicVariables.PPM;
        soldierSizeY = (PulbicVariables.SCREEN_HEIGHT/10)/PulbicVariables.PPM;

        soldierX =((float) Math.random() * PulbicVariables.SCREEN_WIDTH)/PulbicVariables.PPM;
        soldierY = ((float) (Math.random() * PulbicVariables.SCREEN_HEIGHT) + PulbicVariables.SCREEN_HEIGHT)/PulbicVariables.PPM;

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(soldierX,soldierY);

        body = world.createBody(bodyDef);
        shape.setAsBox((soldierSizeX/2),(soldierSizeY/2));
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        //creating animation of soldier
        setPosition(body.getPosition().x,body.getPosition().y);
        setSize(soldierSizeX,soldierSizeY);
        Array<TextureRegion> soldierTextures = new Array<TextureRegion>();
        soldierTextures.add(screen.getAtlas().findRegion("player"));
        soldierTextures.add(screen.getAtlas().findRegion("player1"));
        soldierTextures.add(screen.getAtlas().findRegion("player2"));
        soldierAnimationWithParachute = new Animation(0.3f,soldierTextures);
        soldierTextures.clear();
        //player without parachute animation
        soldierTextures.add(screen.getAtlas().findRegion("playeronly"));
        soldierTextures.add(screen.getAtlas().findRegion("playeronly1"));
        soldierTextures.add(screen.getAtlas().findRegion("playeronly2"));
        soldierAnimation = new Animation(0.1f,soldierTextures);
        soldierTextures.clear();





    }
    public void animate(float elapsedTime){
        setRegion(getSoldierRegion(elapsedTime));
        setPosition((body.getPosition().x -soldierSizeX/2),(body.getPosition().y-soldierSizeY/2));
    }

    public TextureRegion getSoldierRegion(float elapsedTime){
        TextureRegion region;
        if (isParachuteOn) {
            region =(TextureRegion) soldierAnimationWithParachute.getKeyFrame(elapsedTime,true);
        }else {
          region  =(TextureRegion) soldierAnimation.getKeyFrame(elapsedTime,true);
        }
        return region;
    }
    public void destroySoldier(){
        if(body.getPosition().y < 0){
            isDistroyAble = true;
            drawSprite = false;
            PulbicVariables.lives = PulbicVariables.lives-1;

        }


    }
    public Body getBody(){
        return body;
    }

    @Override
    public void dispose() {

    }
}
