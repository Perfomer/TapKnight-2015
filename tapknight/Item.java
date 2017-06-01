package com.wellographics.tapknight;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class Item {
    protected int iId = -1, iAttack = 0, iStamina = 0, iSpirit = 0, iAgility = 0, iPower = 0, iCritical = 0, iAccuracy = 0,
            iBlock = 0, iDodge = 0, iHealth = 0, iEnergy = 0, iValue = 0, iCategory = 0, iClass = 0;
    protected Context iContext;
    protected boolean iWear, iBattleItem, iHardcodedDescription;

    public int getAttack(){return iAttack;}
    public int getStamina(){return iStamina;}
    public int getSpirit(){return iSpirit;}
    public int getPower(){return iPower;}
    public int getCritical(){return iCritical;}
    public int getAccuracy(){return iAccuracy;}
    public int getBlock(){return iBlock;}
    public int getDodge(){return iDodge;}
    public int getHealth(){return iHealth;}
    public int getEnergy(){return iEnergy;}
    public int getValue(){return iValue;}
    public int getAgility(){return iAgility;}
    public boolean isWearing() {return iWear;}
    public int getPlayerClass(){return iClass;}

    public int getDrawableIcon() {
        if (iId < 0) return R.drawable.dr_inventory_item_slot_empty;
        else
            return iContext.getResources().getIdentifier(
                    "dr_item_" + String.valueOf(iId) + "_icon", "drawable", iContext.getPackageName()
            );
    }

    public String getName() {
        try {
            return iContext.getString(
                    iContext.getResources().getIdentifier(
                            "st_item_name_" + String.valueOf(iId), "string", iContext.getPackageName()
                    )
            );
        }
        catch (Resources.NotFoundException e) {
            Log.e("Error!!!", String.valueOf(iId));
        }
        return null;
    }

    public int getCategoryId() {return iCategory;}
    public int getId() {return iId;}

    public String getCategoryName() {
        return iContext.getString(
                iContext.getResources().getIdentifier(
                        "st_item_category_" + String.valueOf(getCategoryId()), "string", iContext.getPackageName())
        );
    }

    public String getDescription(){
        if (iHardcodedDescription) return
            iContext.getString(iContext.getResources().
                    getIdentifier(
                            "st_item_descr_" + String.valueOf(iId), "string", iContext.getPackageName())
            );
        String descr = "";
        if (iAttack != 0) descr += "+" + String.valueOf(iAttack) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_attack_d) + "; ";
        if (iAgility != 0) descr += "+" + String.valueOf(iAgility) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_agility_d) + "; ";
        if (iStamina != 0) descr += "+" + String.valueOf(iStamina) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_stamina_d) + "; ";
        if (iSpirit != 0) descr += "+" + String.valueOf(iSpirit) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_spirit_d) + "; ";
        if (iPower != 0) descr += "+" + String.valueOf(iPower) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_power_d) + "; ";
        if (iCritical != 0) descr += "+" + String.valueOf(iCritical) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_critical_d) + "; ";
        if (iAccuracy != 0) descr += "+" + String.valueOf(iAccuracy) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_accuracy_d) + "; ";
        if (iDodge != 0) descr += "+" + String.valueOf(iDodge) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_dodge_d) + "; ";
        if (iBlock != 0) descr += "+" + String.valueOf(iBlock) + " " + iContext.getString(R.string.st_de_to) + " " + iContext.getString(R.string.st_de_block_d) + "; ";
        return descr;
    }


    Item(int itemId, Context context) {
        iContext = context;
        iId = itemId;
        iHardcodedDescription = false;
        iBattleItem = false;
        switch(itemId){
            case 0:
                iClass = 2;
                iValue = 40;
                iCategory = 10;
                iAttack = 1;
                iPower = 2; break;
            case 1:
                iClass = 2;
                iValue = 20;
                iCategory = 3;
                iStamina = 1;
                iSpirit = 2; break;
            case 2:
                iClass = 1;
                iValue = 65;
                iCategory = 3;
                iStamina = 4;
                iSpirit = 4; break;
            case 3:
                iClass = 0;
                iValue = 30;
                iCategory = 4;
                iAgility = 2;
                iPower = 1; break;
            case 4:
                iClass = 3;
                iValue = 80;
                iCategory = 4;
                iAgility = 5;
                iPower = 4; break;
            case 5:
                iClass = 2;
                iValue = 120;
                iCategory = 0;
                iSpirit = 10;
                iStamina = 9; break;
            case 6:
                iClass = 3;
                iValue = 120;
                iCategory = 8;
                iAttack = 9;
                iAgility = 10; break;
            case 7:
                iClass = 0;
                iValue = 160;
                iCategory = 10;
                iSpirit = 10;
                iAttack = 5; break;
            case 8:
                iClass = 0;
                iValue = 100;
                iCategory = 7;
                iAgility = 6;
                iStamina = 5; break;
            case 9:
                iClass = 1;
                iValue = 180;
                iCategory = 8;
                iAccuracy = 12;
                iAttack = 10; break;
            case 10:
                iClass = 1;
                iValue = 240;
                iCategory = 8;
                iAccuracy = 15;
                iAttack = 15; break;
            case 11:
                iClass = 1;
                iValue = 150;
                iCategory = 0;
                iAgility = 10;
                iDodge = 10; break;
            case 12:
                iClass = 3;
                iValue = 150;
                iCategory = 0;
                iStamina = 12;
                iBlock = 10; break;
            case 13:
                iClass = 2;
                iValue = 100;
                iCategory = 8;
                iAttack = 7;
                iAccuracy = 10; break;
            case 14:
                iClass = 3;
                iValue = 130;
                iCategory = 9;
                iBlock = 10;
                iStamina = 15; break;
            case 15:
                iClass = 3;
                iValue = 250;
                iCategory = 8;
                iAttack = 21; break;
            case 16:
                iClass = 3;
                iValue = 250;
                iCategory = 8;
                iAttack = 15;
                iStamina = 6; break;
            case 17:
                iClass = 3;
                iValue = 250;
                iCategory = 8;
                iPower = 11;
                iAttack = 10; break;
            case 18:
                iClass = 0;
                iValue = 300;
                iCategory = 5;
                iAgility = 15;
                iAccuracy = 15; break;
            case 19:
                iClass = 1;
                iValue = 250;
                iCategory = 5;
                iAgility = 15;
                iAccuracy = 12; break;
            case 20:case 21:case 22:case 23: case 24:
                iClass = 0;
                iHardcodedDescription = true;
                iBattleItem = true;
                iValue = 100;
                iCategory = 11; break;
        }
    }
}
