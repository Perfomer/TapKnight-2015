package com.wellographics.tapknight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;


public class SettingsActivity extends Activity {

    ActionBarManager sABManager;
    InventoryManager sIManager;
    SeekBar sSBMusic, sSBEffects;
    ToggleButton sTBVibration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sABManager = new ActionBarManager(this);
        sIManager = new InventoryManager(this);
        sSBMusic = (SeekBar) findViewById(R.id.layout_settings_seekbar_music);
        sTBVibration = (ToggleButton) findViewById(R.id.layout_settings_togglebutton_vibration);
    }

    public void onNewCharClick(View v) {
        Intent goToSignUpActivity = new Intent(this, SignUpActivity.class);
        startActivityForResult(goToSignUpActivity, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {this.finish(); return;}
        Hero hero = new Hero(data.getIntExtra("class_id", 0), getApplicationContext());
        sABManager.refreshActionBarInfo();
    }

}
