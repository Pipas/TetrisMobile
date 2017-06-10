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

public class MenuScreen extends ScreenAdapter
{

    private SpriteBatch batch;
    private Stage stage;
    private Texture background, top, bot;
    private BitmapFont font;
    private TextButton.TextButtonStyle ts;
    private TextButton startButton, scoreButton;
    private Label title;

    @Override
    public void show()
    {
        batch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        initiateFont();

        initiateTextures();

        initiateButtonStyle();

        initiateStartButton();

        initiateScoreButton();

        initiateTitleLabel();

        stage.addActor(startButton);
        stage.addActor(scoreButton);
        stage.addActor(title);
    }

    private void initiateTextures()
    {
        background = new Texture("ui/mainMenuBackground.png");
        top = new Texture("ui/mainMenuTop.png");
        bot = new Texture("ui/mainMenuBottom.png");
    }

    private void initiateFont()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/prstart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (0.04f * Gdx.graphics.getHeight());
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
    }

    private void initiateTitleLabel()
    {
        Label.LabelStyle ls = new Label.LabelStyle(font, Color.WHITE);
        title = new Label("TETRIS\nMOBILE", ls);
        title.setPosition(0.5f* GameTetris.VIEWPORT_WIDTH - title.getWidth()/2*1.6f, 0.8f*GameTetris.VIEWPORT_HEIGHT);
        title.setFontScaleY(1.6f);
        title.setFontScaleX(1.6f);
    }

    private void initiateScoreButton()
    {
        scoreButton = new TextButton("HIGH\nSCORES", ts);
        scoreButton.setPosition(0.2f* GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        scoreButton.setSize(0.6f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        scoreButton.pad(GameTetris.VIEWPORT_WIDTH/20);
        scoreButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.input.vibrate(25);
                GameTetris.get().setScreen(new ScoreScreen());
            }
        });
    }

    private void initiateButtonStyle()
    {
        SpriteDrawable spriteDrawable = new SpriteDrawable(new Sprite(new Texture("ui/buttonBackground.png")));
        ts = new TextButton.TextButtonStyle(spriteDrawable, spriteDrawable, spriteDrawable, font);
        ts.pressedOffsetX = 3;
        ts.pressedOffsetY = -3;
    }

    private void initiateStartButton()
    {
        startButton = new TextButton("START", ts);
        startButton.setPosition(0.2f* GameTetris.VIEWPORT_WIDTH, 0.5f*GameTetris.VIEWPORT_HEIGHT);
        startButton.setSize(0.6f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        startButton.pad(GameTetris.VIEWPORT_WIDTH/20);
        startButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.input.vibrate(25);
                GameTetris.get().setScreen(new GameScreen());
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0,  Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        batch.draw(top, 0, Gdx.graphics.getHeight() - Gdx.graphics.getWidth()*0.084f,  Gdx.graphics.getWidth(),  Gdx.graphics.getWidth()*0.084f);
        batch.draw(bot, 0, 0,  Gdx.graphics.getWidth(),  Gdx.graphics.getWidth()*0.084f);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose()
    {
        background.dispose();
        top.dispose();
        bot.dispose();
        font.dispose();
        stage.dispose();
        batch.dispose();
    }
}
