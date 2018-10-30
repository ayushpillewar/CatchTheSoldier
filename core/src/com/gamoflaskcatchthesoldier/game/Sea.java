package com.gamoflaskcatchthesoldier.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Sea extends Sprite {

    private float seaX;
    private float seaY;
    private float seaSizeX;
    private float seaSizeY;

    Sea(PlayScreen screen){
        super(screen.getAtlas().findRegion("sea"));

        //setting up size and positon of sea
        seaX = 0;
        seaY = 0;
        seaSizeX = PulbicVariables.SCREEN_WIDTH;
        seaSizeY = PulbicVariables.SCREEN_HEIGHT/8;
        setPosition(seaX/PulbicVariables.PPM,seaY/PulbicVariables.PPM);
        setSize(seaSizeX/PulbicVariables.PPM,seaSizeY/PulbicVariables.PPM);
    }

}
