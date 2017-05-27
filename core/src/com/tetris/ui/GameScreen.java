package com.tetris.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tetris.game.GameState;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class GameScreen extends ScreenAdapter implements InputProcessor {
    private SpriteBatch batch;
    private Sprite sprite;
    private Texture red, blue, green, orange, pink, yellow, teal;
    private GameState gameState;
    private int count = 0;
    private int strafeCount = 0;
    private boolean boost = false;
    private int speed;
    private int BASE_SPEED = 30;
    private float FAST_DROP_ZONE;
    private float BLOCK_SIZE;

    @Override
    public void show(){
        batch = new SpriteBatch();

        gameState = new GameState();

        red = new Texture("redHappySquare.png");
        blue = new Texture("blueHappySquare.png");
        orange = new Texture("orangeHappySquare.png");
        yellow = new Texture("yellowHappySquare.png");
        green = new Texture("greenHappySquare.png");
        pink = new Texture("pinkHappySquare.png");
        teal = new Texture("tealHappySquare.png");

        BLOCK_SIZE = Gdx.graphics.getWidth() / 10;
        FAST_DROP_ZONE = Gdx.graphics.getHeight() * 0.8f;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for(int x = 0; x < 10; x ++)
        {
            for(int y = 0; y < 15; y++)
            {
                if(gameState.getDynamicBoard()[y][x] == 'T' || gameState.getDynamicBoard()[y][x] == 't')
                    sprite = new Sprite(red);
                else if(gameState.getDynamicBoard()[y][x] == 'S' || gameState.getDynamicBoard()[y][x] == 's')
                    sprite = new Sprite(yellow);
                else if(gameState.getDynamicBoard()[y][x] == 'K' || gameState.getDynamicBoard()[y][x] == 'k')
                    sprite = new Sprite(orange);
                else if(gameState.getDynamicBoard()[y][x] == 'A' || gameState.getDynamicBoard()[y][x] == 'a')
                    sprite = new Sprite(green);
                else if(gameState.getDynamicBoard()[y][x] == 'L' || gameState.getDynamicBoard()[y][x] == 'l')
                    sprite = new Sprite(pink);
                else if(gameState.getDynamicBoard()[y][x] == 'V' || gameState.getDynamicBoard()[y][x] == 'v')
                    sprite = new Sprite(blue);
                else
                    sprite = new Sprite(teal);

                if(gameState.getDynamicBoard()[y][x] != ' ')
                    batch.draw(sprite, x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
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
        red.dispose();
        blue.dispose();
        green.dispose();
        yellow.dispose();
        orange.dispose();
        pink.dispose();
        teal.dispose();
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
