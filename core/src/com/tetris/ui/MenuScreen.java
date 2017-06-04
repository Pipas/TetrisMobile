package com.tetris.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class MenuScreen extends ScreenAdapter {

    //To delete
    private Texture menu;
    private SpriteBatch batch;

    private Stage stage;
    private TextButton startButton, scoreButton;
    private BitmapFont titleFont, buttonFont;
    private Label title;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    @Override
    public void show(){
        //To delete
        //menu = new Texture("inserir nome ecra aqui");
        batch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("XcelsionItalic.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(Color.BLACK);
        buttonFont = titleFont;

        SpriteDrawable spriteDrawable = new SpriteDrawable(new Sprite(new Texture("buttonBackground.png")));
        TextButton.TextButtonStyle ts = new TextButton.TextButtonStyle(spriteDrawable, spriteDrawable, spriteDrawable, buttonFont);
        ts.pressedOffsetX = 3;
        ts.pressedOffsetY = -3;

        startButton = new TextButton("START", ts);
        scoreButton = new TextButton("HIGHSCORES", ts);
        startButton.setPosition(0.2f*GameTetris.VIEWPORT_WIDTH, 0.5f*GameTetris.VIEWPORT_HEIGHT);
        scoreButton.setPosition(0.2f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        startButton.setSize(0.6f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        scoreButton.setSize(0.6f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        startButton.pad(GameTetris.VIEWPORT_WIDTH/20);
        scoreButton.pad(GameTetris.VIEWPORT_WIDTH/20);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameTetris.get().setScreen(new GameScreen());
            }
        });

        Label.LabelStyle ls = new Label.LabelStyle(titleFont, Color.BLACK);
        title = new Label("Tetris Mobile", ls);
        title.setFontScaleY(2);
        title.setFontScaleX(1.2f);
        title.setPosition(0.1f*GameTetris.VIEWPORT_WIDTH, 0.8f*GameTetris.VIEWPORT_HEIGHT);

        stage.addActor(startButton);
        stage.addActor(scoreButton);
        stage.addActor(title);

    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }
}
