package com.wellographics.tapknight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Html;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class BattleActivity extends Activity {

    Animation bAnimEnemyWiggle, bAnimEnemyShadow, bAnimHeroGlow, bAnimVanish, bAnimEndBlackout, bAnimEndAppearance, bAnimBloodSplash, bAnimEnemyBreath;
    ImageButton bEnemyView, bPlayerView;
    ImageView bEnemyShadow, bIVProgressBarEnemyHealth, bIVBattleArea, bIVProgressBarEnemyEnergy, bIVProgressBarPlayerHealth, bIVProgressBarPlayerEnergy, bIVPlayerLowHealthIndicator,
            bIVEndProgressExp, bIVEndResult;
    int bLevelId, bCounterStart = 3;
    Creature bEnemy;
    Hero bHero;
    Button bBEndButton;
    Vibrator bVibrator;
    TextView bEnemyName, bTVEndLevel, bTVEndMoney, bTVEndExp, bTVEndResultDescr, bTVLoadingNumber, bTVEnemyDamageIndicator;
    String bFEnemyName = "fonts/ft_enemy_name.ttf", bFFontRegular = "fonts/ft_main_regular.ttf",  bFFontBold = "fonts/ft_main_bold.ttf", CLASS_ID = "class_id";
    RelativeLayout bMainLayout, bRLEndBackground, bRLEndLayout, bRLLoading;
    LinearLayout bLLBottomBar;
    Random bRandomOperator;
    Typeface bTFEnemyName, bTFMainRegular, bTFMainBold;
    SoundManager bSoundManager;
    boolean bBattleEnded = false, bPlayerLowHealth = false, bBattleStarted = false;
    Runnable bRPlayerRegeneration, bREnemyRegeneration, bREnemyAttack, bRBattleStart;
    Handler bHandlerTimers;
    Spell bSpellManager;
    AbsoluteLayout bALBloodScreen;
    DialogManager bDManager;
    Location bLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);


        WelloFontManager bWFontManager = new WelloFontManager(this);
        bEnemyName = bWFontManager.getTextView(R.id.layout_battle_name, 1, true);
        bTVEndLevel = bWFontManager.getTextView(R.id.layout_battle_end_level, 1, true);
        //bTVEndMoney = bWFontManager.getTextView(R.id.layout_battle_end_rewards_money, 1, true);
        bTVEndMoney = (TextView) findViewById(R.id.layout_battle_end_rewards_money);
        bTVEndExp = (TextView) findViewById(R.id.layout_battle_end_rewards_exp);
        bTVEndResultDescr = (TextView) findViewById(R.id.layout_battle_end_result_descr);
        bTVLoadingNumber = (TextView) findViewById(R.id.layout_battle_loading_number);
        bTVEnemyDamageIndicator = (TextView) findViewById(R.id.layout_battle_enemy_damage_ind);

        bEnemyView = (ImageButton) findViewById(R.id.layout_battle_enemy);
        bEnemyShadow = (ImageView) findViewById(R.id.layout_battle_shadow);
        bMainLayout = (RelativeLayout) findViewById(R.id.BattleActivityMainLayout);
        bLLBottomBar = (LinearLayout) findViewById(R.id.layout_battle_bottombar);
        bPlayerView = (ImageButton) findViewById(R.id.layout_battle_player);
        bIVProgressBarEnemyHealth = (ImageView) findViewById(R.id.layout_battle_enemy_health);
        bIVProgressBarEnemyEnergy = (ImageView) findViewById(R.id.layout_battle_enemy_energy);
        bIVProgressBarPlayerHealth = (ImageView) findViewById(R.id.layout_battle_player_health);
        bIVProgressBarPlayerEnergy = (ImageView) findViewById(R.id.layout_battle_player_energy);
        bIVBattleArea = (ImageView) findViewById(R.id.layout_battle_area);
        bIVPlayerLowHealthIndicator = (ImageView) findViewById(R.id.layout_battle_player_lowhealth_indicator);
        bBEndButton = (Button) findViewById(R.id.layout_battle_end_button_exit);
        bIVEndProgressExp = (ImageView) findViewById(R.id.layout_battle_end_experience);
        bIVEndResult = (ImageView) findViewById(R.id.layout_battle_end_result);

        bRLEndBackground = (RelativeLayout) findViewById(R.id.layout_battle_end_background);
        bRLEndLayout = (RelativeLayout) findViewById(R.id.layout_battle_end);
        bRLLoading = (RelativeLayout) findViewById(R.id.layout_battle_loading);
        bALBloodScreen = (AbsoluteLayout) findViewById(R.id.layout_battle_bloodscreen);

        bAnimEnemyWiggle = AnimationUtils.loadAnimation(this, R.anim.an_battle_enemy_wiggle);
        bAnimEnemyShadow = AnimationUtils.loadAnimation(this, R.anim.an_battle_enemy_shadow);
        bAnimHeroGlow = AnimationUtils.loadAnimation(this, R.anim.an_activity_glow);
        bAnimVanish = AnimationUtils.loadAnimation(this, R.anim.an_activity_vanish);
        bAnimEndBlackout = AnimationUtils.loadAnimation(this, R.anim.an_activity_blackout);
        bAnimEndAppearance = AnimationUtils.loadAnimation(this, R.anim.an_activity_appearance);
        bAnimBloodSplash = AnimationUtils.loadAnimation(this, R.anim.an_activity_blood_splash);
        bAnimEnemyBreath = AnimationUtils.loadAnimation(this, R.anim.an_battle_enemy_breath);

        bVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        bRandomOperator = new Random();

        bSoundManager = new SoundManager(15, getAssets(), getLocalClassName());

        bTFEnemyName = Typeface.createFromAsset(getAssets(), bFEnemyName);
        bTFMainRegular = Typeface.createFromAsset(getAssets(), bFFontRegular);
        bTFMainBold = Typeface.createFromAsset(getAssets(), bFFontBold);

        bTVLoadingNumber.setTypeface(bTFMainBold);
        bEnemyName.setTypeface(bTFEnemyName);

        prepareBattle();

        bLocation = new Location(this, bEnemy.getLocation());
        checkForNewLocation();
        bMainLayout.setBackgroundResource(bLocation.getBackground());
        bIVBattleArea.setImageResource(bLocation.getArea());
        bLLBottomBar.setBackgroundResource(bLocation.getBottomBar());

        bPlayerView.setImageResource(bHero.getClassIcon(2));
        setSpellIcons();

        bDManager = new DialogManager(this, bLevelId, bHero);
        bDManager.startDialog();

        bHandlerTimers = new Handler();
        bRPlayerRegeneration = new Runnable() {
            @Override
            public void run() {
                if (!bBattleEnded) {
                    if (bHero.getHealth(false) + bHero.getStamina() / 2 < bHero.getHealth(true)) bHero.setHealth(bHero.getStamina() / 2, true);
                    else bHero.setHealth(bHero.getHealth(true), false);
                    if (bHero.getEnergy(false) + bHero.getSpirit() / 2 < bHero.getEnergy(true)) bHero.setEnergy(bHero.getSpirit() / 2, true);
                    else bHero.setEnergy(bHero.getEnergy(true), false);
                    refreshProgressBars();
                    bHandlerTimers.postDelayed(this, 3000); //повторяем то же самое через 3000 милисекунд
                }else bHandlerTimers.removeCallbacks(this);
            }
        };
        bRBattleStart = new Runnable() {
            @Override
            public void run() {
                if (bCounterStart > 0) {
                    bTVLoadingNumber.setText(String.valueOf(bCounterStart));
                    bTVLoadingNumber.startAnimation(bAnimEndAppearance);
                    bCounterStart--;
                    bHandlerTimers.postDelayed(this, 900);
                }else {
                    bRLLoading.setVisibility(View.INVISIBLE);
                    bHandlerTimers.removeCallbacks(this);
                }
            }
        };

        bREnemyRegeneration	= new Runnable() {
            @Override
            public void run() {
                if (!bBattleEnded) {
                    if (bEnemy.getHealth(false) + bEnemy.getStamina() / 2 < bEnemy.getHealth(true)) bEnemy.setHealth(bEnemy.getStamina() / 2, true);
                    else bEnemy.setHealth(bEnemy.getHealth(true), false);
                    if (bEnemy.getEnergy(false) + bEnemy.getSpirit() / 2 < bEnemy.getEnergy(true)) bEnemy.setEnergy(bEnemy.getSpirit() / 2, true);
                    else bEnemy.setEnergy(bEnemy.getEnergy(true), false);
                    refreshProgressBars();
                    bHandlerTimers.postDelayed(bREnemyRegeneration, 3000);
                }
                else bHandlerTimers.removeCallbacks(this);
            }
        };

        bREnemyAttack	= new Runnable() {
            @Override
            public void run() {
                if (!bBattleEnded) {
                    if (bRandomOperator.nextInt(99) + 1 < 20)  bSpellManager.useSpellByCreature();
                    else handleStrike(false, false, bEnemy.getAttack(false));
                    refreshProgressBars();
                    bHandlerTimers.postDelayed(bREnemyAttack, 3000);
                }
                else bHandlerTimers.removeCallbacks(this);
            }
        };
    }

    public void onStop() {
        super.onStop();
        turnMainTimers(0, false);
        bSoundManager.stopStream();
        this.finish();
    }

    public void onPause() {
        super.onPause();
        turnMainTimers(0, false);
        bSoundManager.stopStream();
        this.finish();
    }

    public void onEnemyClick(View v){
        handleStrike(true, false, bHero.getAttack(false));
    }

    public void onDialogClick(View v) {
        if (!bDManager.onDialogClick()) {
            bRLLoading.setVisibility(View.VISIBLE);
            bHandlerTimers.post(bRBattleStart);
            turnMainTimers(1, true);
            turnMainTimers(2, true);
            bHandlerTimers.postDelayed(bREnemyAttack, 3500);
        };
    }

    public void onSpellButtonClick(View v) {
        String sTag = (String) v.getTag();
        bSoundManager.playSound(bSoundManager.sButtonUse);
        bSpellManager.useSpellByHero(Integer.parseInt((String.valueOf(sTag.toCharArray()[sTag.toCharArray().length - 1]))));
    }

    private void setSpellIcons() {
        ImageButton imageButton;
        for (int i = 1; i < 6; i++) {
            String sTag = "layout_battle_spell_button_" + String.valueOf(i);
            imageButton = (ImageButton) bMainLayout.findViewWithTag((Object) sTag);
            imageButton.setImageResource(bSpellManager.getDrawableIcon(i));
        }
    }

    private void prepareBattle() {
        bBattleEnded = false;
        bBattleStarted = false;
        bHero = new Hero(this);
        bLevelId = bHero.getLastLevel();
        if (bLevelId > 13) bLevelId = 13; //
        bEnemy = new Creature(bLevelId, this);
        refreshProgressBars();
        bEnemyName.setText(bEnemy.getName());
        bEnemyView.setImageResource(bEnemy.getDrawableView());
        if (bEnemy.isFlyable()) { //проверяем, летающий ли моб, чтобы включить ему анимацию полёта
            bEnemyView.startAnimation(bAnimEnemyWiggle);
            bEnemyShadow.startAnimation(bAnimEnemyShadow);
        }else bEnemyView.startAnimation(bAnimEnemyBreath);
        bSpellManager = new Spell(this, bEnemy, bHero);
        bIVProgressBarPlayerEnergy.setVisibility(View.VISIBLE);
        bIVProgressBarPlayerHealth.setVisibility(View.VISIBLE);
    }

    public void refreshProgressBars(){
        checkDeath();
        int percentPlayerHealth = (int) (bHero.getHealth(false) * 100 / bHero.getHealth(true));
        int percentPlayerEnergy = (int) (bHero.getEnergy(false) * 100 / bHero.getEnergy(true));

        if (percentPlayerHealth == 100) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_10);
        if (percentPlayerHealth < 100 && percentPlayerHealth >= 89) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_9);
        if (percentPlayerHealth < 89 && percentPlayerHealth >= 78) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_8);
        if (percentPlayerHealth < 78 && percentPlayerHealth >= 67) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_7);
        if (percentPlayerHealth < 67 && percentPlayerHealth >= 56) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_6);
        if (percentPlayerHealth < 56 && percentPlayerHealth >= 45) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_5);
        if (percentPlayerHealth < 45 && percentPlayerHealth >= 34) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_4);
        if (percentPlayerHealth < 34 && percentPlayerHealth >= 23) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_3);
        if (percentPlayerHealth < 23 && percentPlayerHealth >= 12) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_2);
        if (percentPlayerHealth < 12 && percentPlayerHealth > 0) bIVProgressBarPlayerHealth.setImageResource(R.drawable.dr_battle_vbar_health_1);
        if (percentPlayerHealth == 0) bIVProgressBarPlayerHealth.setVisibility(View.INVISIBLE);

        bIVProgressBarPlayerEnergy.setVisibility(View.VISIBLE);
        if (percentPlayerEnergy == 100) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_10);
        if (percentPlayerEnergy < 100 && percentPlayerEnergy >= 89) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_9);
        if (percentPlayerEnergy < 89 && percentPlayerEnergy >= 78) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_8);
        if (percentPlayerEnergy < 78 && percentPlayerEnergy >= 67) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_7);
        if (percentPlayerEnergy < 67 && percentPlayerEnergy >= 56) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_6);
        if (percentPlayerEnergy < 56 && percentPlayerEnergy >= 45) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_5);
        if (percentPlayerEnergy < 45 && percentPlayerEnergy >= 34) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_4);
        if (percentPlayerEnergy < 34 && percentPlayerEnergy >= 23) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_3);
        if (percentPlayerEnergy < 23 && percentPlayerEnergy >= 12) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_2);
        if (percentPlayerEnergy < 12 && percentPlayerEnergy > 0) bIVProgressBarPlayerEnergy.setImageResource(R.drawable.dr_battle_vbar_energy_1);
        if (percentPlayerEnergy == 0) bIVProgressBarPlayerEnergy.setVisibility(View.INVISIBLE);

        if (percentPlayerHealth < 40) {
            if (!bPlayerLowHealth) {
                bIVPlayerLowHealthIndicator.setVisibility(View.VISIBLE);
                bIVPlayerLowHealthIndicator.startAnimation(bAnimHeroGlow);
                bPlayerLowHealth = true;
            }
        }
        else {
            bIVPlayerLowHealthIndicator.clearAnimation();
            bIVPlayerLowHealthIndicator.setVisibility(View.INVISIBLE);
            bPlayerLowHealth = false;
        }

        bIVProgressBarEnemyHealth.setImageResource(R.drawable.dr_battle_hbar_health);
        bIVProgressBarEnemyEnergy.setImageResource(R.drawable.dr_battle_hbar_energy);

        bIVProgressBarEnemyEnergy.setLayoutParams(new RelativeLayout.LayoutParams((int) (getResources().getDimension(R.dimen.activity_battle_enemy_ind_energy_width) * bEnemy.getEnergy(false) / bEnemy.getEnergy(true)), bIVProgressBarEnemyEnergy.getLayoutParams().height));
        bIVProgressBarEnemyHealth.setLayoutParams(new RelativeLayout.LayoutParams((int) (getResources().getDimension(R.dimen.activity_battle_enemy_ind_health_width) * bEnemy.getHealth(false) / bEnemy.getHealth(true)), bIVProgressBarEnemyHealth.getLayoutParams().height));
    }

    public void turnSpellButtons(boolean value) {
        if (!bBattleEnded) {
            ImageButton imageButton;
            float alpha = value ? 1 : 0.5f;
            for (int i = 1; i < 6; i++) {
                String sTag = "layout_battle_spell_button_" + String.valueOf(i);
                imageButton = (ImageButton) bMainLayout.findViewWithTag((Object) sTag);
                imageButton.setAlpha(alpha);
                imageButton.setEnabled(value);
            }
        }
    }

    public void turnMainTimers(int timerId, boolean value) {

        if (timerId == 1 || timerId == 0) bHandlerTimers.removeCallbacks(bREnemyRegeneration);
        if (timerId == 2 || timerId == 0) bHandlerTimers.removeCallbacks(bRPlayerRegeneration);
        if (timerId == 3 || timerId == 0) bHandlerTimers.removeCallbacks(bREnemyAttack);
        if (value) {
            if (timerId == 1 || timerId == 0) bHandlerTimers.post(bREnemyRegeneration);
            if (timerId == 2 || timerId == 0) bHandlerTimers.post(bRPlayerRegeneration);
            if (timerId == 3 || timerId == 0) bHandlerTimers.postDelayed(bREnemyAttack, 850);
        }
    }

    public void viewEndBattleWindow(boolean win) {
        String         description = "";
        int            moneyReward, expReward, oldExp, newExp;

        bRLEndBackground.setVisibility(View.VISIBLE);
        bRLEndBackground.startAnimation(bAnimEndBlackout);
        bRLEndLayout.startAnimation(bAnimEndAppearance);

        bTVEndLevel.setTypeface(bTFMainBold);
        bTVEndMoney.setTypeface(bTFMainBold);
        bTVEndExp.setTypeface(bTFMainBold);
        bTVEndResultDescr.setTypeface(bTFMainRegular);
        bBEndButton.setTypeface(bTFMainBold);

        if (win) {
            bHero.setLastLevel(1, true);
            bSoundManager.playSound(bSoundManager.sBattleWin);
            bIVEndResult.setImageResource(R.drawable.dr_battle_end_win);
            moneyReward = (bHero.getLastLevel() + 5) * 5;
            expReward = (int) (3 * Math.pow(2, bLevelId + 1));
            bBEndButton.setText(getString(R.string.st_de_continue));
            description = getString(R.string.st_battle_defeat) + "<bold><color='#FFFFFF'> " + bEnemy.getName() + "</color></bold>!";
        }
        else {
            bSoundManager.playSound(bSoundManager.sBattleLose);
            bIVEndResult.setImageResource(R.drawable.dr_battle_end_lose);
            moneyReward = bLevelId;
            expReward = (int) (Math.pow(2, bLevelId + 1)); //aформула
            bBEndButton.setText(getString(R.string.st_battle_restart));
            description = getString(R.string.st_battle_defeated) + "<color='#FFFFFF' > " + bEnemy.getName() + "</color>...";
        }

        oldExp = (int)(getResources().getDimension(R.dimen.activity_battle_enemy_ind_health_width) * bHero.getExperience(false) / bHero.getExperience(true));

        bHero.setExperience(expReward, true);
        bHero.setMoney(moneyReward, true);
        bTVEndLevel.setText(String.valueOf(bHero.getLevel()));
        bTVEndExp.setText("+" + String.valueOf(expReward));
        bTVEndMoney.setText("+" + String.valueOf(moneyReward));
        bTVEndResultDescr.setText(Html.fromHtml(description), TextView.BufferType.SPANNABLE);
        bHero.saveInfo();

        newExp = (int)(getResources().getDimension(R.dimen.activity_battle_enemy_ind_health_width) * bHero.getExperience(false) / bHero.getExperience(true));

        bIVEndProgressExp.setLayoutParams(new RelativeLayout.LayoutParams(oldExp, bIVEndProgressExp.getLayoutParams().height));

        Animation growing = new ScaleAnimation(oldExp, newExp, getResources().getDimension(R.dimen.activity_battle_enemy_ind_height), getResources().getDimension(R.dimen.activity_battle_enemy_ind_height));
        growing.setDuration(2000);
        bIVEndProgressExp.startAnimation(growing);

    }

    public void onEndButtonClick(View v) {
        bSoundManager.playSound(bSoundManager.sButtonUse);
        if (bEnemy.getHealth(false) == 0) finish();
        else {
            turnMainTimers(0, true);
            prepareBattle();
            bRLEndBackground.setVisibility(View.GONE);
        }
    }

    private void checkForNewLocation() {
        if (bHero.isNewLocation(bLocation.getIdentifier())) {
            int experience = (int)Math.pow(bHero.getLevel() + 1, 3);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.an_activity_fadein);
            bHero.addNewLocation(bLocation.getIdentifier());
            TextView textView = (TextView) findViewById(R.id.layout_battle_newlocation);
            String string =
                            getString(R.string.st_battle_openedLocation1) +
                            bLocation.getName() +
                            getString(R.string.st_battle_openedLocation2) +
                            String.valueOf(experience) +
                            getString(R.string.st_battle_openedLocation3);
            textView.setText(string);
            bHero.setExperience(experience, true);
            textView.startAnimation(anim);
        };
    }

    public void onPlayerClick(View v) {}

    /** Boolean-метод, проверяющий жив ли игрок/противник:
     * 	@param choice — выбор цели (true — проверяем игрока, false — противника);
     *  @return bBattleEnded (окончен ли бой/кто-то умер)  */
    public boolean checkDeath(boolean choice) {
        if (choice) {
            if (bHero.getHealth(false) <= 0 && !bBattleEnded) {
                bBattleEnded = true;
                bHero.setHealth(0, false);
                bSoundManager.playSound(bSoundManager.sPlayerDeath);
                viewEndBattleWindow(false);
            }
        }
        else {
            if (bEnemy.getHealth(false) <= 0 && !bBattleEnded) {
                bBattleEnded = true;
                bEnemy.setHealth(0, false);
                switch (bRandomOperator.nextInt(2)) {
                    case 0: bSoundManager.playSound(bSoundManager.sEnemyDeath1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sEnemyDeath2); break;
                    case 2: bSoundManager.playSound(bSoundManager.sEnemyDeath3);
                }
                viewEndBattleWindow(true);
            }
        }
        if (bBattleEnded == true) {
            bEnemyView.startAnimation(bAnimVanish);
            bEnemyShadow.startAnimation(bAnimVanish);
            bEnemyView.setVisibility(View.INVISIBLE);
            bEnemyShadow.setVisibility(View.INVISIBLE);
            turnMainTimers(0, false);
            turnSpellButtons(false);}
        return bBattleEnded;
    }

    public boolean checkDeath() {return checkDeath(true) || checkDeath(false);}

    /** Обработчик ударов [ЧЕРЕЗ НЕГО НАНОСИТСЯ ВЕСЬ УРОН В АКТИВНОСТИ!]
     * @param target — выбор цели (true — атакуем противника, false — игрока);
     * @param spell — является ли эта атака заклинанием;
     * @param attack — количество урона, который нужно будет нанести.
     * Метод содержит вероятность критического удара, блока, а также промаха. */
    public double handleStrike(boolean target, boolean spell, double attack) {
        String action = "";
        attack = (attack * ((100 - bHero.VARIATION_FACTOR / 2) + (int)(Math.random() * bHero.VARIATION_FACTOR))) / 100;  //считаем урон
        if (target) {
            if (bRandomOperator.nextInt(99) + 1 < bHero.getCritical()) {
                bEnemyView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY); //вибрация
                switch (bRandomOperator.nextInt(2)) { //проигрываем рандомный звук
                    case 0: bSoundManager.playSound(bSoundManager.sPlayerCrit1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sPlayerCrit2); break;
                    case 2: bSoundManager.playSound(bSoundManager.sPlayerCrit3);
                }
                attack *= 2 + (bRandomOperator.nextInt(2) + 1); //увеличиваем атаку в несколько раз
                action = "crit";
            }else if ((bRandomOperator.nextInt(99) + 1 < bHero.MISS_CHANCE) && !spell) { //заклинанием промахнуться нельзя
                switch (bRandomOperator.nextInt(1)) { //проигрываем рандомный звук
                    case 0: bSoundManager.playSound(bSoundManager.sMiss1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sMiss2);
                }
                action = "miss"; //промахнулись, дальше урон нанесён не будет
                createDamageInd(0, action, spell);
            }else if ((bRandomOperator.nextInt(99) + 1 < bHero.getBlock()) && !spell) { //блокировать заклинание тоже нельзя
                switch (bRandomOperator.nextInt(2)) { //проигрываем рандомный звук
                    case 0: bSoundManager.playSound(bSoundManager.sPlayerBlock1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sPlayerBlock2); break;
                    case 2: bSoundManager.playSound(bSoundManager.sPlayerBlock3);
                }
                attack *= Math.random(); //атака при блоке колеблется от 0% до 99%
                action = "block";
            }else if (!spell) {
                switch (bRandomOperator.nextInt(2)) { //проигрываем рандомный звук
                    case 0: bSoundManager.playSound(bSoundManager.sPlayerAttack1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sPlayerAttack2); break;
                    case 2: bSoundManager.playSound(bSoundManager.sPlayerAttack3);
                }
                action = "hit";
            }
            if (!action.equals("miss")) { //проверяем промах. если всё хорошо, то бьём.
                if (attack > 0 && attack < 1) attack = 1;
                if  (bEnemy.getHealth(false) < attack) bEnemy.setHealth(0, false); //если ХпПротивника < КолвоАтаки, то, чтобы число не получилось отрицательным, приравниваем к нулю
                else bEnemy.setHealth(-attack, true); //а если всё нормально, то просто отнимаем нужное число от его актуального уровня здоровья
                createDamageInd((int) attack, action, spell);
                refreshProgressBars(); //обновляем прогрессбары
            }
        }

        else {
            if (bRandomOperator.nextInt(99) + 1 < bEnemy.getCritical()) {
                bEnemyView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY); //вибрация
                switch (bRandomOperator.nextInt(2)) {
                    case 0: bSoundManager.playSound(bSoundManager.sEnemyCrit1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sEnemyCrit2); break;
                    case 2: bSoundManager.playSound(bSoundManager.sEnemyCrit3);
                }
                attack *= 2 + (bRandomOperator.nextInt(2) + 1); //увеличиваем атаку в несколько раз
                action = "crit";

            }else if (bRandomOperator.nextInt(99) + 1 < bEnemy.MISS_CHANCE) {
                switch (bRandomOperator.nextInt(1)) {
                    case 0: bSoundManager.playSound(bSoundManager.sMiss1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sMiss2);
                }
                action = "miss";
            }else if ((bRandomOperator.nextInt(99) + 1 < bEnemy.getBlock()) && !spell) {
                bSoundManager.playSound(bSoundManager.sEnemyBlock);
                action = "block";
                attack *= Math.random(); //урон при блоке колеблется от 0% до 99%
            }else {
                switch (bRandomOperator.nextInt(2)) {
                    case 0: bSoundManager.playSound(bSoundManager.sEnemyAttack1); break;
                    case 1: bSoundManager.playSound(bSoundManager.sEnemyAttack2); break;
                    case 2: bSoundManager.playSound(bSoundManager.sEnemyAttack3);
                }
                action = "hit";
            }
            if (!action.equals("miss")) {
                if (bHero.getHealth(false) < attack) bHero.setHealth(0, false);
                else bHero.setHealth(-attack, true);
                bVibrator.vibrate(100);
                bALBloodScreen.startAnimation(bAnimBloodSplash);
                showEnemyDamage((int)attack, action);
                refreshProgressBars();
            }
        }
        return attack;
    }

    protected void showEnemyDamage(int damage, String actionId) {
        String text = "-" + String.valueOf(damage);
        Typeface tf = bTFMainRegular;
        int textSize = (int)getResources().getDimension(R.dimen.activity_battle_enemy_damage);
        bTVEnemyDamageIndicator.setTextColor(getResources().getColor(R.color.cl_white));
        switch (actionId) {
            case "miss": text = "miss"; break;
            case "crit":
                textSize = (int)getResources().getDimension(R.dimen.activity_battle_enemy_damage_critical);
                tf = bTFMainBold;
                bTVEnemyDamageIndicator.setTextColor(getResources().getColor(R.color.cl_orange_name_available));
                break;
            case "stun": text = "stun";
        }
        bTVEnemyDamageIndicator.setAllCaps(true);
        bTVEnemyDamageIndicator.setTextSize(textSize);
        bTVEnemyDamageIndicator.setTypeface(tf);
        bTVEnemyDamageIndicator.setText(text);
        bTVEnemyDamageIndicator.startAnimation(bAnimBloodSplash);
    }

    protected void createDamageInd(int damage, String actionId, boolean spell){
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.an_battle_damagetext);
        final TextView damageInd = new TextView(this);
        Display display = getWindowManager().getDefaultDisplay();
        bRandomOperator = new Random();
        int width = display.getWidth() / 2 - bRandomOperator.nextInt(350) / 2 + bRandomOperator.nextInt(350) - 100;
        int height = display.getHeight() / 2 - bRandomOperator.nextInt(200) / 2 + bRandomOperator.nextInt(200) - 150;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(width, height, 0, 0);

        String text = String.valueOf(damage);
        Typeface tf = bTFMainRegular;
        int textSize = (int)getResources().getDimension(R.dimen.activity_battle_damage), cl = spell ? getResources().getColor(R.color.cl_blue_magic) : getResources().getColor(R.color.cl_white_tranparent);

        damageInd.setLayoutParams(lp);
        switch (actionId) {
            case "miss": text = "Miss"; break;
            case "crit":
                textSize = (int)getResources().getDimension(R.dimen.activity_battle_damage_critical);
                cl = spell ? getResources().getColor(R.color.cl_blue_magic_crit) : getResources().getColor(R.color.cl_orange_name_available);
                tf = bTFMainBold;
                break;
            case "block":
                text = "Block (Dealed damage: " + text + ")"; break;
            case "stun":
                text = "Stun";
                tf = bTFMainBold;
        }
        damageInd.setText(text);
        damageInd.setAllCaps(true);
        damageInd.setTextSize(textSize);
        damageInd.setTypeface(tf);
        damageInd.setTextColor(cl);

        bMainLayout.addView(damageInd);
        textAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {damageInd.setVisibility(View.GONE);}
            @Override public void onAnimationRepeat(Animation animation) {}
        });
        damageInd.startAnimation(textAnimation);
    }
}