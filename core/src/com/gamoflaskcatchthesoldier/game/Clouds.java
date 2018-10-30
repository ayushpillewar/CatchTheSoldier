package com.gamoflaskcatchthesoldier.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Clouds extends Sprite {
    private TextureRegion cloud;
    private float cloudX;
    private float cloudY;
    private float cloudSizeX;
    private float cloudsizeY;
    Clouds (PlayScreen screen){
        super(screen.getAtlas().findRegion("cloud"));
        //setting cloud initial x and y position
        cloudX = PulbicVariables.SCREEN_WIDTH /2.0f;
        cloudY = PulbicVariables.SCREEN_HEIGHT/2.0f;
        setPosition(cloudX/PulbicVariables.PPM,cloudY/PulbicVariables.PPM);

        //setting cloud size
        cloudSizeX = PulbicVariables.SCREEN_WIDTH/6.0f;
        cloudsizeY = PulbicVariables.SCREEN_HEIGHT/6.0f;
        setSize(cloudSizeX/PulbicVariables.PPM,cloudsizeY/PulbicVariables.PPM);
        // cloud = new TextureRegion(getTexture(),PulbicVariables.SCREEN_WIDTH + 10,PulbicVariables.SCREEN_HEIGHT/2,PulbicVariables.SCREEN_WIDTH/6,PulbicVariables.SCREEN_HEIGHT/6);

    }
    public void moveCloud(){
        if(cloudX< 0 - cloudSizeX){
            cloudX = PulbicVariables.SCREEN_WIDTH + 20.0f;
        }else{
            cloudX = cloudX - 0.5f;
        }

        setPosition(cloudX/PulbicVariables.PPM,cloudY/PulbicVariables.PPM);
    }


}
