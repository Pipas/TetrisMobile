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
    private BitmapFont font;
    private Label title;
    private String name;
    private int score;
    private Label.LabelStyle labelStyle;

    public GameOverScreen(int score)
    {
        this.score = score;
    }

    @Override
    public void show()
    {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        initiateFont();

        initiateTextures();

        initiateButton();

        initiateTitleLabel();

        launchTextInput();

        stage.addActor(mainButton);
        stage.addActor(title);
    }

    private void launchTextInput()
    {
        Input.TextInputListener textListener = new Input.TextInputListener()
        {
            @Override
            public void input(String input)
            {
                name = input;
                name = name.substring(0, Math.min(name.length(), 3));
                name = name.toUpperCase();

                initiateOutputLabels();

                GameTetris.get().getDatabaseManager().addScore(new Highscore(name, score));
            }

            @Override
            public void canceled()
            {
                System.out.println("Aborted");
            }
        };

        Gdx.input.getTextInput(textListener, "Enter your initials", "", "3 letter max");
    }

    private void initiateOutputLabels()
    {
        Label scoreOutput = new Label(Integer.toString(score), labelStyle);
        scoreOutput.setFontScaleY(2);
        scoreOutput.setFontScaleX(2);
        scoreOutput.setPosition(0.5f* GameTetris.VIEWPORT_WIDTH - scoreOutput.getWidth(), 0.5f*GameTetris.VIEWPORT_HEIGHT);
        stage.addActor(scoreOutput);

        Label nameOutput = new Label(name, labelStyle);
        nameOutput.setFontScaleY(2);
        nameOutput.setFontScaleX(2);
        nameOutput.setPosition(0.5f*GameTetris.VIEWPORT_WIDTH - nameOutput.getWidth(), 0.5f*GameTetris.VIEWPORT_HEIGHT + scoreOutput.getHeight() * 2.2f);
        stage.addActor(nameOutput);
    }

    private void initiateTitleLabel()
    {
        labelStyle = new Label.LabelStyle(font, Color.WHITE);
        title = new Label("GAME\nOVER", labelStyle);
        title.setPosition(0.5f* GameTetris.VIEWPORT_WIDTH - title.getWidth()/2*1.6f, 0.8f*GameTetris.VIEWPORT_HEIGHT);
        title.setFontScaleY(1.6f);
        title.setFontScaleX(1.6f);
    }

    private void initiateButton()
    {
        SpriteDrawable spriteDrawable = new SpriteDrawable(new Sprite(new Texture("ui/buttonBackground.png")));
        TextButton.TextButtonStyle ts = new TextButton.TextButtonStyle(spriteDrawable, spriteDrawable, spriteDrawable, font);
        ts.pressedOffsetX = 3;
        ts.pressedOffsetY = -3;

        mainButton = new TextButton("MAIN\nMENU", ts);

        mainButton.setPosition(0.2f* GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        mainButton.setSize(0.6f*GameTetris.VIEWPORT_WIDTH, 0.2f*GameTetris.VIEWPORT_HEIGHT);
        mainButton.pad(GameTetris.VIEWPORT_WIDTH/20);
        mainButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.input.vibrate(25);
                GameTetris.get().setScreen(new MenuScreen());
            }
        });
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
        parameter.size = 50;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
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
}

