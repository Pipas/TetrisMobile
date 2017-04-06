package com.lpoo.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MainTetris extends ApplicationAdapter
{
	private SpriteBatch batch;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private BitmapFont font15;
	private Sprite sprite;
	private Texture img;

	private float BLOCK_SIZE;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("3Dventure.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 150;
		font15 = generator.generateFont(parameter);
		font15.setColor(Color.BLACK);

		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		BLOCK_SIZE = Gdx.graphics.getWidth() / 10;
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);


	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		for (int i = 0; i < 10; i++)
		{
			batch.draw(sprite, i*BLOCK_SIZE, sprite.getY(), BLOCK_SIZE, BLOCK_SIZE);
		}

		font15.draw(batch, "Hello World!", 10, 150);

		batch.end();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
		font15.dispose();
		generator.dispose();
		img.dispose();
	}
}
