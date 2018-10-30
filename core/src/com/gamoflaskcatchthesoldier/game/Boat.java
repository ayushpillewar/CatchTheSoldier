package com.gamoflaskcatchthesoldier.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Boat extends Sprite {
    PlayScreen screen;
    private float boatX;
    private float boatY;
    private float boatSizeX;
    private float boatSizeY;
    BodyDef bodyDef;
    Body boatBody;
    FixtureDef fixtureDef;
    PolygonShape shape;
    private World world;
    Boat(PlayScreen screen, World world){

        super(screen.getAtlas().findRegion("boat"));
        this.screen = screen;
        this.world = world;

        //setting up the initial position of boat
        boatX = (PulbicVariables.SCREEN_WIDTH + 20.0f)/PulbicVariables.PPM;
        boatY = (PulbicVariables.SCREEN_HEIGHT/20.0f)/PulbicVariables.PPM;
        setPosition(boatX,boatY);

        //setting up the size of boat
        boatSizeX = (PulbicVariables.SCREEN_WIDTH/5)/PulbicVariables.PPM;
        boatSizeY = (PulbicVariables.SCREEN_HEIGHT/6)/PulbicVariables.PPM;

        setSize(boatSizeX,boatSizeY);

        //creating the box2d body
        //this.world = world;
        shape = new PolygonShape();
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(boatX,boatY);
        boatBody = world.createBody(bodyDef);

        shape.setAsBox((boatSizeX/2),(boatSizeY/2));

        fixtureDef.shape = shape;
        boatBody.createFixture(fixtureDef);
        boatBody.setLinearVelocity(new Vector2(-5f,0));
        boatBody.setUserData(this);




    }

    public void createBoatBody(){

    }
    public void moveBoat(){
       /* if(boatX < 0 - boatSizeX){
            boatX = PulbicVariables.SCREEN_WIDTH + 20;
        }else{
            boatX = boatX - PulbicVariables.BoatSpeed;
        }*/

        if(boatBody.getPosition().x < 0 - boatSizeX ){
            boatBody.setLinearVelocity(PulbicVariables.BoatVeloctiy,0);
            screen.createSoldier();
        }else if(boatBody.getPosition().x > (PulbicVariables.SCREEN_WIDTH)/PulbicVariables.PPM + boatSizeX){
            boatBody.setLinearVelocity(-PulbicVariables.BoatVeloctiy,0);
            screen.createSoldier();
        }
        setPosition((boatBody.getPosition().x - (boatSizeX/2)),(boatBody.getPosition().y - (boatSizeY/2)));

        //boatBody.applyLinearImpulse(new Vector2(1,0),new Vector2(boatBody.getWorldCenter().x,boatBody.getWorldCenter().y),true);

        //changin boat velocity as level increases
        if(PulbicVariables.Score >100){
            PulbicVariables.BoatVeloctiy = 7f;
        }else if(PulbicVariables.Score > 200){
            PulbicVariables.BoatVeloctiy = 8f;
        }else if(PulbicVariables.Score >300){
            PulbicVariables.BoatVeloctiy = 10f;
        }else if(PulbicVariables.Score > 450){
            PulbicVariables.BoatVeloctiy = 12f;
        }else if(PulbicVariables.Score > 600 ){
            PulbicVariables.BoatVeloctiy = 15f;
        }

    }

}
