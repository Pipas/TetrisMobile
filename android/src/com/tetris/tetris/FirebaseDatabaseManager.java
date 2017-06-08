package com.tetris.tetris;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetris.score.DatabaseManager;
import com.tetris.score.Highscore;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pipas_ on 06/06/2017.
 */

public class FirebaseDatabaseManager implements DatabaseManager
{
    private DatabaseReference mDatabase;
    private ValueEventListener scoreListener;

    public boolean connect()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return true;
    }

    public boolean addScore(Highscore newScore)
    {
        getScores();

        if(Highscore.getScoreList().size() >= 20)
        {
            ArrayList<Highscore> load = Highscore.getScoreList();
            Collections.sort(load);
            if(newScore.getScore() > load.get(load.size() - 1).getScore())
            {
                mDatabase.child("scores").child(Integer.toString(load.get(load.size() - 1).hashCode())).removeValue();
                mDatabase.child("scores").child(Integer.toString(newScore.hashCode())).setValue(newScore);
            }
            else return false;
        }
        else
            mDatabase.child("scores").child(Integer.toString(newScore.hashCode())).setValue(newScore);

        return true;
    }

    public boolean getScores()
    {
        scoreListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Highscore.getScoreList().clear();
                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    Highscore.getScoreList().add(d.getValue(Highscore.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };

        mDatabase.child("scores").addValueEventListener(scoreListener);

        return true;
    }


}
