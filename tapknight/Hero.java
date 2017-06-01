package com.wellographics.tapknight;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Hero extends Primal {
    protected final int MAX_THINGS_IN_EQUIPMENT = 11 ,MAX_THINGS_IN_BAG = 21;
    protected int hClassId, hExperience, hMoney, hBagThings[], hEquipmentThings[], hOpenedLocations[], hLastLevel;
    protected SharedPreferences hSPOperator;
    protected String hPreferencesIds[] = {
            "player_class_id", "player_level", "last_level", "player_experience", "player_money", "player_stamina", "player_spirit",
            "player_agility", "player_power", "player_block", "player_accuracy", "player_critical"
    };



    public Hero(int classId, Context context) {
        prepareArrays();

        dContext = context;
        hClassId = classId;
        hOpenedLocations = new int[10];
        hSPOperator = dContext.getSharedPreferences("PlayerStatsPref", dContext.MODE_PRIVATE);
        setMoney(0, false);
        setExperience(0, false);
        setLevel(1, false);
        setStamina(1, false);
        setSpirit(1, false);
        setAgility(1, false);
        setPower(1, false);
        setCritical(3, false);
        setAccuracy(5, false);
        setBlock   (5, false);
        setLastLevel(0, false);

        saveInfo();
    }

    public Hero(Context context) {
        dContext = context;
        hSPOperator = dContext.getSharedPreferences("PlayerStatsPref", dContext.MODE_PRIVATE);
        setActualInfo();
    }

    public boolean isNewLocation(int locationID) {
        for(int i = 0; i < hOpenedLocations.length; i++)
            if (hOpenedLocations[i] == locationID) return false;
        return true;
    }

    public void addNewLocation(int locationID) {
        for(int i = 0; i < hOpenedLocations.length; i++)
            if (hOpenedLocations[i] == 0) {
                hOpenedLocations[i] = locationID;
                saveInfo();
                return;
            }
    }

    public int getClassId() {return hClassId;}

    public String getClassName() {
        switch (getClassId()) {
            case 1: return "Archer";
            case 2: return "Wizard";
            case 3: return "Paladin";
            default: return "";
        }
    }

    public int[] getBagThingsArray() {return hBagThings;}

    public void prepareArrays() {
        hBagThings = new int[MAX_THINGS_IN_BAG];
        for (int i = 0; i < hBagThings.length; i++) hBagThings[i] = -1;
        hEquipmentThings = new int[MAX_THINGS_IN_EQUIPMENT];
        for (int i = 0; i < hEquipmentThings.length; i++) hEquipmentThings[i] = -1;
        hOpenedLocations = new int[10];
    }

    public void setLastLevel(int value, boolean increase) {
        if (increase) hLastLevel += value;
        else hLastLevel = value;
    }

    public int getLastLevel() {return hLastLevel;}

    public int getMoney() {return hMoney;}

    public int[] getThingsArray() {
        organizeBagThingsArray();
        return hBagThings;
    }

    public int getExperience(boolean full) {
        if (full) return (int) (10 * Math.pow(5, getLevel() - 1));
        else return hExperience;
    }

    public void setExperience(int value, boolean increase) {
        if (increase) hExperience += value;
        else hExperience = value;
        checkLevelUpgrade();
    }

    public void checkLevelUpgrade() {
        if (getExperience(true) > getExperience(false)) return;
        else {
            SoundManager soundManager = new SoundManager(1, dContext.getAssets(), "");
            soundManager.playSound(soundManager.sLevelUp);
            setExperience(getExperience(false) - getExperience(true), false);
            setLevel(1, true);
            setStamina(getLevel(), true);
            setSpirit(getLevel(), true);
            setAgility(getLevel(), true);
            setPower(getLevel(), true);
            if (getLevel() % 2 == 0) setCritical(1, true);
            setAccuracy(3, true);
            setBlock   (1, true);
            saveInfo();
        }
    }

    protected int[] refreshPreferencesValues(){
        int preferencesValues[] = {hClassId, dLevel, hLastLevel, hExperience, hMoney, dStamina, dSpirit, dAgility, dPower, dBlock, dAccuracy, dCritical};
        return preferencesValues;
    }

    public void organizeBagThingsArray() {
        String string = hSPOperator.getString("player_bag_things_array", "@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1");
        char[] charArray = string.toCharArray();
        String id = "";
        int[] things = new int[MAX_THINGS_IN_BAG];
        for (int i = 1, j = 0; i < charArray.length; i++) {
            if (charArray[i] != '@') id += charArray[i];
            else {
                things[j] = Integer.parseInt(id);
                Log.d("abbad0n", "\ni = " + String.valueOf(i) + ";\nj = " + String.valueOf(j) + ";\nid = " + id + ";\n things[j] = " + String.valueOf(things[j]));
                id = "";
                j++;
            }

        }
        hBagThings = things;
    }

    public void organizeEquipmentThingsArray() {
        String string = hSPOperator.getString("player_equip_things_array", "@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1@-1");
        char[] charArray = string.toCharArray();
        String id = "";
        int[] things = new int[MAX_THINGS_IN_BAG];
        for (int i = 1, j = 0; i < charArray.length; i++) {
            if (charArray[i] != '@') id += charArray[i];
            else {
                things[j] = Integer.parseInt(id);
                Log.d("abbad0n", "\ni = " + String.valueOf(i) + ";\nj = " + String.valueOf(j) + ";\nid = " + id + ";\n things[j] = " + String.valueOf(things[j]));
                id = "";
                j++;
            }

        }
        hEquipmentThings = things;
    }

    private void organizeLocationArray() {
        String string = hSPOperator.getString("player_locations_array", "@0@0@0@0@0@0@0@0@0@0");
        char[] charArray = string.toCharArray();
        String id = "";
        int[] locations = new int[10];
        for (int i = 1, j = 0; i < charArray.length; i++) {
            if (charArray[i] != '@') id += charArray[i];
            else {
                locations[j] = Integer.parseInt(id);
                id = "";
                j++;
            }
        }
        hOpenedLocations = locations;
    }

    protected String getLocationString() {
        String locationList = "";
        for (int i = 0; i < hOpenedLocations.length; i++) locationList += "@" + String.valueOf(hOpenedLocations[i]);
        return locationList;
    }

    public Integer[] getAvailableThingsArray() {
        ArrayList<Integer> allThings = new ArrayList<Integer>();
        for (int i = 0;; i++) {
            Item item = new Item(i, dContext);
            Log.d("ItemDebug",
                            "ID/Name: " + String.valueOf(item.getId()) + "/" + item.getName() +
                            "\nCategory: " + item.getCategoryName() +
                            "\nClassID: " + String.valueOf(item.getPlayerClass()) +
                            "\nValue: " + String.valueOf(item.getValue()) +
                            "\nDescription: " + item.getDescription());
            if (item.getValue() == 0) break;
            if (item.getPlayerClass() == getClassId() || item.getPlayerClass() == 0) allThings.add(item.getId());
        }
        return allThings.toArray(new Integer[allThings.size()]);
    }

    public int findThingInBag(int id) {
        for (int i = 0; i < hBagThings.length; i++) if (hBagThings[i] == id) return i;
        return -1;
    }

    protected String getBagThingsString() {
        String thingsList = "";
        for (int i = 0; i < hBagThings.length; i++) thingsList += "@" + String.valueOf(hBagThings[i]);
        thingsList += "@-1";
        return thingsList;
    }

    protected String getEquipmentThingsString() {
        String thingsList = "";
        for (int i = 0; i < hEquipmentThings.length; i++) thingsList += "@" + String.valueOf(hEquipmentThings[i]);
        thingsList += "@-1";
        return thingsList;
    }



    private boolean checkSlot(int category) {
        int thingQue = findThingInBag(hEquipmentThings[category]);
        if (thingQue != -1) return hBagThings[thingQue] != -1;
        return false;
    }

    public boolean addThingInABag(int id) {
        for (int i = 0; i < MAX_THINGS_IN_BAG; i++)
            if (hBagThings[i] == -1) {
                hBagThings[i] = id;
                saveInfo();
                return true;
            }
        Toast.makeText(dContext, R.string.st_er_notspace, Toast.LENGTH_LONG).show();
        return false;
    }

    public boolean equipThingOnHero(int id) {
        Item item = new Item(id, dContext);
        if (hEquipmentThings[item.getCategoryId()] != -1 || addThingInABag(hEquipmentThings[item.getCategoryId()])) {
            hEquipmentThings[item.getCategoryId()] = id;

            setAccuracy(item.getAccuracy(), true);
            setAgility(item.getAgility(), true);
            setBlock(item.getBlock(), true);
            setCritical(item.getCritical() / 2, true);
            setPower(item.getPower(), true);
            setSpirit(item.getSpirit(), true);
            setStamina(item.getStamina(), true);
            setAttack(item.getAttack(), true);
            saveInfo();
            return true;
        }
        return false;
    }

    public void unequipThing(int category) {
        Item item = new Item(hEquipmentThings[category], dContext);
        if (addThingInABag(hEquipmentThings[category])) {
            hEquipmentThings[category] = -1;
            setAccuracy(-item.getAccuracy(), true);
            setAgility(-item.getAgility(), true);
            setBlock(-item.getBlock(), true);
            setCritical(-item.getCritical() / 2, true);
            setPower(-item.getPower(), true);
            setSpirit(-item.getSpirit(), true);
            setStamina(-item.getStamina(), true);
            setAttack(-item.getAttack(), true);
            saveInfo();
       }
    }

    public void removeThing(int queue) {
        Item item = new Item(getBagThingId(queue), dContext);
        setMoney(item.getValue() / 4, true);
        for (int i = queue; i < MAX_THINGS_IN_BAG - 1; i++) hBagThings[i] = hBagThings[i + 1];
        hBagThings[MAX_THINGS_IN_BAG - 1] = -1;
        saveInfo();
    }

//    public boolean addThingToBag(int id) {
//        Item item = new Item(id, dContext);
//        if (checkSlot(item.getCategoryId())) {
//            Log.d("BLABLABLA", "Одеваем вещь. ID: " + String.valueOf(id));
//            for (int i = 0; i < hBagThings.length; i++) {
//                if (hBagThings[i] == -1) {
//                    hBagThings[i] = id;
//                    saveInfo();
//                    return true;
//                }
//            }
//        }
//        else {
//            Log.d("StarOfDeath", String.valueOf(id));
//            equipThing(id);
//            return true;
//        }
//        return false;
//    }

//    public void equipThing(int id) {
//        Item item = new Item(id, dContext);
//        if (checkSlot(item.getCategoryId())) {
//            Log.d("BLABLABLA", "Добавляем вещь в сумку. ID: " + String.valueOf(id));
//            Item item2 = new Item(hEquipmentThings[item.getCategoryId()], dContext);
//            addThingToBag(item2.getId());
//        }
//        hEquipmentThings[item.getCategoryId()] = item.getId();
//        setAccuracy(item.getAccuracy(), true);
//        setAgility(item.getAgility(), true);
//        setBlock(item.getBlock(), true);
//        setCritical(item.getCritical() / 2, true);
//        setPower(item.getPower(), true);
//        setSpirit(item.getSpirit(), true);
//        setStamina(item.getStamina(), true);
//        setAttack(item.getAttack(), true);
//        saveInfo();
//    }

    public int[] getEquipArray() {
        organizeEquipmentThingsArray();
        return hEquipmentThings;
    }

    public int[] getLocationsArray() {
        organizeLocationArray();
        return hOpenedLocations;
    }

//    public void unequipThing(int category) {
//        if (findItemSlotInBag() != -1) {
//            Item item = new Item(hEquipmentThings[category], dContext);
//            addThingToBag(item.getId());
//        }
//        else Toast.makeText(dContext, dContext.getString(R.string.st_er_notspace), Toast.LENGTH_SHORT).show();
//    }
//    public void removeThing(int listQueue) {
//        Item item = new Item(hBagThings[listQueue], dContext);
//        setAccuracy(-item.getAccuracy(), true);
//        setAgility(-item.getAgility(), true);
//        setBlock(-item.getBlock(), true);
//        setCritical(-item.getCritical(), true);
//        setPower(-item.getPower(), true);
//        setSpirit(-item.getSpirit(), true);
//        setStamina(-item.getStamina(), true);
//        setAttack(-item.getAttack(), true);
//        for (int i = listQueue + 1; i < hBagThings.length; i++) hBagThings[i - 1] = hBagThings[i];
//        hBagThings[MAX_THINGS_IN_BAG - 1] = -1;
//        saveInfo();
//    }

    public int getBagThingId(int queue) {
        return hBagThings[queue];
    }

    public int getEquipmentThingId(int queue) {
        return hEquipmentThings[queue];
    }

    public void setMoney(int value, boolean increase) {
        if (increase) hMoney += value;
        else hMoney = value;
    }

    public int getClassIcon(int type) {
        String className = "", typeDrawable = "";
        switch (hClassId) {
            case 1: className = "archer"; break;
            case 2: className = "wizard"; break;
            case 3: className = "paladin"; break;
        }
        switch (type) {
            case 1: typeDrawable = "symb"; break;
            case 2: typeDrawable = "char"; break;
            case 3: typeDrawable = "full"; break;
        }
        return dContext.getResources().getIdentifier(
                "dr_icon_class_" + typeDrawable + "_" + className, "drawable", dContext.getPackageName()
        );
    }

    public void saveInfo() {
        SharedPreferences.Editor ed = hSPOperator.edit();
        for (int i = 0; i < refreshPreferencesValues().length; i++) ed.putInt(hPreferencesIds[i], refreshPreferencesValues()[i]);
        ed.putString("player_equip_things_array", getEquipmentThingsString());
        ed.putString("player_bag_things_array", getBagThingsString());
        ed.putString("player_locations_array", getLocationString());
        ed.commit();
    }

    public void setActualInfo() {
        hClassId = hSPOperator.getInt(hPreferencesIds[0], 0);
        setLevel(hSPOperator.getInt(hPreferencesIds[1], 1), false);
        setLastLevel(hSPOperator.getInt(hPreferencesIds[2], 0), false);
        hExperience = hSPOperator.getInt(hPreferencesIds[3], 1);
        setMoney(hSPOperator.getInt(hPreferencesIds[4], 0), false);
        setStamina(hSPOperator.getInt(hPreferencesIds[5], 1), false);
        setSpirit(hSPOperator.getInt(hPreferencesIds[6], 1), false);
        setAgility(hSPOperator.getInt(hPreferencesIds[7], 1), false);
        setPower(hSPOperator.getInt(hPreferencesIds[8], 1), false);
        setBlock(hSPOperator.getInt(hPreferencesIds[9], 5), false);
        setAccuracy(hSPOperator.getInt(hPreferencesIds[10], 5), false);
        setCritical(hSPOperator.getInt(hPreferencesIds[11], 3), false);

        organizeBagThingsArray();
        organizeEquipmentThingsArray();
        organizeLocationArray();
        //processThingsArray();

        setHealth(getHealth(true), false);
        setEnergy(getEnergy(true), false);
        setAttack(getAttack(true), false);
    }

}