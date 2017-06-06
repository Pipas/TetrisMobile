package com.tetris.tetris;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tetris.score.DatabaseManager;
import com.tetris.ui.GameTetris;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		DatabaseManager databaseManager = new FirebaseDatabaseManager();
		initialize(new GameTetris(databaseManager), config);
	}
}
