package com.gamoflaskcatchthesoldier.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MainMenuScreen implements Screen {
    CatchTheSoldier game;
    AddInterface addInterface;
  /*  Stage stage;
    TextButton play;
    TextButton exit;
    TextButton.TextButtonStyle playStyle;
    TextButton.TextButtonStyle exitStyle;
    BitmapFont font;
    Skin skin;*/
    TextureAtlas buttonAtlas;

    TextureRegion play;
    TextureRegion playClicked;
    TextureRegion exit;
    TextureRegion exitClicked;
    float playX;
    float playY;
    float playWidth;
    float playHeight;
    float exitX;
    float eixtY;
    float exitWidth;
    float exitHeight;
    boolean isPlayButtonClicked = false;
    Label score;
    Label highScore;
    Stage stage;
    Viewport viewport;
    int highscore;
    int lastGameScore;
    Preferences preferences;






    MainMenuScreen( CatchTheSoldier game){
        this.game = game;
    /*    stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas("unnamed.atlas");
        skin.addRegions(buttonAtlas);
        playStyle = new TextButton.TextButtonStyle();
        playStyle.font = font;
        playStyle.up = skin.getDrawable("play");
        playStyle.down = skin.getDrawable("playclicked");
        exitStyle = new TextButton.TextButtonStyle();
        exitStyle.font = font;
        exitStyle.up = skin.getDrawable("exitnew");
        exitStyle.down = skin.getDrawable("exitclickednwe");
       // textButtonStyle.checked = skin.getDrawable("checked-button");
        play = new TextButton("Play", playStyle);
        exit = new TextButton("eixt",exitStyle);
        play.setPosition(PulbicVariables.SCREEN_WIDTH/5,PulbicVariables.SCREEN_HEIGHT/2);
        exit.setPosition(PulbicVariables.SCREEN_WIDTH/2,PulbicVariables.SCREEN_HEIGHT/2);
        //play.setScale(.2f,.2f);
        //exit.setScale(.2f,.2f);
      //  play.setSize(PulbicVariables.SCREEN_WIDTH/4,PulbicVariables.SCREEN_HEIGHT/4);
    //    exit.setSize(PulbicVariables.SCREEN_WIDTH/4,PulbicVariables.SCREEN_HEIGHT/4);
        stage.addActor(play);
        stage.addActor(exit);

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayScreen(game));
            }
        });
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

*/
        viewport = new FitViewport(PulbicVariables.SCREEN_WIDTH,PulbicVariables.SCREEN_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,game.batch);
        //creating custom font for the game
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.SKY;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font12;
        //last game score label generation
        lastGameScore = PulbicVariables.Score;
        score = new Label(String.format("LAST GAME'S SCORE: %d",lastGameScore),style);
        score.setPosition(PulbicVariables.SCREEN_WIDTH/2.5f,PulbicVariables.SCREEN_HEIGHT/5);

        //high score label generation
        preferences = Gdx.app.getPreferences("game");

        highscore = preferences.getInteger("high");
        highScore = new Label(String.format("HIGHSCORE: %d",highscore),style);
        highScore.setPosition(PulbicVariables.SCREEN_WIDTH/2.5f,PulbicVariables.SCREEN_HEIGHT -PulbicVariables.SCREEN_HEIGHT/5);
        stage.addActor(score);
        stage.addActor(highScore);






        buttonAtlas = new TextureAtlas("unnamed.atlas");
        play = buttonAtlas.findRegion("play");
        playClicked = buttonAtlas.findRegion("playclicked");
        exit = buttonAtlas.findRegion("exitnew");
        exitClicked = buttonAtlas.findRegion("exitclickednwe");
        playX = PulbicVariables.SCREEN_WIDTH/5;
        playY = PulbicVariables.SCREEN_HEIGHT/2;
        playHeight =PulbicVariables.SCREEN_HEIGHT/4;
        playWidth = PulbicVariables.SCREEN_WIDTH/4;
        exitX = PulbicVariables.SCREEN_WIDTH/2;
        eixtY = PulbicVariables.SCREEN_HEIGHT/2;
        exitWidth = PulbicVariables.SCREEN_WIDTH/4;
        exitHeight = PulbicVariables.SCREEN_HEIGHT/4;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        inputListener();
        Gdx.gl.glClearColor(0,0,0,1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(play,playX,playY,playWidth,playHeight);
        game.batch.draw(exit,exitX,eixtY,exitWidth,exitHeight);
        game.batch.end();
        stage.draw();



    }

    public void inputListener(){
        if(Gdx.input.isTouched()){
            float x = Gdx.input.getX();
            float y  = PulbicVariables.SCREEN_HEIGHT -Gdx.input.getY();
            if(x>playX && x <playX + playWidth && y > playY && y <playY + playHeight){
                play = playClicked;
                try{
                    Thread.sleep(200);
                }catch (Exception e){

                }
                PulbicVariables.lives = 10;
                PulbicVariables.BoatVeloctiy = 5f;


                game.setScreen(new PlayScreen(game));

            }
            if(x>exitX && x <exitX + exitWidth && y >eixtY && y <eixtY + exitHeight){
                exit = exitClicked;
                try{
                    Thread.sleep(200);
                }catch (Exception e){

                }
                Gdx.app.exit();
            }
        }
    }
    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
        buttonAtlas.dispose();



    }
}
