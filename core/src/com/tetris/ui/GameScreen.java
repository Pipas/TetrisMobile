package com.tetris.ui;

import com.badlogic.gdx.Gdx;
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
    private SpriteBatch batch;
    private Sprite square, backsprite, infosprite;
    private Texture darkTone, midTone, lightTone, background, info;
    private GameState gameState;
    private int count = 0;
    private int strafeCount = 0;
    private boolean boost = false;
    private int speed = 30;
    private int level = 1;
    private float FAST_DROP_ZONE;
    private float BLOCK_SIZE;
    private float HEADER_HEIGHT;
    private float BOARD_HEIGHT, BOARD_WIDTH;
    private float START_X, START_Y, START_X_BOARD;
    private float NEXT_PIECE_MIDDLE_X, NEXT_PIECE_MIDDLE_Y;
    private float SCORE_MIDDLE_X, SCORE_MIDDLE_Y;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;
    private GlyphLayout layout;
    private Music music;
    private Sound strafe, rotate, drop, line;
    private Boolean dropPlayed = false;
    private Boolean linePlayed = false;

    @Override
    public void show(){
        batch = new SpriteBatch();

        gameState = new GameState();

        initiateTextures(1);
        background = new Texture("ui/gameBackground.png");
        info = new Texture("ui/infoBackground.png");
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
        NEXT_PIECE_MIDDLE_X = 0.067f * Gdx.graphics.getWidth() + (0.882f * Gdx.graphics.getWidth())*0.75f;
        NEXT_PIECE_MIDDLE_Y = Gdx.graphics.getHeight() - HEADER_HEIGHT/2;
        SCORE_MIDDLE_X = 0.067f * Gdx.graphics.getWidth() + (0.882f * Gdx.graphics.getWidth())*0.25f;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/prstart.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.characters = "0123456789";
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);

        layout = new GlyphLayout();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameMusic.wav"));
        music.setLooping(true);
        music.setVolume(0.1f);

        rotate = Gdx.audio.newSound(Gdx.files.internal("sounds/rotate.wav"));
        strafe = Gdx.audio.newSound(Gdx.files.internal("sounds/strafe.wav"));
        drop = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.wav"));
        line = Gdx.audio.newSound(Gdx.files.internal("sounds/line.wav"));

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.42745f,0.42745f,0.42745f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    private void drawScore()
    {
        layout.setText(font, Integer.toString(gameState.getScore()), Color.WHITE, Gdx.graphics.getWidth()/2, Align.center, true);
        font.draw(batch, layout, 0, NEXT_PIECE_MIDDLE_Y);
    }

    private void drawBoardState()
    {
        for(int x = 0; x < 10; x ++)
        {
            for(int y = 0; y < 15; y++)
            {
                square = getSquareSprite(gameState.getDynamicBoard()[y][x]);
                if(gameState.getDynamicBoard()[y][x] != ' ')
                    batch.draw(square, START_X + x*BLOCK_SIZE, START_Y + y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
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

    private void drawUiSprites() {
        batch.draw(infosprite, 0, Gdx.graphics.getHeight() - HEADER_HEIGHT, Gdx.graphics.getWidth(), HEADER_HEIGHT);
        batch.draw(backsprite, START_X_BOARD , 0, BOARD_WIDTH , BOARD_HEIGHT);
        layout.setText(font, Integer.toString(gameState.getScore()), Color.WHITE, Gdx.graphics.getWidth(), Align.left, true);
    }

    private void checkGameOver()
    {
        if(gameState.checkGameOver())
        {
            music.stop();
            GameTetris.get().setScreen(new GameOverScreen(gameState.getScore()));
        }
    }

    private void checkIfLineDeleted()
    {
        if(gameState.wasLineDeleted() && !linePlayed)
        {
            line.play();
            Gdx.input.vibrate(100);
            linePlayed = true;
        }
    }

    private void checkIfPieceLocked()
    {
        if(gameState.isPieceLocked() && !dropPlayed)
        {
            drop.play();
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
                    strafe.play();
            }
            else if (Gdx.input.getAccelerometerX() <= -0.75)
            {
                if(gameState.strafeRight())
                    strafe.play();
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
            return NEXT_PIECE_MIDDLE_X - 3 * BLOCK_SIZE;
        else if(representation == 'S')
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
        if(representation == 'T' || representation == 't')
            return new Sprite(lightTone);
        else if(representation == 'O' || representation == 'o')
            return new Sprite(lightTone);
        else if(representation == 'J' || representation == 'j')
            return new Sprite(darkTone);
        else if(representation == 'Z' || representation == 'z')
            return new Sprite(midTone);
        else if(representation == 'L' || representation == 'l')
            return new Sprite(midTone);
        else if(representation == 'S' || representation == 's')
            return new Sprite(darkTone);
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
        rotate.dispose();
        strafe.dispose();
        drop.dispose();
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button)
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
                rotate.play();
            }
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
