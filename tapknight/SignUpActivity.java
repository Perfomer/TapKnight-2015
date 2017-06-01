package com.wellographics.tapknight;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {

    String sFontPath = "fonts/ft_main_bold.ttf", CLASS_ID = "class_id";
    TextView sTVHeader, sTVWizard, sTVArcher, sTVPaladin;
    ImageView sIVWizardG, sIVArcherG, sIVPaladinG;
    Button sBContinue;
    Animation sAGlow;
    SoundManager sSoundManager;
    int sActiveClassButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        sSoundManager = new SoundManager(2, getAssets(), getLocalClassName());

        sIVArcherG = (ImageView) findViewById(R.id.layout_signup_icon_archer_glow);
        sIVWizardG = (ImageView) findViewById(R.id.layout_signup_icon_wizard_glow);
        sIVPaladinG = (ImageView) findViewById(R.id.layout_signup_icon_paladin_glow);

        sTVHeader = (TextView) findViewById(R.id.layout_signup_header_text);
        sTVWizard = (TextView) findViewById(R.id.layout_signup_wizard_text);
        sTVArcher = (TextView) findViewById(R.id.layout_signup_archer_text);
        sTVPaladin = (TextView) findViewById(R.id.layout_signup_paladin_text);

        sBContinue = (Button) findViewById(R.id.layout_signup_button_continue);

        sAGlow = AnimationUtils.loadAnimation(this, R.anim.an_activity_glow);

        Typeface sCF = Typeface.createFromAsset(getAssets(), sFontPath);
        sTVHeader.setTypeface(sCF);
        sTVWizard.setTypeface(sCF); sTVWizard.setAllCaps(true);
        sTVArcher.setTypeface(sCF); sTVArcher.setAllCaps(true);
        sTVPaladin.setTypeface(sCF); sTVPaladin.setAllCaps(true);
        sBContinue.setTypeface(sCF); sBContinue.setAllCaps(true);

    }

    public void onIconClick(View v){
        if (sActiveClassButton != Integer.parseInt(String.valueOf(v.getTag()))) {
            sActiveClassButton = Integer.parseInt(String.valueOf(v.getTag()));

            sBContinue.setText(R.string.st_de_continue);
            sBContinue.setBackgroundResource(R.drawable.dr_activity_button_active);


            sIVWizardG.setVisibility(View.INVISIBLE);
            sIVWizardG.clearAnimation();
            sIVArcherG.setVisibility(View.INVISIBLE);
            sIVArcherG.clearAnimation();
            sIVPaladinG.setVisibility(View.INVISIBLE);
            sIVPaladinG.clearAnimation();

            switch (sActiveClassButton) {
                case 1:
                    sIVArcherG.setVisibility(View.VISIBLE);
                    sIVArcherG.setAnimation(sAGlow);
                    sBContinue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dr_icon_class_symb_archer, 0, 0, 0);
                    break;
                case 2:
                    sIVWizardG.setVisibility(View.VISIBLE);
                    sIVWizardG.setAnimation(sAGlow);
                    sBContinue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dr_icon_class_symb_wizard, 0, 0, 0);
                    break;
                case 3:
                    sIVPaladinG.setVisibility(View.VISIBLE);
                    sIVPaladinG.setAnimation(sAGlow);
                    sBContinue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dr_icon_class_symb_paladin, 0, 0, 0);
                    break;
            }
        }
    }

    public void onContinueButtonClick(View v){
        sSoundManager.playSound(sSoundManager.sButtonUse);
        if (sActiveClassButton != 0) {
            Hero hero = new Hero(sActiveClassButton, this);
            sSoundManager.playSound(sSoundManager.sCloseActivity);
            Intent intent = new Intent();
            intent.putExtra("class_id", sActiveClassButton);
            setResult(RESULT_OK, intent);
            this.finish();
        }else Toast.makeText(this, getString(R.string.st_er_notchoice), Toast.LENGTH_SHORT).show();
    }
}
