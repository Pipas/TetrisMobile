package com.tetris.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class MenuScreen extends ScreenAdapter {
    private Texture menu;
    private SpriteBatch batch;

    @Override
    public void show(){
        menu = new Texture("inserir nome ecra aqui");
        batch = new SpriteBatch();
    }
    @Override
    public void render(float delta){
        batch.draw(menu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
