package com.gamoflaskcatchthesoldier.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private CatchTheSoldier game;
    private Hud hud;
    private Clouds clouds;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private TextureAtlas atlas ;
    private Sprite cloud;
    private Boat boat;
    private Sea sea;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private  Array<Soldier> soldier ;
    private Array<Soldier> removeAbleSoldier;
    private Soldier[] soldiers;
    private float elapsedTime;
    Preferences preferences;
    AddInterface addInterface;



    PlayScreen(CatchTheSoldier game){
        //creating the physics world
        PulbicVariables.Score = 0;
        elapsedTime = 0f;
        world = new World(new Vector2(0,-0.1f),true);
        box2DDebugRenderer = new Box2DDebugRenderer();


        atlas = new TextureAtlas("sprite.atlas");
        this.game = game;
        hud = new Hud(game.batch);
        clouds = new Clouds(this);
        boat = new Boat(this,world);
        gameCam = new OrthographicCamera();
        viewport = new StretchViewport(PulbicVariables.SCREEN_WIDTH/PulbicVariables.PPM,PulbicVariables.SCREEN_HEIGHT/PulbicVariables.PPM,gameCam);
        gameCam.position.set((PulbicVariables.SCREEN_WIDTH/2)/PulbicVariables.PPM,(PulbicVariables.SCREEN_HEIGHT/2)/PulbicVariables.PPM,0);



        sea = new Sea(this);

        soldier = new Array<Soldier>();
        removeAbleSoldier = new Array<Soldier>();
        createSoldier();

        //contact listener
        setcontactListener();

        //apply force when parachute is poped

        this.addInterface = game.addInterface;



    }

    public void applyForce(){
        for (Soldier so:soldier
             ) {
                if(!so.isParachuteOn){
                    so.getBody().applyLinearImpulse(new Vector2(0,-2),so.body.getWorldCenter(),true);
                }
        }
    }
    public void setcontactListener(){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                if (contact.getFixtureA().getBody().getUserData() instanceof Boat && contact.getFixtureB().getBody().getUserData() instanceof Soldier) {
                    ((Soldier) contact.getFixtureB().getBody().getUserData()).isDistroyAble = true;
                    PulbicVariables.Score += 5;


                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    public void createSoldier(){
        for(int i =0 ;i<3;i++){
            soldier.add(new Soldier(world,this));
        }
    }
    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {

    }
    public void update(float delta){
        world.step(1/60f,6,2);
        gameCam.update();
        removeAbleSoldier.clear();
        for (Soldier so: soldier
             ) {
            so.destroySoldier();
            if(so.isDistroyAble){
                world.destroyBody(so.getBody());
                removeAbleSoldier.add(so);
            }


        }
        for (Soldier sor: removeAbleSoldier
                ) {
            soldier.removeValue(sor,true);
        }
        elapsedTime = elapsedTime + delta;
        //checking for back button press
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            //creating a dialog
        }
    }

    public void handleInput(float delta){
        if(Gdx.input.isTouched()){
            float x = Gdx.input.getX()/PulbicVariables.PPM;
            float y = Gdx.input.getY()/PulbicVariables.PPM;

            gameCam.unproject(new Vector3(x,y,0));
            for (Soldier so:soldier
                 ) {
                float xo =so.getX();
                float yo = so.getY();

                float sizex = so.soldierSizeX;
                float sizey  = so.soldierSizeY;
                    if(so.getBoundingRectangle().contains(x,(PulbicVariables.SCREEN_HEIGHT/PulbicVariables.PPM)-y)){
                        so.isParachuteOn = false;
                    }
            }
        }
    }
    @Override
    public void render(float delta) {

        update(delta);
        handleInput(delta);
        Gdx.gl.glClearColor(0,191,255,1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        sea.draw(game.batch);
        clouds.draw(game.batch);
        clouds.moveCloud();
        boat.draw(game.batch);
        boat.moveBoat();
        for (Soldier soldier:soldier
             ) {
            soldier.draw(game.batch);
            soldier.animate(elapsedTime);
        }
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        //drawing box2d renderer
      //  box2DDebugRenderer.render(world,gameCam.combined);


        applyForce();

        hud.update();
        checkForGameOver();

    }
    public void checkForGameOver(){
        if(PulbicVariables.lives <= 0){
            //change screen to gameover screen
            game.setScreen(new MainMenuScreen(game));
            preferences = Gdx.app.getPreferences("game");
            if(preferences.getInteger("high") < PulbicVariables.Score){
                preferences.putInteger("high",PulbicVariables.Score).flush();
            }

        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        atlas.dispose();
        hud.dispose();
        world.dispose();

    }
}
