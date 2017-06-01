package com.wellographics.tapknight;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogManager {
    private Animation dmAAppearance, dmAAppearance2, dmAGlowing;
    private TextView dmDialogText, dmCharacterName, dmContinueText;
    private ImageView dmCharacterView;
    private RelativeLayout dmBackground, dmDialogLayout;
    private Activity dmContext;
    private int dmLevelId, dmCurrentCue, dmMaxCueForLevel;
    private String dmFEnemyName = "fonts/ft_enemy_name.ttf", dmFFontRegular = "fonts/ft_main_regular.ttf",  dmFFontBold = "fonts/ft_main_bold.ttf";
    private Typeface dmTFEnemyName, dmTFMainRegular, dmTFMainBold;
    private Hero dmHero;
    private Creature dmEnemy;
    private boolean dmPlayerTalking, dmDialogIsEnded;

    DialogManager(Activity context, int levelId, Hero hero) {
        dmContext = context;
        dmLevelId = levelId;
        dmCurrentCue = 0;
        dmHero = hero;
        dmPlayerTalking = false;
        dmDialogIsEnded = false;
        dmEnemy = new Creature(dmLevelId, dmContext);

        dmDialogText = (TextView) dmContext.findViewById(R.id.dialog_text);
        dmCharacterName = (TextView) dmContext.findViewById(R.id.dialog_character_name);
        dmContinueText = (TextView) dmContext.findViewById(R.id.dialog_continue_text);
        dmCharacterView = (ImageView) dmContext.findViewById(R.id.dialog_character_view);
        dmBackground = (RelativeLayout) dmContext.findViewById(R.id.dialog_background);
        dmDialogLayout = (RelativeLayout) dmContext.findViewById(R.id.dialog_layout);

        dmAAppearance = AnimationUtils.loadAnimation(dmContext, R.anim.an_activity_blackout);
        dmAAppearance2 = AnimationUtils.loadAnimation(dmContext, R.anim.an_activity_appearance);
        dmAGlowing = AnimationUtils.loadAnimation(dmContext, R.anim.an_activity_glow);

        dmTFEnemyName = Typeface.createFromAsset(dmContext.getAssets(), dmFEnemyName);
        dmTFMainRegular = Typeface.createFromAsset(dmContext.getAssets(), dmFFontRegular);
        dmTFMainBold = Typeface.createFromAsset(dmContext.getAssets(), dmFFontBold);

        dmDialogText.setTypeface(dmTFMainRegular);
        dmCharacterName.setTypeface(dmTFEnemyName);
        dmContinueText.setTypeface(dmTFMainBold);

        dmContinueText.startAnimation(dmAGlowing);

        switch (dmLevelId) {
            case 1:case 2:case 4:case 8:case 12:case 13:
                dmMaxCueForLevel = 3; break;
            case 3:case 7:case 9:
                dmMaxCueForLevel = 4; break;
            case 5:case 10:
                dmPlayerTalking = true;
                dmMaxCueForLevel = 4; break;

            default:
                dmMaxCueForLevel = 2;
        }

        startDialog();
    }

    public void startDialog() {
        dmBackground.startAnimation(dmAAppearance);
        dmDialogLayout.startAnimation(dmAAppearance2);
        dmCharacterName.startAnimation(dmAAppearance2);
        setDialogInfo();
    }

    private int getEnemyImageResource() {
        return dmContext.getResources().getIdentifier(
                "dr_activity_dialog_enemy_" + String.valueOf(dmLevelId + 1), "drawable", dmContext.getPackageName()
        );
    }

    private int getHeroImageResource() {
        return dmContext.getResources().getIdentifier(
                "dr_activity_dialog_hero_" + String.valueOf(dmHero.getClassId()), "drawable", dmContext.getPackageName()
        );
    }

    private boolean setDialogInfo() {
        Log.e("WOTTAK", String.valueOf(dmCurrentCue) + " " + String.valueOf(dmMaxCueForLevel) + " " + String.valueOf(dmDialogIsEnded));
        if (dmCurrentCue >= dmMaxCueForLevel && !dmDialogIsEnded) {
            finishDialog();
            return false;
        }
        dmDialogText.setText(getCurrentCueText());
        if (dmPlayerTalking) {
            dmCharacterName.setText(dmEnemy.getName());
            dmCharacterView.setImageResource(getEnemyImageResource());
        }
        else {
            dmCharacterName.setText(dmHero.getClassName());
            dmCharacterView.setImageResource(getHeroImageResource());
        }
        dmPlayerTalking = !dmPlayerTalking;
        return true;
    }

    private String getCurrentCueText() {
        return " - " + dmContext.getString(
                dmContext.getResources().getIdentifier(
                        "st_dialog_" + String.valueOf(dmLevelId + 1) + "_" + String.valueOf(dmCurrentCue + 1), "string", dmContext.getPackageName()
                )
        );
    }

    private void finishDialog() {
        dmDialogIsEnded = true;
        dmBackground.setVisibility(View.GONE);
    }

    private boolean nextCue() {
        dmCurrentCue++;
        return setDialogInfo();
    }

    public boolean onDialogClick() {
        return nextCue();
    }
}
