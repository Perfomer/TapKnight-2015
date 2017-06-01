package com.wellographics.tapknight;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class ActionBarManager {
    private Animation abAMoneyWiggle;
    private Button abMoney, abEnergy, abDiamond;
    private ImageButton abHeroIcon;
    private Hero abCharacter;
    private Activity abActivity;
    private InventoryManager abInventoryManager;
    private WelloFontManager abWFManager;

    public ActionBarManager(Activity context) {
        abActivity = context;
        abWFManager = new WelloFontManager(abActivity);
        abMoney = abWFManager.getButton(R.id.actionbar_money, 2, false);
        abEnergy = abWFManager.getButton(R.id.actionbar_energy, 2, false);
        abDiamond = abWFManager.getButton(R.id.actionbar_diamond, 2, false);
        abHeroIcon = (ImageButton) abActivity.findViewById(R.id.actionbar_char_icon);
        abAMoneyWiggle = AnimationUtils.loadAnimation(abActivity, R.anim.an_activity_refresh_text);
        abInventoryManager = new InventoryManager(abActivity);
        refreshActionBarInfo();
    }

    public void refreshActionBarInfo() {
        abCharacter = new Hero(abActivity);
        abMoney.setText(String.valueOf(abCharacter.getMoney()));
        abHeroIcon.setBackgroundResource(abCharacter.getClassIcon(1));
    }

    public void selectItem(Object tag) {abInventoryManager.selectBagItem(tag);}

    public void removeButton() {abInventoryManager.removeBagItem(); refreshActionBarInfo();}

    public void equipButton(View v) {
        abInventoryManager.equipItem();
        refreshActionBarInfo();
    }

    public void onHeaderButtonClick(View v) {abInventoryManager.setInventoryPage(Integer.parseInt(String.valueOf(v.getTag())));}

    public void onHeroClick() {abInventoryManager.onHeroClick();}

    public void moneyAnimation() {abMoney.startAnimation(abAMoneyWiggle);}

    public void onMoneyClick() {}

    public void closeInventory() {abInventoryManager.showInventory(false);}

    public void onCharButtonClick() {abInventoryManager.showInventory(true);}

    public void onEquipViewClick(Object tag) {abInventoryManager.selectEquipmentItem(tag);}

    public void onUnequipClick() {abInventoryManager.unequipItem();}
}
