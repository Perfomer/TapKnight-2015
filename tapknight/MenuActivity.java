package com.wellographics.tapknight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuActivity extends Activity {

    RelativeLayout mRLLoading;
    ImageView mIVSettings, mIVPlay, mIVShop, mIVWello;
    Animation mASettingsButton, mAPlayButton, mAShopButton, mWelloAnim;
    ImageButton mIBPlay, mIBSettings, mIBShop;
    TextView mTVPlay, mTVSettings, mTVShop, mTVAbout;
    Button mBExit;
    AlertDialog.Builder mADBExit;
    Hero mHero;
    ActionBarManager mABManager;
    WindowManager mWManager;
    SoundManager mSoundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSoundManager = new SoundManager(3, getAssets(), getLocalClassName());
        mHero = new Hero(this);

        if (mHero.getClassId() == 0) {
            Intent goToSignUpActivity = new Intent(this, SignUpActivity.class);
            mSoundManager.playSound(mSoundManager.sStartActivity);
            startActivityForResult(goToSignUpActivity, 1);
        }

        setContentView(R.layout.activity_menu);
        WelloFontManager mWFontManager = new WelloFontManager(this);
        //mWManager = new WindowManager(this);

        mRLLoading = (RelativeLayout) findViewById(R.id.layout_menu_loading_layout);
        mIVPlay = (ImageView) findViewById(R.id.layout_menu_button_play_background);
        mIVSettings = (ImageView) findViewById(R.id.layout_menu_button_settings_background);
        mIVShop = (ImageView) findViewById(R.id.layout_menu_button_shop_background);
        mIVWello = (ImageView) findViewById(R.id.layout_menu_loading_image);
        mIBPlay = (ImageButton) findViewById(R.id.layout_menu_button_play);
        mIBSettings = (ImageButton) findViewById(R.id.layout_menu_button_settings);
        mIBShop = (ImageButton) findViewById(R.id.layout_menu_button_shop);

        mBExit = mWFontManager.getButton(R.id.layout_menu_button_exit, 3, true);
        mTVShop = mWFontManager.getTextView(R.id.layout_menu_shop_text, 3, true);
        mTVSettings = mWFontManager.getTextView(R.id.layout_menu_settings_text, 3, true);
        mTVPlay = mWFontManager.getTextView(R.id.layout_menu_play_text, 3, true);
        mTVAbout = mWFontManager.getTextView(R.id.layout_menu_about_text, 2, false);


        mAPlayButton = AnimationUtils.loadAnimation(this, R.anim.an_menu_button_rotate_01);
        mASettingsButton = AnimationUtils.loadAnimation(this, R.anim.an_menu_button_rotate_02);
        mAShopButton = AnimationUtils.loadAnimation(this, R.anim.an_menu_button_rotate_03);
        mWelloAnim = AnimationUtils.loadAnimation(this, R.anim.an_activity_wello);

        mIVPlay.startAnimation(mAPlayButton);
        mIVSettings.startAnimation(mASettingsButton);
        mIVShop.startAnimation(mAShopButton);

        setActionBarInfo();

        mADBExit = new AlertDialog.Builder(MenuActivity.this);
        mADBExit.setTitle(R.string.st_de_quit)
                .setMessage(R.string.st_de_accept_quit)
                .setNegativeButton(R.string.st_de_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg0) {
                        turnAllButtons(true);
                        mSoundManager.playSound(mSoundManager.sGameDialogClose);
                    }
                })
                .setPositiveButton(R.string.st_de_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        System.runFinalizersOnExit(true);
                        System.exit(0);
                        MenuActivity.this.finish();
                    }
                })
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        mSoundManager.playSound(mSoundManager.sGameDialogClose);
                    }
                });
        mWelloAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRLLoading.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mIVWello.startAnimation(mWelloAnim);
    }

    public void onBackPressed(){
        mADBExit.show();
    }

    public void onResume(){
        super.onResume();
        setActionBarInfo();
    }

    public void setActionBarInfo(){mABManager = new ActionBarManager(this);}

    public void onButtonClick(View v) {
        turnAllButtons(false);
        switch (Integer.parseInt(String.valueOf(v.getTag()))){
            case 1: startActivity(new Intent(this, SettingsActivity.class)); break;
            case 2: startActivity(new Intent(this, BattleActivity.class)); break;
            case 3: startActivity(new Intent(this, ShopActivity.class)); break;
            case 4: mSoundManager.playSound(mSoundManager.sGameDialogOpen); mADBExit.show();
        }
        if (Integer.parseInt(String.valueOf(v.getTag())) != 4) {
            mSoundManager.playSound(mSoundManager.sStartActivity);
            turnAllButtons(true);
        }
    }

    public void onCloseClick(View v) {turnAllButtons(true); mABManager.closeInventory();}
    public void onHeroClick(View v) {mABManager.onHeroClick();}
    public void onItemClick(View v) {mABManager.selectItem(v.getTag());}
    public void onSellButtonClick(View v) {mABManager.removeButton();}
    public void onHeaderButtonClick(View v) {mABManager.onHeaderButtonClick(v);}
    public void onEquipButtonClick(View v) {mABManager.equipButton(v);}
    public void onEquipViewClick(View v) {mABManager.onEquipViewClick(v.getTag());}
    public void onUnequipButtonClick(View v) {mABManager.onUnequipClick();}
    public void onMoneyClick(View v) {mABManager.onMoneyClick(); setActionBarInfo();}
    public void onEnergyClick(View v) {}
    public void onCharButtonClick(View v) {turnAllButtons(false); mABManager.onCharButtonClick();}

    public void turnAllButtons(boolean turn) {
        mIBPlay.setEnabled(turn);
        mIBShop.setEnabled(turn);
        mIBSettings.setEnabled(turn);
    }


    public void onWindowItemClick(View v) {mWManager.selectItem(v.getTag());}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {this.finish(); return;}
        Hero hero = new Hero(data.getIntExtra("class_id", 0), getApplicationContext());
        setActionBarInfo();
        //mTVPlay.setText(getString(R.string.st_de_play) + " (" + String.valueOf(mHero.getLastLevel() + 1) + " " + getString(R.string.st_level) + ")");
    }


    private class WindowManager {
        private RelativeLayout wmRL;
        private Button wmBClose, wmBStart;
        private TextView wmTVHeader;
        private int wmCheckedItems[], wmSelectedItem;
        private Activity wmContext;
        private WelloFontManager wmWFManager;
        private DisposableItemManager wmDIManager;

        public WindowManager(Activity context) {
            wmContext = context;
            wmWFManager = new WelloFontManager(context);
            wmSelectedItem = 0;
            wmDIManager = new DisposableItemManager(context, mHero);
            wmRL = (RelativeLayout) context.findViewById(R.id.window_background);
            wmBClose = wmWFManager.getButton(R.id.window_button_close, 2, true);
            wmBStart = wmWFManager.getButton(R.id.window_button_start, 2, true);
            wmTVHeader = wmWFManager.getTextView(R.id.window_header, 3, true);
            wmBClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WindowManager.this.closeWindow();
                }
            });
            wmBStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WindowManager.this.startBattle();
                }
            });
            refreshBag();
        }

        public void closeWindow() {
            wmRL.setVisibility(View.INVISIBLE);
        }

        public void startBattle() {
            Intent intent = new Intent(MenuActivity.this, BattleActivity.class);
            startActivity(intent);
        }

        public void openPreBattleWindow() {
            wmRL.setVisibility(View.VISIBLE);
        }

        private void setPreviewBagItemInfo() {
            ImageView imIVItem = (ImageView) findViewById(R.id.inventory_item_icon);
            TextView imTVItemName = wmWFManager.getTextView(R.id.inventory_item_name, 2, false);
            TextView imTVItemDescription = wmWFManager.getTextView(R.id.inventory_item_description, 2, false);

            Integer[] itemsarray = wmDIManager.getAvailableDisposableItemsIdsArray();
            if (wmSelectedItem != 0 && wmSelectedItem < itemsarray.length) {
                Item item = new Item(itemsarray[wmSelectedItem - 1], wmContext);
                imTVItemDescription.setText(item.getDescription());
                imTVItemDescription.setTextColor(getResources().getColor(R.color.cl_orange_name_available));
                imTVItemName.setText(item.getName());
                imIVItem.setImageResource(item.getDrawableIcon());
            }
        }

        public void selectItem(Object tag) {
            mSoundManager.playSound(mSoundManager.sButtonUse);
            String id = "";
            wmSelectedItem = 0;
            for (int i = String.valueOf(tag).toCharArray().length - 1; ; i--) {
                if (String.valueOf(tag).toCharArray()[i] != '_')
                    id += String.valueOf(tag).toCharArray()[i];
                else break;
            }
            if (id.length() == 2) {
                String first, second;
                first = String.valueOf(id.toCharArray()[1]);
                second = String.valueOf(id.toCharArray()[0]);
                id = first + second;
            }
            wmSelectedItem = Integer.parseInt(id);
            ImageButton itemView = (ImageButton) wmRL.findViewWithTag(tag);
            refreshBag();
            setPreviewBagItemInfo();
            itemView.setImageResource(R.drawable.dr_inventory_item_slot_selected);
        }

        private void refreshBag() {
            Item[] array = wmDIManager.getAvailableDisposableItemsArray();
            for (int i = 0; i < array.length; i++) {
                String sTag = "inventory_item_slot_" + String.valueOf(i + 1);
                Object tag = (Object) sTag;
                Item item = array[i];
                ImageButton itemView = (ImageButton) wmRL.findViewWithTag(tag);
                itemView.setBackgroundResource(item.getDrawableIcon());
            }
            setPreviewBagItemInfo();
        }

        private void clearAllIconsHover() {
            for (int i = 0; i < 14; i++) { //hardcode
                String sTag = "inventory_item_slot_" + String.valueOf(i + 1);
                Object tag = (Object) sTag;
                ImageButton itemView = (ImageButton) wmRL.findViewWithTag(tag);
                itemView.setImageResource(0);
            }
        }
    }

}