package com.wellographics.tapknight;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.Random;

public class Spell{
    int sCounter;
    BattleActivity sBattleActivityOperator;
    Context sContext;
    Handler sHandlerTimers;
    Creature sEnemy;
    Hero sHero;
    SoundManager sSoundManager;
    Random sRandomOperator;
    Runnable sRBunchOfLightTimer, sRFireballTimer, sRFrostyWhirlwindTimer, sRBurningSoulTimer, sRHolyArmorTimer, sRSealStrengthTimer, sRTrapTimer, sRSapTimer, sRThrillOfHuntTimer;

    public Spell(Context context, Hero hero) {
        sHero = hero;
        sContext = context;
    }

    public Spell(BattleActivity activity, Creature enemy, Hero hero) {
        sRandomOperator = new Random();
        sHandlerTimers = new Handler();
        sBattleActivityOperator = activity;
        sContext = sBattleActivityOperator.getApplicationContext();
        sEnemy = enemy;
        sHero = hero;
        sSoundManager = new SoundManager(5, activity.getAssets(), "Spell" + sHero.getClassName());
        sCounter = 0;

        switch (hero.getClassId()) {
            case 1:
                sRTrapTimer = new Runnable() {
                    @Override
                    public void run() {
                        if (sBattleActivityOperator.checkDeath(false)) sHandlerTimers.removeCallbacks(this);
                        else if (sCounter < 15) {
                            sCounter++;
                            sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 0.25);
                            sHandlerTimers.postDelayed(this, 1000);
                        }
                        else {
                            sCounter = 0;
                            sEnemy.setAttack(sEnemy.getAttack(true), false);
                            sHandlerTimers.removeCallbacks(this);
                            sBattleActivityOperator.turnSpellButtons(true);
                        }
                    }
                };
                sRSapTimer = new Runnable() {
                    @Override
                    public void run() {
                        sEnemy.setStun(false);
                        sBattleActivityOperator.turnSpellButtons(true);
                        sBattleActivityOperator.turnMainTimers(3, true);
                    }
                };
            case 2:
                sRBunchOfLightTimer = new Runnable() {
                    @Override
                    public void run() {
                        if (sBattleActivityOperator.checkDeath(false)) sHandlerTimers.removeCallbacks(this);
                        else if (sCounter < 5) {
                            sSoundManager.playSound(sSoundManager.sSpellBunchOfLight2);
                            sCounter++;
                            sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 3);
                            sHandlerTimers.postDelayed(this, 1000);
                        }else {
                            sCounter = 0;
                            sHandlerTimers.removeCallbacks(this);
                            sBattleActivityOperator.turnSpellButtons(true);
                        }
                    }
                };
                sRFireballTimer = new Runnable() {
                    @Override
                    public void run() {
                        if (sBattleActivityOperator.checkDeath()) sHandlerTimers.removeCallbacks(this);
                        else if (sCounter < 10) {
                            switch (sRandomOperator.nextInt(2)) {
                                case 0: sSoundManager.playSound(sSoundManager.sSpellFireball1); break;
                                case 1: sSoundManager.playSound(sSoundManager.sSpellFireball2); break;
                                case 2: sSoundManager.playSound(sSoundManager.sSpellFireball3);
                            }
                            sCounter++;
                            sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 5);
                            sHandlerTimers.postDelayed(this, 1000);
                        }
                        else {
                            sCounter = 0;
                            sBattleActivityOperator.turnSpellButtons(true);
                            sHandlerTimers.removeCallbacks(this);
                        }
                    }
                };
                sRFrostyWhirlwindTimer = new Runnable() {
                    @Override
                    public void run() {
                        sBattleActivityOperator.turnMainTimers(3, true);
                        sBattleActivityOperator.turnSpellButtons(true);
                        sHandlerTimers.removeCallbacks(this);
                    }
                };
                sRBurningSoulTimer = new Runnable() {
                    @Override
                    public void run() {
                        if (sBattleActivityOperator.checkDeath()) sHandlerTimers.removeCallbacks(this);
                        else if (sCounter < 10) {
                            switch (sRandomOperator.nextInt(2)) {
                                case 0: sSoundManager.playSound(sSoundManager.sSpellBurningSoul1); break;
                                case 1: sSoundManager.playSound(sSoundManager.sSpellBurningSoul2); break;
                                case 2: sSoundManager.playSound(sSoundManager.sSpellBurningSoul3);
                            }
                            sCounter++;
                            sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 5.5);
                            sBattleActivityOperator.handleStrike(false, true, sHero.getAttack(true) * 2.5);
                            sHandlerTimers.postDelayed(this, 750);
                        }
                        else {
                            sCounter = 0;
                            sBattleActivityOperator.turnSpellButtons(true);
                            sHandlerTimers.removeCallbacks(this);
                        }
                    }
                };
                break;
            case 3:
                sRHolyArmorTimer = new Runnable() {
                    @Override
                    public void run() {
                        sEnemy.setAttack(sEnemy.getAttack(true), false);
                        sBattleActivityOperator.turnSpellButtons(true);
                        sHandlerTimers.removeCallbacks(this);
                    }
                };
                sRSealStrengthTimer = new Runnable() {
                    @Override
                    public void run() {
                        sHero.setAttack(sHero.getAttack(true), false);
                        sBattleActivityOperator.turnSpellButtons(true);
                        sHandlerTimers.removeCallbacks(this);
                    }
                };
        }
    }

    public int getDrawableIcon(int spellId) {
        return sContext.getResources().getIdentifier(
                "dr_spell_" + String.valueOf((sHero.getClassId() - 1) * 5 + spellId) + "_icon", "drawable",
                sContext.getPackageName()
        );
    }

    public String getSpellName(int spellId) {
        //Log.e("ASSTUPID", String.valueOf(spellId));
        return sContext.getString(sContext.getResources().getIdentifier(
                "st_spell_name_" + String.valueOf((sHero.getClassId() - 1) * 5 + spellId), "string",
                sContext.getPackageName()
        ));
    }

    public String getSpellDescription(int spellId) {
        return sContext.getString(sContext.getResources().getIdentifier(
                "st_spell_description_" + String.valueOf((sHero.getClassId() - 1) * 5 + spellId), "string",
                sContext.getPackageName()
        ));
    }


    public void useSpellByHero(int spellId) {
        int cost = 0;
        switch (spellId) {
            case 1:
                cost = (int) (sHero.getEnergy(true) / 7);
                switch (sHero.getClassId()) {
                    case 1: archerTrap(); break;
                    case 2: wizardBunchOfLight(); break;
                    case 3: paladinHeroicStrike();
                }break;
            case 2:
                cost = (int) (sHero.getEnergy(true) / 5);
                switch (sHero.getClassId()) {
                    case 1: archerFocusedShot(); break;
                    case 2: wizardFireball(); break;
                    case 3: paladinHolyArmor();
                }break;
            case 3:
                cost = (int) (sHero.getEnergy(true) / 3.5);
                switch (sHero.getClassId()) {
                    case 1: archerVictoryRush(); break;
                    case 2: wizardFrostyWhirlwind(); break;
                    case 3: paladinRestore();
                }break;
            case 4:
                cost = (int) (sHero.getEnergy(true) / 2);
                switch (sHero.getClassId()) {
                    case 1: archerSap(); break;
                    case 2: wizardArcaneFlow(); break;
                    case 3: paladinLightRetribution();
                }break;
            case 5:
                cost = (int) (sHero.getEnergy(true) / 5);
                switch (sHero.getClassId()) {
                    case 1: archerThrillOfHunt(); break;
                    case 2: wizardBurningSoul(); break;
                    case 3: paladinSealStrength();
                }break;
            default: Log.wtf("Spell", "Used invalid key: " + String.valueOf(spellId));
        }
        sHero.setEnergy(-cost, true);
    }

    public void useSpellByCreature() {
        switch (sEnemy.getCreatureId()) {
            case 0: _0spell(); break;
        }
        sEnemy.setEnergy(-sEnemy.getEnergy(true) / 3, true);
    }

    private void _0spell() {
        sSoundManager.playSound(sSoundManager.sSpellBurningSoul1);
        sBattleActivityOperator.handleStrike(false, true, sEnemy.getAttack(true) * 5);
    }

    private void wizardBunchOfLight() {
        sSoundManager.playSound(sSoundManager.sSpellBunchOfLight1);
        sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 1.5);
        sBattleActivityOperator.turnSpellButtons(false);
        sHandlerTimers.post(sRBunchOfLightTimer);
    }

    private void wizardFireball() {
        sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 10);
        sBattleActivityOperator.turnSpellButtons(false);
        sHandlerTimers.post(sRFireballTimer);
    }

    private void wizardArcaneFlow() {
        if (sEnemy.getEnergy(false) - sEnemy.getEnergy(true) / 2 < 0) sEnemy.setEnergy(0, false);
        else sEnemy.setEnergy(-sEnemy.getEnergy(true) / 2, true);
        sBattleActivityOperator.handleStrike(true, true, sEnemy.getEnergy(true) / 2);
    }

    private void wizardBurningSoul() {
        sBattleActivityOperator.turnSpellButtons(false);
        sHandlerTimers.post(sRBurningSoulTimer);
    }

    private void wizardFrostyWhirlwind() {
        sSoundManager.playSound(sSoundManager.sSpellFrostyWhirlwind);
        sBattleActivityOperator.turnMainTimers(3, false);
        sHandlerTimers.postDelayed(sRFrostyWhirlwindTimer, 15000);
        sBattleActivityOperator.createDamageInd(0, "stun", true);
        sBattleActivityOperator.turnSpellButtons(false);
    }

    private void paladinHolyArmor() {
        sSoundManager.playSound(sSoundManager.sSpellHolyArmor);
        sEnemy.setAttack(0, false);
        sBattleActivityOperator.turnSpellButtons(false);
        sHandlerTimers.postDelayed(sRHolyArmorTimer, 5000);
    }

    private void paladinHeroicStrike() {
        sSoundManager.playSound(sSoundManager.sSpellHeroicStrike);
        sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(false) * 5);
    }

    private void paladinSealStrength() {
        sSoundManager.playSound(sSoundManager.sSpellSealStrength);
        sHero.setAttack(sHero.getAttack(true) * 3, false);
        sBattleActivityOperator.turnSpellButtons(false);
        sHandlerTimers.postDelayed(sRSealStrengthTimer, 10000);
    }

    private void paladinLightRetribution() {
        sSoundManager.playSound(sSoundManager.sSpellLightRetribution);
        sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 20);
    }

    private void paladinRestore() {
        sSoundManager.playSound(sSoundManager.sSpellRestore);
        if (sHero.getHealth(false) + sHero.getHealth(true) / 2 < sHero.getHealth(true)) sHero.setHealth(sHero.getHealth(true) / 2, true);
        else sHero.setHealth(sHero.getHealth(true), false);
    }

    private void archerTrap() {
        sSoundManager.playSound(sSoundManager.sSpellTrap);
        sEnemy.setAttack(sEnemy.getAttack(true) * 0.75, true);
        sBattleActivityOperator.turnSpellButtons(false);
        sHandlerTimers.post(sRTrapTimer);
    }

    public void archerFocusedShot() {
        sSoundManager.playSound(sSoundManager.sSpellFocusedShot);
        sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 2);
        if (sHero.getEnergy(false) + sHero.getEnergy(true) / 5 < sHero.getEnergy(true))
            sHero.setEnergy(sHero.getEnergy(true) / 2, true);
        else sHero.setEnergy(sHero.getEnergy(true), false);
    }

    public void archerVictoryRush() {
        sSoundManager.playSound(sSoundManager.sSpellVictoryRush);
        if (sEnemy.getHealth(false) < sEnemy.getHealth(true) / 10 * 4) sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 5.5);
        else sBattleActivityOperator.handleStrike(true, true, sHero.getAttack(true) * 3.5);
    }

    public void archerSap(){
        sSoundManager.playSound(sSoundManager.sSpellSap);
        sEnemy.setStun(true);
        if (sEnemy.getEnergy(false) - sEnemy.getHealth(true) / 4 > 0) sEnemy.setEnergy(-sEnemy.getEnergy(true) / 4, true);
        else sEnemy.setEnergy(0, false);
        sBattleActivityOperator.turnMainTimers(3, false);
        sBattleActivityOperator.createDamageInd(0, "stun", true);
        sHandlerTimers.postDelayed(sRSapTimer, 10000);
    }
    public void archerThrillOfHunt(){sHandlerTimers.post(sRThrillOfHuntTimer);}
}