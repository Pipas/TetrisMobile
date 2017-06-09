package com.tetris.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.tetris.game.GameState;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class GameScreen extends ScreenAdapter implements InputProcessor
{
    public enum State
    {
        PAUSE,
        RUN
    }

    private SpriteBatch batch;
    private Sprite square, backsprite, infosprite;
    private Texture darkTone, midTone, lightTone, background, info;
    private GameState gameState;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;
    private GlyphLayout layout;
    private Music music;
    private Sound strafeSound, rotateSound, dropSound, lineSound, gameoversound, pausesound;
    private boolean dropPlayed = false;
    private boolean linePlayed = false;
    private boolean boost = false;
    private int count = 0, strafeCount = 0, speed = 30, level = 1;
    private float FAST_DROP_ZONE;
    private float BLOCK_SIZE;
    private float HEADER_HEIGHT;
    private float BOARD_HEIGHT, BOARD_WIDTH;
    private float START_X_BLOCKS, START_Y_BLOCKS, START_X_BOARD;
    private float NEXT_PIECE_MIDDLE_X, NEXT_PIECE_MIDDLE_Y;
    private State STATE = State.RUN;

    @Override
    public void show()
    {
        batch = new SpriteBatch();
        gameState = new GameState();
        initiateTextures(1);

        initiateBackground();

        initiateGameDimentions();

        initiateFont();

        layout = new GlyphLayout();

        initiateSounds();

        initiateMusic(1);

        Gdx.input.setInputProcessor(this);
    }

    private void initiateSounds()
    {
        rotateSound = Gdx.audio.newSound(Gdx.files.internal("sounds/rotate.wav"));
        strafeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/strafe.wav"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.wav"));
        lineSound = Gdx.audio.newSound(Gdx.files.internal("sounds/line.wav"));
        gameoversound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        pausesound = Gdx.audio.newSound(Gdx.files.internal("sounds/pause.wav"));
    }

    private void initiateMusic(int i)
    {
        if(i == 1)
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music1.wav"));
        else if(i == 2)
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music2.wav"));
        else
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music3.wav"));
        music.setLooping(true);
        music.setVolume(0.1f);
    }

    private void initiateFont()
    {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/prstart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
    }

    private void initiateGameDimentions()
    {
        FAST_DROP_ZONE = Gdx.graphics.getHeight() * 0.8f;
        HEADER_HEIGHT = Gdx.graphics.getWidth()*0.336f;
        BOARD_HEIGHT = Gdx.graphics.getHeight() - HEADER_HEIGHT;
        BOARD_WIDTH = 0.704f * BOARD_HEIGHT;
        START_X_BOARD = (Gdx.graphics.getWidth() - BOARD_WIDTH)/2;
        BLOCK_SIZE = (BOARD_WIDTH * 0.832f)/10f;
        START_X_BLOCKS = (0.084f * BOARD_WIDTH) + START_X_BOARD;
        START_Y_BLOCKS = 0.059f * BOARD_HEIGHT;
        NEXT_PIECE_MIDDLE_X = 0.067f * Gdx.graphics.getWidth() + (0.882f * Gdx.graphics.getWidth())*0.75f;
        NEXT_PIECE_MIDDLE_Y = Gdx.graphics.getHeight() - HEADER_HEIGHT/2;
    }

    private void initiateBackground()
    {
        background = new Texture("ui/gameBackground.png");
        info = new Texture("ui/infoBackground.png");
        backsprite = new Sprite(background);
        infosprite = new Sprite(info);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.42745f,0.42745f,0.42745f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(STATE == State.RUN)
        {

            music.play();

            batch.begin();

            drawUiSprites();

            drawNextPiece();

            drawBoardState();

            drawScore();

            batch.end();

            checkAccelerometer();

            tryStrafe();

            tryBoost();

            adjustSpeed();

            checkIfPieceLocked();

            checkIfLineDeleted();

            checkGameOver();

            count++;
        }
        else if(STATE == State.PAUSE)
        {
            music.pause();

            batch.begin();

            drawUiSprites();

            drawPause();

            batch.end();
        }
    }

    private void drawScore()
    {
        layout.setText(font, Integer.toString(gameState.getScore()), Color.WHITE, Gdx.graphics.getWidth()/2, Align.center, true);
        font.draw(batch, layout, 0, NEXT_PIECE_MIDDLE_Y + layout.height/2);
    }

    private void drawPause()
    {
        layout.setText(font, "PAUSED", Color.WHITE, Gdx.graphics.getWidth(), Align.center, true);
        font.draw(batch, layout, 0, NEXT_PIECE_MIDDLE_Y + layout.height/2);
    }

    private void drawBoardState()
    {
        for(int x = 0; x < 10; x ++)
        {
            for(int y = 0; y < 15; y++)
            {
                square = getSquareSprite(gameState.getDynamicBoard()[y][x]);
                if(gameState.getDynamicBoard()[y][x] != ' ')
                    batch.draw(square, START_X_BLOCKS + x*BLOCK_SIZE, START_Y_BLOCKS + y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    private void drawNextPiece()
    {
        char representation = gameState.getNextPiece().getPermanentChar();
        square = getSquareSprite(representation);
        for(int i = 0; i < 4; i++)
            batch.draw(square, getNextPiecePositionX(representation) + gameState.getNextPiece().getSquare(i).getX() * BLOCK_SIZE, getNextPiecePositionY(representation) + gameState.getNextPiece().getSquare(i).getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }

    private void drawUiSprites()
    {
        batch.draw(infosprite, 0, Gdx.graphics.getHeight() - HEADER_HEIGHT, Gdx.graphics.getWidth(), HEADER_HEIGHT);
        batch.draw(backsprite, START_X_BOARD , 0, BOARD_WIDTH , BOARD_HEIGHT);
    }

    private void checkGameOver()
    {
        if(gameState.checkGameOver())
        {
            gameoversound.play();
            music.stop();
            GameTetris.get().setScreen(new GameOverScreen(gameState.getScore()));
        }
    }

    private void checkIfLineDeleted()
    {
        if(gameState.wasLineDeleted() && !linePlayed)
        {
            lineSound.play();
            Gdx.input.vibrate(100);
            linePlayed = true;
        }
    }

    private void checkIfPieceLocked()
    {
        if(gameState.isPieceLocked() && !dropPlayed)
        {
            dropSound.play();
            Gdx.input.vibrate(25);
            dropPlayed = true;
        }
    }

    private void adjustSpeed()
    {
        if(level != gameState.getLevel())
        {
            level = gameState.getLevel();
            initiateTextures(level);
            speed = (int) (30 - 11.377 * Math.log(level));
            if(level == 4)
            {
                music.stop();
                initiateMusic(2);
                music.play();
            }
            else if(level == 7)
            {
                music.stop();
                initiateMusic(3);
                music.play();
            }
        }
    }

    private void tryBoost()
    {
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
            dropPlayed = false;
            linePlayed = false;
        }
    }

    private void tryStrafe()
    {
        if(strafeCount >= 10)
        {
            if (Gdx.input.getAccelerometerX() >= 0.75)
            {
                if(gameState.strafeLeft())
                    strafeSound.play();
            }
            else if (Gdx.input.getAccelerometerX() <= -0.75)
            {
                if(gameState.strafeRight())
                    strafeSound.play();
            }
            strafeCount = 0;
        }
    }

    private void checkAccelerometer()
    {
        if (Math.abs(Gdx.input.getAccelerometerX()) < 0.75)
            strafeCount = 0;
        else if (Math.abs(Gdx.input.getAccelerometerX()) >= 0.75 && Math.abs(Gdx.input.getAccelerometerX()) < 1.5)
            strafeCount++;
        else
            strafeCount += 2;
    }

    private float getNextPiecePositionX(char representation)
    {
        if(representation == 'I')
            return NEXT_PIECE_MIDDLE_X - 2 * BLOCK_SIZE;
        else if(representation == 'O')
            return NEXT_PIECE_MIDDLE_X - BLOCK_SIZE;
        else
            return NEXT_PIECE_MIDDLE_X - 1.5f * BLOCK_SIZE;
    }

    private float getNextPiecePositionY(char representation)
    {
        if(representation == 'I')
            return NEXT_PIECE_MIDDLE_Y - 0.5f * BLOCK_SIZE;
        else
            return NEXT_PIECE_MIDDLE_Y - BLOCK_SIZE;
    }

    private Sprite getSquareSprite(char representation)
    {
        if(representation == 'J' || representation == 'j' || representation == 'S' || representation == 's')
            return new Sprite(darkTone);
        else if(representation == 'Z' || representation == 'z' || representation == 'L' || representation == 'l')
            return new Sprite(midTone);
        else
            return new Sprite(lightTone);
    }

    private void initiateTextures(int level)
    {
        lightTone = new Texture("sprites/" + level + "/light.png");
        darkTone = new Texture("sprites/" + level + "/dark.png");
        midTone = new Texture("sprites/" + level + "/mid.png");
    }

    @Override
    public void dispose ()
    {
        batch.dispose();
        lightTone.dispose();
        darkTone.dispose();
        midTone.dispose();
        background.dispose();
        info.dispose();
        generator.dispose();
        music.dispose();
        rotateSound.dispose();
        strafeSound.dispose();
        dropSound.dispose();
        lineSound.dispose();
        gameoversound.dispose();
        pausesound.dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button)
    {
        if(STATE == State.RUN)
        {
            if(screenY > FAST_DROP_ZONE)
            {
                boost = true;
            }
            else if(screenY < FAST_DROP_ZONE)
            {
                if(gameState.rotate())
                {
                    Gdx.input.vibrate(25);
                    rotateSound.play();
                }
            }
        }
        else
        {
            Gdx.input.vibrate(25);
        }
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
    public void resize (int width, int height) {}

    @Override
    public boolean keyDown (int keycode)
    {
        if(keycode == Input.Keys.BACK)
        {
            if(STATE == State.RUN)
            {
                STATE = State.PAUSE;
                pausesound.play();
            }
            else
            {
                STATE = State.RUN;
                pausesound.play();
            }
        }

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
