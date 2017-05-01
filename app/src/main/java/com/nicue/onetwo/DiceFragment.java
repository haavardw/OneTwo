package com.nicue.onetwo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DiceFragment extends android.support.v4.app.Fragment implements View.OnClickListener, DiceListAdapter.DiceAdapterOnClickHandler {
    private ArrayList<String> mItems = new ArrayList<>();
    private ArrayList<String> mFaces = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DiceListAdapter mListAdapter;
    private boolean started = false;
    private Handler handler = new Handler();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View diceView = inflater.inflate(R.layout.dice_layout, container, false);

        mRecyclerView = (RecyclerView) diceView.findViewById(R.id.recyclerview_dice);

        RecyclerView.LayoutManager layoutManager
                = new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mListAdapter = new DiceListAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mListAdapter);

        FloatingActionButton fab_dice = (FloatingActionButton) diceView.findViewById(R.id.fab_dice);
        fab_dice.setOnClickListener(this);
        mRecyclerView = (RecyclerView) diceView.findViewById(R.id.recyclerview_dice);
        readItems();
        mListAdapter.setmData(mFaces,mItems);
        mListAdapter.notifyDataSetChanged();
        return diceView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab_dice:
                fabDiceClick(v);
        }
    }

    @Override
    public void onClick(View v, int pos) {
        int id = v.getId();
        Log.d("Clicked", "onFragment");
        switch (id) {
            case R.id.throw_button:
                return;
        }
    }
    /*
    public void rollDice(View v) {
        int id = v.getId();
        Log.d("Clicked", "onFragment");
        switch (id) {
            case R.id.throw_button:
                //int max_dice = Integer.parseInt(items.get(pos));
                int max_dice = 6;
                Random r = new Random();
                int new_num = r.nextInt(max_dice);
                TextView rolledNumber = (TextView) v.findViewById(R.id.tv_dice);
                rolledNumber.setText(new_num);
        }
    }
    */

    public void fabDiceClick(View view) {
        final EditText et_dice = new EditText(getActivity());
        et_dice.setRawInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(et_dice)
                .setTitle("Dice\'s Faces:")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String faces = et_dice.getText().toString();
                        mFaces.add(faces);
                        mItems.add(faces);
                        Log.d("Items", String.valueOf(mItems));
                        mListAdapter.setmData(mFaces, mItems);
                        writeItems();

                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }

    /*
    private Runnable runnable = new Runnable() {
    @Override
    public void run() {
        final Random random = new Random();
        int i = random.nextInt(2 - 0 + 1) + 0;
        random_note.setImageResource(image[i]);
        if(started) {
            start();
            }
        }
    };

    public void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void start() {
        started = true;
        handler.postDelayed(runnable, 2000);
    }
    */


    private void readItems() {
        File filesDir = getActivity().getFilesDir();
        File todoFile = new File(filesDir, "dices.txt");
        try {
            mFaces = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            mFaces = new ArrayList<String>();
        }
        mItems.clear();
        for (int i = 0 ; i<mFaces.size(); i++)
        {mItems.add(mFaces.get(i));
        }
    }

    private void writeItems() {
        File filesDir = getActivity().getFilesDir();
        File todoFile = new File(filesDir, "dices.txt");
        try {
            FileUtils.writeLines(todoFile, mFaces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

