package com.gamoflaskcatchthesoldier.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Font;

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    Label score;
    Label lives;
    private int scoreCount;

    public Hud(SpriteBatch spriteBatch){
        //creating freestyle font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.WHITE;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font12;



        scoreCount = PulbicVariables.Score;

        viewport = new FitViewport(PulbicVariables.SCREEN_WIDTH,PulbicVariables.SCREEN_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,spriteBatch);
        score = new Label(String.format("SCORE: %d",scoreCount),style);
        lives = new Label(String.format("LIVES: &d",PulbicVariables.lives),style);
        lives.setPosition(0,0);
      //  score.setPosition(0,(0+Gdx.graphics.getHeight() - score.getHeight()));
        score.setPosition(PulbicVariables.SCREEN_WIDTH/2-20,PulbicVariables.SCREEN_HEIGHT/2);
        score.setScale(10,10);

        stage.addActor(score);
        stage.addActor(lives);
    }
    public void update(){
        score.setText(String.format("SCORE: %d",PulbicVariables.Score));
        lives.setText(String.format("LIVES: %d",PulbicVariables.lives));

    }
    public void dispose(){
        stage.dispose();


    }

}
