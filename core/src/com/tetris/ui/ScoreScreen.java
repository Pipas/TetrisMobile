package com.tetris.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tetris.score.Highscore;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class ScoreScreen extends ScreenAdapter
{

    private SpriteBatch batch;
    private Stage stage;
    private Texture background, top, bot;
    private BitmapFont font;
    private Label.LabelStyle labelStyle;
    private Label title;
    private Table table;
    private ScrollPane pane;
    private TextButton backButton;

    @Override
    public void show()
    {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        initiateFont();

        initiateTextures();

        initialilizeTitle();

        initializeTable();

        initializePane();

        initializeButton();

        stage.addActor(title);
        stage.addActor(pane);
        stage.addActor(backButton);

    }

    private void initializeButton()
    {
        TextButton.TextButtonStyle ts = new TextButton.TextButtonStyle(null, null, null, font);
        ts.pressedOffsetX = 2;
        ts.pressedOffsetY = -2;
        backButton = new TextButton("BACK", ts);
        backButton.setPosition(0.1f* GameTetris.VIEWPORT_WIDTH, 0.025f*GameTetris.VIEWPORT_HEIGHT);
        backButton.setSize(0.3f*GameTetris.VIEWPORT_WIDTH, 0.10f*GameTetris.VIEWPORT_HEIGHT);
        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.input.vibrate(25);
                GameTetris.get().setScreen(new MenuScreen());
            }
        });
    }

    private void initializePane()
    {
        pane  = new ScrollPane(table);
        pane.setPosition(0.05f* GameTetris.VIEWPORT_WIDTH, 0.125f*GameTetris.VIEWPORT_HEIGHT);
        pane.setSize(0.9f*GameTetris.VIEWPORT_WIDTH, 0.70f*GameTetris.VIEWPORT_HEIGHT);
    }

    private void initializeTable()
    {
        table = new Table();
        table.pad(Value.percentHeight(100f));

        for (int i = 0; i < Highscore.getScoreList().size(); i++)
        {
            ArrayList<Highscore> load = Highscore.getScoreList();
            Collections.sort(load);
            Label scores1 = new Label(load.get(i).getName(), labelStyle);
            Label scores2 = new Label(Integer.toString(load.get(i).getScore()), labelStyle);
            table.add(scores1).pad(Value.percentHeight(0.2f, scores1));
            table.add(scores2).pad(Value.percentHeight(0.2f, scores2));
            table.row();
        }
    }

    private void initialilizeTitle()
    {
        title = new Label("HIGHSCORES", labelStyle);
        title.setPosition(0.5f* GameTetris.VIEWPORT_WIDTH - title.getWidth()/2*1.1f, 0.875f*GameTetris.VIEWPORT_HEIGHT);
        title.setFontScaleY(1.2f);
        title.setFontScaleX(1.1f);
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
        labelStyle = new Label.LabelStyle(font, Color.WHITE);
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

        if (Gdx.input.isKeyPressed(Input.Keys.BACK))
        {
            GameTetris.get().setScreen(new MenuScreen());
        }

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
