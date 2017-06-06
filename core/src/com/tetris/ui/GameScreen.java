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
        NEXT_PIECE_MIDDLE_X = 0.067f * Gdx.graphics.getWidth() + (0.882f * Gdx.graphics.getWidth())*0.75f;
        NEXT_PIECE_MIDDLE_Y = Gdx.graphics.getHeight() - HEADER_HEIGHT/2;
        SCORE_MIDDLE_X = 0.067f * Gdx.graphics.getWidth() + (0.882f * Gdx.graphics.getWidth())*0.25f;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("prstart.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.characters = "0123456789";
        font = generator.generateFont(parameter);

        layout = new GlyphLayout();

        music = Gdx.audio.newMusic(Gdx.files.internal("music1.wav"));
        music.setLooping(true);
        music.setVolume(0.1f);

        rotate = Gdx.audio.newSound(Gdx.files.internal("rotate.wav"));
        strafe = Gdx.audio.newSound(Gdx.files.internal("strafe.wav"));
        drop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        line = Gdx.audio.newSound(Gdx.files.internal("line.wav"));

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.42745f,0.42745f,0.42745f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        music.play();

        batch.begin();

        batch.draw(infosprite, 0, Gdx.graphics.getHeight() - HEADER_HEIGHT, Gdx.graphics.getWidth(), HEADER_HEIGHT);
        batch.draw(backsprite, START_X_BOARD , 0, BOARD_WIDTH , BOARD_HEIGHT);
        layout.setText(font, Integer.toString(gameState.getScore()), Color.WHITE, Gdx.graphics.getWidth(), Align.left, true);

        for(int i = 0; i < 4; i++)
        {
            char representation = gameState.getNextPiece().getPermanentChar();
            square = getSquareSprite(representation);
            batch.draw(square, getNextPiecePositionX(representation) + gameState.getNextPiece().getSquare(i).getX() * BLOCK_SIZE, getNextPiecePositionY(representation) + gameState.getNextPiece().getSquare(i).getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }

        for(int x = 0; x < 10; x ++)
        {
            for(int y = 0; y < 15; y++)
            {
                square = getSquareSprite(gameState.getDynamicBoard()[y][x]);
                if(gameState.getDynamicBoard()[y][x] != ' ')
                    batch.draw(square, START_X + x*BLOCK_SIZE, START_Y + y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        font.setColor(Color.WHITE);
        layout.setText(font, Integer.toString(gameState.getScore()), Color.WHITE, Gdx.graphics.getWidth()/2, Align.center, true);
        font.draw(batch, layout, 0, NEXT_PIECE_MIDDLE_Y);

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
            {
                gameState.strafeLeft();
                strafe.play();
            }
            else if (Gdx.input.getAccelerometerX() <= -0.75)
            {
                gameState.strafeRight();
                strafe.play();
            }
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
            dropPlayed = false;
            linePlayed = false;
        }

        if(gameState.getLevel() <= 5)
            speed = BASE_SPEED - (gameState.getLevel() - 1) * 5;
        else
            speed = BASE_SPEED - 15 - gameState.getLevel();

        if(gameState.getFallingPiece().isDone() && !dropPlayed)
        {
            drop.play();
            dropPlayed = true;
        }

        if(gameState.wasLineDeleted() && !linePlayed)
        {
            line.play();
            linePlayed = true;
        }

        if(gameState.checkGameOver())
        {
            music.stop();
            GameTetris.get().setScreen(new GameOverScreen(gameState.getScore()));
        }
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
            return new Sprite(white);
        else if(representation == 'S' || representation == 's')
            return new Sprite(white);
        else if(representation == 'K' || representation == 'k')
            return new Sprite(blue);
        else if(representation == 'A' || representation == 'a')
            return new Sprite(green);
        else if(representation == 'L' || representation == 'l')
            return new Sprite(green);
        else if(representation == 'V' || representation == 'v')
            return new Sprite(blue);
        else
            return new Sprite(white);
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
            gameState.rotate();
            rotate.play();
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
