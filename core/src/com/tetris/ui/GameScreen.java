package com.tetris.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tetris.game.GameState;

import static java.awt.Color.orange;
import static java.awt.Color.pink;
import static java.awt.Color.red;
import static java.awt.Color.yellow;
import static javax.sound.midi.ShortMessage.START;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class GameScreen extends ScreenAdapter implements InputProcessor {
    private SpriteBatch batch;
    private Sprite square, backsprite, infosprite;
    private Texture blue, green, white, background, info;
    private GameState gameState;
    private int count = 0;
    private int strafeCount = 0;
    private boolean boost = false;
    private int speed;
    private int BASE_SPEED = 30;
    private float FAST_DROP_ZONE;
    private float BLOCK_SIZE;
    private float HEADER_HEIGHT;
    private float BOARD_HEIGHT, BOARD_WIDTH;
    private float START_X, START_Y, START_X_BOARD;

    @Override
    public void show(){
        batch = new SpriteBatch();

        gameState = new GameState();

        white = new Texture("whiteSquare.png");
        blue = new Texture("blueSquare.png");
        green = new Texture("greenSquare.png");
        background = new Texture("gameBackground.png");
        info = new Texture("infoBackground.png");
        backsprite = new Sprite(background);
        infosprite = new Sprite(info);

        FAST_DROP_ZONE = Gdx.graphics.getHeight() * 0.8f;
        HEADER_HEIGHT = Gdx.graphics.getWidth()*0.336f;
        BOARD_HEIGHT = Gdx.graphics.getHeight() - HEADER_HEIGHT;
        BOARD_WIDTH = 0.704f * BOARD_HEIGHT;
        START_X_BOARD = (Gdx.graphics.getWidth() - BOARD_WIDTH)/2;
        BLOCK_SIZE = (BOARD_WIDTH * 0.832f)/10f;
        START_X = (0.084f * BOARD_WIDTH) + START_X_BOARD;
        START_Y = 0.059f * BOARD_HEIGHT;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.42745f,0.42745f,0.42745f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(infosprite, 0, Gdx.graphics.getHeight() - HEADER_HEIGHT, Gdx.graphics.getWidth(), HEADER_HEIGHT);
        batch.draw(backsprite, START_X_BOARD , 0, BOARD_WIDTH , BOARD_HEIGHT);

        for(int x = 0; x < 10; x ++)
        {
            for(int y = 0; y < 15; y++)
            {
                if(gameState.getDynamicBoard()[y][x] == 'T' || gameState.getDynamicBoard()[y][x] == 't')
                    square = new Sprite(white);
                else if(gameState.getDynamicBoard()[y][x] == 'S' || gameState.getDynamicBoard()[y][x] == 's')
                    square = new Sprite(white);
                else if(gameState.getDynamicBoard()[y][x] == 'K' || gameState.getDynamicBoard()[y][x] == 'k')
                    square = new Sprite(blue);
                else if(gameState.getDynamicBoard()[y][x] == 'A' || gameState.getDynamicBoard()[y][x] == 'a')
                    square = new Sprite(green);
                else if(gameState.getDynamicBoard()[y][x] == 'L' || gameState.getDynamicBoard()[y][x] == 'l')
                    square = new Sprite(green);
                else if(gameState.getDynamicBoard()[y][x] == 'V' || gameState.getDynamicBoard()[y][x] == 'v')
                    square = new Sprite(blue);
                else
                    square = new Sprite(white);

                if(gameState.getDynamicBoard()[y][x] != ' ')
                    batch.draw(square, START_X + x*BLOCK_SIZE, START_Y + y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        batch.end();

        if (Math.abs(Gdx.input.getAccelerometerX()) < 0.75)
            strafeCount = 0;
        else if (Math.abs(Gdx.input.getAccelerometerX()) >= 0.75 && Math.abs(Gdx.input.getAccelerometerX()) < 1.5)
        {
            strafeCount++;
        }
        else
        {
            strafeCount += 2;
        }

        if(strafeCount >= 10)
        {
            if (Gdx.input.getAccelerometerX() >= 0.75)
                gameState.strafeLeft();
            else if (Gdx.input.getAccelerometerX() <= -0.75)
                gameState.strafeRight();
            strafeCount = 0;
        }

        count++;

        if(boost)
        {
            if(count >= 3)
            {
                count = 0;
                gameState.advance();
            }
        }
        else if(count >= speed)
        {
            count = 0;
            gameState.advance();
        }

        if(gameState.getLevel() <= 5)
            speed = BASE_SPEED - (gameState.getLevel() - 1) * 5;
        else
            speed = BASE_SPEED - 15 - gameState.getLevel();

        if(gameState.checkGameOver())
            gameState = new GameState();
    }

    @Override
    public void dispose ()
    {
        batch.dispose();
        white.dispose();
        blue.dispose();
        green.dispose();
        background.dispose();
        info.dispose();
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button)
    {
        if(screenY > FAST_DROP_ZONE)
        {
            boost = true;
        }
        else if(screenY < FAST_DROP_ZONE)
            gameState.rotate();
        return true;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button)
    {
        boost = false;
        return true;
    }

    @Override
    public void resize (int width, int height)
    {
        return;
    }

    @Override
    public boolean keyDown (int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp (int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped (char character)
    {
        return false;
    }

    @Override
    public boolean scrolled (int amount)
    {
        return false;
    }

    @Override
    public boolean mouseMoved (int screenX, int screenY)
    {
        return false;
    }
}
