package com.wellographics.tapknight;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class SoundManager {
    SoundPool smSoundPool;

    AssetManager smAssetManager;

    int sLevelUp, sCoin, sMiss1, sMiss2, sBattleWin, sWear1, sStartActivity, sButtonUse,
            sGameDialogOpen, sGameDialogClose, sBattleLose, sTurnPage1, sTurnPage2, sCloseActivity,
            sEnemyDeath1,sEnemyDeath2, sEnemyDeath3, sEnemyBlock, sEnemyCrit1, sEnemyCrit2, sEnemyCrit3, sEnemyAttack1, sEnemyAttack2, sEnemyAttack3,
            sPlayerDeath, sPlayerAttack1, sPlayerAttack2, sPlayerAttack3, sPlayerCrit1, sPlayerCrit2, sPlayerCrit3,  sPlayerBlock1, sPlayerBlock2,  sPlayerBlock3,
            sSpellFireball1, sSpellFireball2, sSpellFireball3,  sSpellBunchOfLight1, sSpellBunchOfLight2, sSpellFrostyWhirlwind, sSpellArcaneFlow, sSpellBurningSoul1,
            sSpellBurningSoul2, sSpellBurningSoul3,
            sSpellFocusedShot, sSpellSap, sSpellThrillOfHunt, sSpellTrap, sSpellVictoryRush,
            sSpellHeroicStrike, sSpellHolyArmor, sSpellLightRetribution, sSpellRestore, sSpellSealStrength;

    public SoundLoader sSoundLoader;

    static public String sLocation;

    SoundManager(int maxStreams, AssetManager assetManager, String location) {loadSound(maxStreams, assetManager, location);}

    private void loadSound(int maxStreams, AssetManager assetManager, String location) {
        smAssetManager = assetManager;
        smSoundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        sLocation = location;
        sSoundLoader = new SoundLoader();
        sSoundLoader.execute();
    }

    public void stopStream() {
        if (sSoundLoader == null) return;
        sSoundLoader.cancel(true);
    }

    class SoundLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            sButtonUse = loadSound("sound/battle/sd_battle_spelluse.ogg");
            sCloseActivity = loadSound("sound/general/sd_gen_closeactivity.ogg");
            sCoin = loadSound("sound/general/sd_gen_buy.wav");
            switch (SoundManager.sLocation) {
                case "BattleActivity":
                    sEnemyDeath1 = loadSound("sound/battle/sd_battle_enemy_death1.ogg");
                    sEnemyDeath2 = loadSound("sound/battle/sd_battle_enemy_death2.ogg");
                    sEnemyDeath3 = loadSound("sound/battle/sd_battle_enemy_death3.ogg");
                    sEnemyBlock = loadSound("sound/battle/sd_battle_enemy_block.ogg");
                    sEnemyCrit1 = loadSound("sound/battle/sd_battle_enemy_crit1.ogg");
                    sEnemyCrit2 = loadSound("sound/battle/sd_battle_enemy_crit2.ogg");
                    sEnemyCrit3 = loadSound("sound/battle/sd_battle_enemy_crit3.ogg");
                    sEnemyAttack1 = loadSound("sound/battle/sd_battle_enemy_attack1.mp3");
                    sEnemyAttack2 = loadSound("sound/battle/sd_battle_enemy_attack2.mp3");
                    sEnemyAttack3 = loadSound("sound/battle/sd_battle_enemy_attack3.mp3");

                    sPlayerDeath = loadSound("sound/battle/sd_battle_player_death.ogg");
                    sPlayerAttack1 = loadSound("sound/battle/sd_battle_player_attack1.ogg");
                    sPlayerAttack2 = loadSound("sound/battle/sd_battle_player_attack2.ogg");
                    sPlayerAttack3 = loadSound("sound/battle/sd_battle_player_attack3.ogg");
                    sPlayerCrit1 = loadSound("sound/battle/sd_battle_player_crit1.ogg");
                    sPlayerCrit2 = loadSound("sound/battle/sd_battle_player_crit2.ogg");
                    sPlayerCrit3 = loadSound("sound/battle/sd_battle_player_crit3.ogg");
                    sPlayerBlock1 = loadSound("sound/battle/sd_battle_player_block1.ogg");
                    sPlayerBlock2 = loadSound("sound/battle/sd_battle_player_block2.ogg");
                    sPlayerBlock3 = loadSound("sound/battle/sd_battle_player_block3.ogg");

                    sMiss1 = loadSound("sound/battle/sd_battle_miss1.ogg");
                    sMiss2 = loadSound("sound/battle/sd_battle_miss2.ogg");
                    sLevelUp = loadSound("sound/general/sd_gen_levelup.ogg");
                    sBattleWin = loadSound("sound/battle/sd_battle_win.ogg");
                    sBattleLose = loadSound("sound/battle/sd_battle_lose.ogg");
                    break;
                case "MenuActivity":
                    sStartActivity = loadSound("sound/general/sd_gen_startactivity.ogg");
                    sGameDialogOpen = loadSound("sound/general/sd_gen_gamedialog_open.ogg");
                    sGameDialogClose = loadSound("sound/general/sd_gen_gamedialog_close.ogg");
                    break;
                case "Inventory":
                    sWear1 = loadSound("sound/general/sd_gen_wear_1.ogg");
                    break;
                case "ShopActivity":
                    sTurnPage1 = loadSound("sound/general/sd_gen_turnpage_1.ogg");
                    sTurnPage2 = loadSound("sound/general/sd_gen_turnpage_2.ogg");
                    sCloseActivity = loadSound("sound/general/sd_gen_closeactivity.ogg");
                    break;
                case "SignUpActivity":
                    break;
                case "SpellWizard":
                    sSpellFireball1 = loadSound("sound/spell/sd_spell_wizard_fireball_01.ogg");
                    sSpellFireball2 = loadSound("sound/spell/sd_spell_wizard_fireball_02.ogg");
                    sSpellFireball3 = loadSound("sound/spell/sd_spell_wizard_fireball_03.ogg");
                    sSpellBunchOfLight1 = loadSound("sound/spell/sd_spell_wizard_bunchoflight_01.ogg");
                    sSpellBunchOfLight2 = loadSound("sound/spell/sd_spell_wizard_bunchoflight_02.ogg");
                    sSpellFrostyWhirlwind = loadSound("sound/spell/sd_spell_wizard_frostywhirlwind.ogg");
                    sSpellArcaneFlow = loadSound("sound/spell/sd_spell_wizard_arcaneflow.ogg");
                    sSpellBurningSoul1 = loadSound("sound/spell/sd_spell_wizard_burningsoul_01.ogg");
                    sSpellBurningSoul2 = loadSound("sound/spell/sd_spell_wizard_burningsoul_02.ogg");
                    sSpellBurningSoul3 = loadSound("sound/spell/sd_spell_wizard_burningsoul_03.ogg");
                    break;
                case "SpellArcher":
                    sSpellFocusedShot = loadSound("sound/spell/sd_spell_archer_focusedshot.ogg");
                    sSpellSap = loadSound("sound/spell/sd_spell_archer_sap.ogg");
                    sSpellThrillOfHunt = loadSound("sound/spell/sd_spell_archer_thrillofhunt.ogg");
                    sSpellTrap = loadSound("sound/spell/sd_spell_archer_trap.ogg");
                    sSpellVictoryRush = loadSound("sound/spell/sd_spell_archer_victoryrush.ogg");
                    break;
                case "SpellPaladin":
                    sSpellHeroicStrike = loadSound("sound/spell/sd_spell_paladin_heroicstrike.ogg");
                    sSpellHolyArmor = loadSound("sound/spell/sd_spell_paladin_holyarmor.ogg");
                    sSpellLightRetribution = loadSound("sound/spell/sd_spell_paladin_lightretribution.ogg");
                    sSpellRestore = loadSound("sound/spell/sd_spell_paladin_restore.ogg");
                    sSpellSealStrength = loadSound("sound/spell/sd_spell_paladin_sealstrength.ogg");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd = null;
        try {afd = smAssetManager.openFd(fileName);}
        catch (IOException e) {
            e.printStackTrace();
            Log.wtf("WTF", "Couldn't load file '" + fileName + "'");
            return -1;
        }
        return smSoundPool.load(afd, 1);
    }

    public void playSound(int sound) {if (sound > 0) smSoundPool.play(sound, 1, 1, 1, 0, 1);}

}
