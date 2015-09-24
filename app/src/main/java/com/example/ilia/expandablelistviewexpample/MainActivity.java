package com.example.ilia.expandablelistviewexpample;

import android.app.ExpandableListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
    ExpandableListView expandableListView;
    GridView gridView;
    boolean mIsLvFocused;
    boolean mIsGvFocused;
    View mLastActivatedListView = null;
    View mLastActivatedGridView = null;
    int mLastLvPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        gridView = (GridView) findViewById(R.id.gridView);
        ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this
                , R.layout.grid_item
                , R.id.tvText, data);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deActivateLastGridView();
                mLastActivatedGridView = view;
                activateLastGridView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gridView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d(TAG, "grid focus=" + b);
                if (b) {
                    mIsGvFocused = b;
                    activateLastGridView();
                } else {
                    deActivateLastGridView();
                    mIsGvFocused = b;
                }
            }
        });
        mIsGvFocused = gridView.isFocused();

        expandableListView = (ExpandableListView) findViewById(R.id.categoriesList);
        List<List<String>> megaList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> miniList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                miniList.add("child " + j);
            }
            megaList.add(miniList);
        }
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, megaList);
        expandableListView.setAdapter(categoryAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Log.d("11111", "group " + l);

                return false;
            }
        });
        expandableListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                deActivateLastView();
                mLastActivatedListView = view;
                activateLastView();
                Log.d(TAG, "on itemSelect i=" + i + " l=" + l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Log.d("11111", "child " + i1);

                return false;
            }
        });
        expandableListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // not focused
                    deActivateLastView();
                    mIsLvFocused = false;
                    mLastLvPosition = expandableListView.getSelectedItemPosition();
                } else {
                    // focused
                    Log.d(TAG, "focused true selected=" + expandableListView.getSelectedItemPosition());
                    mIsLvFocused = true;
                    activateLastView();
                }
            }
        });
        mIsLvFocused = expandableListView.isFocused();

        return true;
    }

    private void activateLastView() {
        if (mLastActivatedListView != null && mIsLvFocused) {
            mLastActivatedListView.setActivated(true);
        }
    }

    private void deActivateLastView() {
        if (mLastActivatedListView != null && mIsLvFocused) {
            mLastActivatedListView.setActivated(false);
        }
    }

    private void activateLastGridView() {
        if (mLastActivatedGridView != null && mIsGvFocused) {
            mLastActivatedGridView.setActivated(true);
        }
    }

    private void deActivateLastGridView() {
        if (mLastActivatedGridView != null && mIsGvFocused) {
            mLastActivatedGridView.setActivated(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
