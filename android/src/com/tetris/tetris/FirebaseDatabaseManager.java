package com.tetris.tetris;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetris.score.DatabaseManager;

/**
 * Created by Pipas_ on 06/06/2017.
 */

public class FirebaseDatabaseManager implements DatabaseManager
{
    private DatabaseReference mDatabase;

    public boolean connect()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return true;
    }

    public boolean addScore(int score)
    {
        mDatabase.child("scores").setValue(score);
        return true;
    }
}
