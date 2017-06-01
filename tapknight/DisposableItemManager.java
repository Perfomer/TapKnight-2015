package com.wellographics.tapknight;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageButton;

import java.util.ArrayList;

public class DisposableItemManager {
    private BattleActivity diBattleActivityOperator;
    private boolean diUsable;
    private Item[] diItemsArray;
    private int diChoosenItemsArray[];
    private Hero diHero;
    private Runnable diRPoisonousParfume;
    private Handler diHandlerTimers;
    private Context diContext;
    public final int MAX_DISPOSABLE_ITEMS = 3;

    public DisposableItemManager(BattleActivity ba, int[] args) {
        diBattleActivityOperator = ba;
        diContext = ba;
        diHero = ba.bHero;
        diChoosenItemsArray = args;
        diHandlerTimers = new Handler();
        diItemsArray = getAvailableDisposableItemsArray();


        diRPoisonousParfume = new Runnable() {
            @Override
            public void run() {
                if (diBattleActivityOperator.checkDeath(false)) diHandlerTimers.removeCallbacks(this);
                diBattleActivityOperator.handleStrike(true, true, diBattleActivityOperator.bEnemy.getHealth(true) / 20);
                diHandlerTimers.postDelayed(this, 1000);
            }
        };
    }

    public DisposableItemManager(Context context, Hero hero) {
        diContext = context;
        diHero = hero;
        diItemsArray = getAvailableDisposableItemsArray();

    }

    private boolean isUsable(int id) {
        boolean usable = false;
        switch (id) {
            case 21:
            case 22:
            case 23:
                usable = true; break;
        }
        return usable;
    }

    public Item[] getAvailableDisposableItemsArray() {
        ArrayList<Item> allThings = new ArrayList<>();
        int[] array = diHero.getBagThingsArray();
        for (int i = 0; i < array.length; i++) {
            Item item = new Item(i, diContext);
            if (item.getValue() == 0) break;
            if (item.getCategoryId() == 11) allThings.add(item);
        }
        return allThings.toArray(new Item[allThings.size()]);
    }

    public Integer[] getAvailableDisposableItemsIdsArray() {
        ArrayList<Integer> allThings = new ArrayList<>();
        int[] array = diHero.getBagThingsArray();
        for (int i = 0; i < array.length; i++) {
            Item item = new Item(i, diContext);
            if (item.getValue() == 0) break;
            if (item.getCategoryId() == 11) allThings.add(item.getId());
        }
        return allThings.toArray(new Integer[allThings.size()]);
    }

    public void applyUnusableItems() {
        for (int i = 0; i < diChoosenItemsArray.length; i++) {
            Item item = new Item(diChoosenItemsArray[i], diContext);
            if (!isUsable(item.getId())) useItem(i);
        }
    }



    public void useItem(int queue) {
        Item item = new Item(diChoosenItemsArray[queue], diBattleActivityOperator);
        switch(item.getId()) {
            case 20: diBattleActivityOperator.bEnemy.setAttack(diBattleActivityOperator.bEnemy.getAttack(true) * 0.75, false); break;
            case 21:
                diBattleActivityOperator.handleStrike(true, true, diBattleActivityOperator.bEnemy.getHealth(true) * 0.25);
            case 22: diBattleActivityOperator.bHero.setEnergy(diBattleActivityOperator.bHero.getEnergy(true), false); break;
            case 23: diBattleActivityOperator.bHero.setHealth(diBattleActivityOperator.bHero.getHealth(true) * 0.75, true); break;
            case 24: diHandlerTimers.post(diRPoisonousParfume); break;
        }
        diChoosenItemsArray[queue] = -1;
    }
}
