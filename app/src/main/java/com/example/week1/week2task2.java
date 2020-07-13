package com.example.week1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.week1.provider.ItemViewModel;
import com.example.week1.provider.item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.util.StringTokenizer;

public class week2task2 extends AppCompatActivity {
    EditText itemTextView;
    EditText quantityTextView;
    EditText costTextView;
    EditText descriptionTextView;
    ToggleButton toggleView;

    public static final String TAG = "LIFE_CYCLE_TRACING";
    private String nonViewState;
    final String FILE_NAME = "Week3File";

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerlayout;
    FloatingActionButton fabBtn;
    Button addBtn;
    private ItemViewModel mItemViewModel;
    ScrollView scrollView;
    Button delBtn;

    int xMotionEventDown;
    int yMotionEventDown;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerlayout = findViewById((R.id.drawer_layout));
        fabBtn = findViewById(R.id.fab);

        itemTextView = findViewById(R.id.itemText);
        quantityTextView = findViewById(R.id.quantityText);
        costTextView = findViewById(R.id.costText);
        descriptionTextView = findViewById(R.id.descriptionText);
        toggleView = findViewById(R.id.toggleButton);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);


        //fab
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item item1 = new item(itemTextView.getText().toString(),quantityTextView.getText().toString(),costTextView.getText().toString(), descriptionTextView.getText().toString(), Boolean.toString(!toggleView.isChecked()));
                mItemViewModel.insert(item1);
                Snackbar.make(view, "Item added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //add
        addBtn = findViewById(R.id.newItemButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item item1 = new item(itemTextView.getText().toString(),quantityTextView.getText().toString(),costTextView.getText().toString(), descriptionTextView.getText().toString(), Boolean.toString(!toggleView.isChecked()));
                mItemViewModel.insert(item1);
                Snackbar.make(v, "Item added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        delBtn = findViewById(R.id.deleteButton);
        
        scrollView = findViewById(R.id.scrollLayout);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();

                int x; int y; int deltaX; int deltaY;

                if (action == MotionEvent.ACTION_DOWN){
                    xMotionEventDown = (int) event.getX();
                    yMotionEventDown = (int) event.getY();
                }

                else if(action == MotionEvent.ACTION_UP){
                    x = (int) event.getX();
                    y = (int) event.getY();

                    deltaX = x - xMotionEventDown;
                    deltaY = y = yMotionEventDown;

                    if (deltaX < 800 && deltaY > -800) {
                        if (deltaX > 0){
                            readMyEts(v);
                        }
                        else if (deltaX < 0){
                            clearName(v);
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        View v = getWindow().getCurrentFocus();
        String msg = "";

        switch (id){
            case R.id.option_id_1:
                msg = "item added";
                readMyEts(v);
                break;
            case R.id.option_id_2:
                msg = "fields cleared";
                clearView(v);
                break;
        }

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        return true;
    }

    public void clearName(View v) {
        boolean toggleBoolean = false;
        toggleView.setChecked(toggleBoolean);
        quantityTextView.setText("");
        costTextView.setText("");
        descriptionTextView.setText("");
        itemTextView.setText("");
    }

    public void readMyEts(View v) {
        String st1 = itemTextView.getText().toString();
        String msg = st1 + " has been added to the list";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    public void clearView(View v){
        SharedPreferences sP = getSharedPreferences(FILE_NAME,0);
        SharedPreferences.Editor editor = sP.edit();
        clearName(v);
        editor.putString("itemTextKey","");
        editor.putString("quantityTextKey","");
        editor.putString("costTextKey","");
        editor.putString("descriptionKey","");
        editor.putBoolean("frozenData", false);
        editor.apply();
    }


    //life style code starts here
        protected void onResume() {
            super.onResume();
            restorePersistentdata();
            Log.i(TAG, "onResume");
        }

        protected void onPause() {
            super.onPause();
            savePersistentData();
            Log.i(TAG, "onPause");
        }


        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("nonViewDataKey",nonViewState);
            Log.i(TAG, "onSaveInstanceState");

        }

        //only gets executed if inState != null so no need to check for null Bundle
        protected void onRestoreInstanceState(Bundle inState) {
            super.onRestoreInstanceState(inState);
            nonViewState = inState.getString("nonViewDataKey");
            Log.i(TAG, "onRestoreInstanceState");

        }

        public void restorePersistentdata(){
            SharedPreferences sP = getSharedPreferences(FILE_NAME,0);
            String itemTextString = sP.getString("itemTextKey"," ");
            String quantityTextString = sP.getString("quantityTextKey"," ");
            String costTextString = sP.getString("costTextKey"," ");
            String descriptionTextString = sP.getString("descriptionKey"," ");
            Boolean toggle1 = sP.getBoolean("frozenData",true);

            itemTextView.setText(itemTextString);
            quantityTextView.setText(quantityTextString);
            costTextView.setText(costTextString);
            descriptionTextView.setText(descriptionTextString);
            toggleView.setChecked(!toggle1);

            Log.i("tag",itemTextString);

        }

        public void savePersistentData(){
            SharedPreferences sP = getSharedPreferences(FILE_NAME,0);
            SharedPreferences.Editor editor = sP.edit();

            String itemTextString = itemTextView.getText().toString();
            String quantityTextString = quantityTextView.getText().toString();
            String costTextString = costTextView.getText().toString();
            String descriptionTextString = descriptionTextView.getText().toString();
            Boolean frozenBoolean = !toggleView.isChecked(); //yes = frozen

            editor.putString("itemTextKey",itemTextString);
            editor.putString("quantityTextKey",quantityTextString);
            editor.putString("costTextKey",costTextString);
            editor.putString("descriptionKey",descriptionTextString);
            editor.putBoolean("frozenData", !frozenBoolean);
            editor.apply();

        }



class MyBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
        StringTokenizer sT = new StringTokenizer(msg, ";");
        String itemText = sT.nextToken();
        String quantityText = sT.nextToken();
        String costText = sT.nextToken();
        String descriptionText = sT.nextToken();
        String toggleText = sT.nextToken();

        Boolean myBool = Boolean.parseBoolean(toggleText);

        itemTextView.setText(itemText);
        quantityTextView.setText(quantityText);
        costTextView.setText(costText);
        descriptionTextView.setText(descriptionText);
        toggleView.setChecked(myBool);
    }
}
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            View v = getWindow().getCurrentFocus();

            if (id == R.id.item_id_1) {
                readMyEts(v);
            } else if (id == R.id.item_id_2) {
                clearView(v);
            } else if (id == R.id.item_id_3){
                Intent intent = new Intent(week2task2.this,displayItems.class);
                startActivity(intent);
            }
            drawerlayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }

}

