package com.wellographics.tapknight;

import android.content.Context;

public class Creature extends Primal {
    protected boolean cFlyable;
    protected int cCasts[] = new int[3], cId, cLocated;

    public Creature(int creatureId, Context context){
        cId = creatureId;
        dContext = context;
        cLocated = 1;
        switch (creatureId){
            case 0:
                setLevel(1, false);
                setStamina(7, false);
                setSpirit(1, false);
                setAgility(1, false);
                setPower(3, false);
                setCritical(3, false);
                setAccuracy(5, false);
                setBlock   (5, false);
                cFlyable = true;
                break;

            case 1:
                setLevel(2, false);
                setStamina(35, false);
                setSpirit(2, false);
                setAgility(2, false);
                setPower(3, false);
                setCritical(3, false);
                setAccuracy(5, false);
                setBlock   (5, false);
                cFlyable = true;
                break;
            case 2:
                cLocated = 2;
                setLevel(3, false);
                setStamina(150, false);
                setSpirit(2, false);
                setAgility(5, false);
                setPower(7, false);
                setCritical(2, false);
                setAccuracy(5, false);
                setBlock   (5, false);
                cFlyable = true;
                break;
            case 3:
                setLevel(4, false);
                setStamina(220, false);
                setSpirit(5, false);
                setAgility(17, false);
                setPower(9, false);
                setCritical(2, false);
                setAccuracy(15, false);
                setBlock   (10, false);
                cFlyable = true;
                break;
            case 4:
                cLocated = 2;
                setLevel(5, false);
                setStamina(270, false);
                setSpirit(10, false);
                setAgility(10, false);
                setPower(12, false);
                setCritical(2, false);
                setAccuracy(25, false);
                setBlock   (10, false);
                cFlyable = true;
                break;
            case 5:
                setLevel(6, false);
                setStamina(65, false);
                setSpirit(20, false);
                setAgility(25, false);
                setPower(22, false);
                setCritical(6, false);
                setAccuracy(35, false);
                setBlock   (25, false);
                cFlyable = true;
                break;
            case 6:
                cLocated = 3;
                setLevel(7, false);
                setStamina(100, false);
                setSpirit(30, false);
                setAgility(35, false);
                setPower(30, false);
                setCritical(7, false);
                setAccuracy(45, false);
                setBlock   (30, false);
                cFlyable = false;
                break;
            case 7:
                setLevel(8, false);
                setStamina(140, false);
                setSpirit(40, false);
                setAgility(45, false);
                setPower(30, false);
                setCritical(3, false);
                setAccuracy(45, false);
                setBlock   (20, false);
                cFlyable = false;
                break;
            case 8:
                setLevel(9, false);
                setStamina(200, false);
                setSpirit(40, false);
                setAgility(45, false);
                setPower(30, false);
                setCritical(3, false);
                setAccuracy(45, false);
                setBlock   (20, false);
                cFlyable = false;
                break;
            case 9:
                setLevel(10, false);
                setStamina(250, false);
                setSpirit(40, false);
                setAgility(45, false);
                setPower(35, false);
                setCritical(3, false);
                setAccuracy(45, false);
                setBlock   (20, false);
                cFlyable = false;
                break;
            case 10:
                cLocated = 2;
                setLevel(11, false);
                setStamina(300, false);
                setSpirit(40, false);
                setAgility(45, false);
                setPower(35, false);
                setCritical(3, false);
                setAccuracy(45, false);
                setBlock   (20, false);
                cFlyable = false;
                break;
            case 11:
                setLevel(12, false);
                setStamina(350, false);
                setSpirit(40, false);
                setAgility(45, false);
                setPower(35, false);
                setCritical(3, false);
                setAccuracy(45, false);
                setBlock   (20, false);
                cFlyable = false;
                break;
            case 12:
                cLocated = 3;
                setLevel(13, false);
                setStamina(450, false);
                setSpirit(40, false);
                setAgility(35, false);
                setPower(50, false);
                setCritical(3, false);
                setAccuracy(45, false);
                setBlock   (20, false);
                cFlyable = false;
                break;
            case 13:
                cLocated = 2;
                setLevel(14, false);
                setStamina(500, false);
                setSpirit(40, false);
                setAgility(45, false);
                setPower(50, false);
                setCritical(8, false);
                setAccuracy(45, false);
                setBlock   (20, false);
                cFlyable = false;
                break;
        }
        setStun(false);
        setAttack(getAttack(true), false);
        setHealth(getHealth(true), false);
        setEnergy(getEnergy(true), false);
    }

    public boolean isFlyable(){return cFlyable;}
    public String getName() {
        return dContext.getString(
                dContext.getResources().getIdentifier(
                        "st_enemy_name_" + String.valueOf(cId + 1), "string", dContext.getPackageName()
                )
        );
    }
    public int[] getCastIdsArray(){
        int[] array = {cCasts[0], cCasts[1], cCasts[2]};
        return array;
    }

    public int getDrawableView() {
        return dContext.getResources().getIdentifier(
                "dr_enemy_view_" + String.valueOf(cId + 1), "drawable", dContext.getPackageName()
        );
    }

    public int getCreatureId() {return cId;}

    public int getLocation() {return cLocated;}
}
