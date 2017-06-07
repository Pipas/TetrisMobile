package com.tetris.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.tetris.score.Highscore;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class GameOverScreen extends ScreenAdapter
{
    private SpriteBatch batch;

    private Stage stage;
    private Texture background, top, bot;
    private TextButton mainButton;
    private BitmapFont titleFont, buttonFont;
    private Label title, scoreOutput;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private String name;
    private int score;

    public GameOverScreen(int score)
    {
        this.score = score;
    }

    @Override
    public void show(){
        batch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("prstart.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(Color.WHITE);
        buttonFont = titleFont;

        background = new Texture("mainMenuBackground.png");
        top = new Texture("mainMenuTop.png");
        bot = new Texture("mainMenuBottom.png");

        SpriteDrawable spriteDrawable = new SpriteDrawable(new Sprite(new Texture("buttonBackground.png")));
        TextButton.TextButtonStyle ts = new TextButton.TextButtonStyle(spriteDrawable, spriteDrawable, spriteDrawable, buttonFont);
        ts.pressedOffsetX = 3;
        ts.pressedOffsetY = -3;


        mainButton = new TextButton("MAIN\nMENU", ts);

        mainButton.setPosition(0.2f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);

        mainButton.setSize(0.6f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);

        mainButton.pad(GameTetris.VIEWPORT_WIDTH/20);
        mainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameTetris.get().setScreen(new MenuScreen());
            }
        });

        Label.LabelStyle ls = new Label.LabelStyle(titleFont, Color.WHITE);
        title = new Label("GAME\nOVER", ls);
        title.setPosition(0.5f*GameTetris.VIEWPORT_WIDTH - title.getWidth()/2*1.6f, 0.8f*GameTetris.VIEWPORT_HEIGHT);
        title.setFontScaleY(1.6f);
        title.setFontScaleX(1.6f);

        Input.TextInputListener textListener = new Input.TextInputListener()
        {
            @Override
            public void input(String input)
            {
                name = input;
                GameTetris.get().getDatabaseManager().addScore(new Highscore(name, score));
            }

            @Override
            public void canceled()
            {
                System.out.println("Aborted");
            }
        };

        Gdx.input.getTextInput(textListener, "Player Name: ", "", "max 5 lenght");

        //GameTetris.get().getDatabaseManager().addScore(new Highscore(name, score));

        scoreOutput = new Label("", ls);

        scoreOutput.setFontScaleY(1.6f);
        scoreOutput.setFontScaleX(1.6f);

        stage.addActor(mainButton);
        stage.addActor(title);
        stage.addActor(scoreOutput);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0,  Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        batch.draw(top, 0, Gdx.graphics.getHeight() - Gdx.graphics.getWidth()*0.084f,  Gdx.graphics.getWidth(),  Gdx.graphics.getWidth()*0.084f);
        batch.draw(bot, 0, 0,  Gdx.graphics.getWidth(),  Gdx.graphics.getWidth()*0.084f);
        batch.end();

        scoreOutput.setText(name);
        scoreOutput.setPosition(0.5f*GameTetris.VIEWPORT_WIDTH - scoreOutput.getWidth()/2*1.6f, 0.6f*GameTetris.VIEWPORT_HEIGHT);
        stage.addActor(scoreOutput);


        stage.act(delta);
        stage.draw();

    }
}

