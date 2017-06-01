package com.wellographics.tapknight;

import android.content.Context;

public class Primal {
    public static final int MISS_CHANCE = 5, VARIATION_FACTOR = 40;
    protected int dLevel, dStamina, dSpirit, dAgility, dPower, dCritical, dAccuracy, dBlock;
    protected double dHealth, dEnergy, dAttack;
    protected boolean dStunned;
    protected Context dContext;

    public double getHealth(boolean full) {
        if (full) return dStamina * 10 + dLevel * 25;
        else return dHealth;
    }

    public double getEnergy(boolean full) {
        if (full) return dSpirit * 10 + dLevel * 25;
        else return dEnergy;
    }

    public double getAttack(boolean full) {
        if (full) return dPower * dLevel;
        else return dAttack;
    }

    public int getCritical() {
        return dCritical + dPower / 2;
    }

    public int getDodge() {
        return dAgility + dAccuracy;
    }

    public int getBlock() {
        return dStamina + dBlock;
    }

    public int getStamina() {
        return dStamina;
    }

    public int getSpirit() {
        return dSpirit;
    }

    public int getLevel() {
        return dLevel;
    }

    public boolean isStunned() {
        return dStunned;
    }

    public int getAgility() {
        return dAgility;
    }

    public int getPower() {
        return dPower;
    }

    public int getAccuracy() {
        return dAccuracy;
    }

    public void setStun(boolean value) {
        dStunned = value;
    }

    public void setAttack(double value, boolean increase) {
        if (increase) dAttack += value;
        else dAttack = value;
    }

    public void setHealth(double value, boolean increase) {
        if (increase) dHealth += value;
        else dHealth = value;
    }

    public void setEnergy(double value, boolean increase) {
        if (increase) dEnergy += value;
        else dEnergy = value;
    }

    public void setLevel(int value, boolean increase) {
        if (increase) dLevel += value;
        else dLevel = value;
    }

    public void setStamina(int value, boolean increase) {
        if (increase) dStamina += value;
        else dStamina = value;
    }

    public void setSpirit(int value, boolean increase) {
        if (increase) dSpirit += value;
        else dSpirit = value;
    }

    public void setAgility(int value, boolean increase) {
        if (increase) dAgility += value;
        else dAgility = value;
    }

    public void setPower(int value, boolean increase) {
        if (increase) dPower += value;
        else dPower = value;
    }

    public void setCritical(int value, boolean increase) {
        if (increase) dCritical += value;
        else dCritical = value;
    }

    public void setAccuracy(int value, boolean increase) {
        if (increase) dAccuracy += value;
        else dAccuracy = value;
    }

    public void setBlock(int value, boolean increase) {
        if (increase) dBlock += value;
        else dBlock = value;
    }
}