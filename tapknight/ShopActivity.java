package com.wellographics.tapknight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ShopActivity extends Activity {

    //ArrayList<View> sItemsArray;
    GridView sGridView;
    Hero sHero;
    ActionBarManager sABManager;
    InventoryManager sIManager;
    final String sBoldFontPath = "fonts/ft_main_bold.ttf",sRegularFontPath = "fonts/ft_main_regular.ttf";
    Typeface sCFBold, sCFRegular;
    SoundManager sSoundManager;
    ItemAdapter sIAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        sHero = new Hero(this);
        sCFBold = Typeface.createFromAsset(getAssets(), sBoldFontPath);
        sCFRegular = Typeface.createFromAsset(getAssets(), sRegularFontPath);
        sGridView = (GridView) findViewById(R.id.layout_shop_grid);
        sABManager = new ActionBarManager(this);
        sIManager = new InventoryManager(this);
        sSoundManager = new SoundManager(3, getAssets(), getLocalClassName());
        refreshAdapter();

    }

    public void onBackButtonClick(View v) {
        this.finish();
    }

    public void refreshAdapter() {
        sHero = new Hero(this);
        sIAdapter = new ItemAdapter(this, sHero.getAvailableThingsArray());
        sGridView.setAdapter(sIAdapter);
    }

    public void onCloseClick(View v) {sABManager.closeInventory();}
    public void onHeroClick(View v) {sABManager.onHeroClick();}
    public void onItemClick(View v) {sABManager.selectItem(v.getTag());}
    public void onSellButtonClick(View v) {sABManager.removeButton();}
    public void onEquipButtonClick(View v) {sABManager.equipButton(v);}
    public void onEquipViewClick(View v) {sABManager.onEquipViewClick(v.getTag());}
    public void onUnequipButtonClick(View v) {sABManager.onUnequipClick();}
    public void onMoneyClick(View v) {}
    public void onEnergyClick(View v) {}
    public void onHeaderButtonClick(View v) {sABManager.onHeaderButtonClick(v);}
    public void onCharButtonClick(View v) {sABManager.onCharButtonClick();}
    public void onBuyItem(View v) {
        sSoundManager.playSound(sSoundManager.sButtonUse);
        sIAdapter.onBuyItem(v);
    }

    public class ItemAdapter extends BaseAdapter {

        private Context mContext;
        public Integer[] mThumbIds;

        public ItemAdapter(Context context, Integer[] itemIds) {
            mContext = context;
            mThumbIds = itemIds;
        }

        @Override
        public int getCount() {return mThumbIds.length;}

        @Override
        public Object getItem(int position) {return mThumbIds[position];}

        @Override
        public long getItemId(int position) {return 0;}

        public void onBuyItem(View v) {
            sSoundManager.playSound(sSoundManager.sButtonUse);
            int id = (int) v.getTag();
            Item item = new Item(id, getApplicationContext());
            if (sHero.getMoney() < item.getValue())
                Toast.makeText(ShopActivity.this, getString(R.string.st_er_notmoney), Toast.LENGTH_SHORT).show();
            else {
                if (item.getCategoryId() != 11 && sHero.equipThingOnHero(item.getId())) {
                    sSoundManager.playSound(sSoundManager.sCoin);
                    sABManager.moneyAnimation();
                    sHero.setMoney(-item.getValue(), true);
                    sHero.saveInfo();
                    sABManager.refreshActionBarInfo();
                    refreshAdapter();
                }
                else if (item.getCategoryId() == 11) {
                    sSoundManager.playSound(sSoundManager.sCoin);
                    sABManager.moneyAnimation();
                    sHero.setMoney(-item.getValue(), true);
                    sHero.saveInfo();
                    sABManager.refreshActionBarInfo();
                    sHero.addThingInABag(item.getId());
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View itemView = getLayoutInflater().inflate(R.layout.shop_item, null);
            TextView
                    name = (TextView) itemView.findViewById(R.id.shop_item_name),
                    category = (TextView) itemView.findViewById(R.id.shop_item_category),
                    description = (TextView) itemView.findViewById(R.id.shop_item_description);
            Button valueButton = (Button) itemView.findViewById(R.id.shop_item_value_button);
            ImageView image = (ImageView) itemView.findViewById(R.id.shop_item_icon);
            Item item = new Item(mThumbIds[position], mContext);

            name.setTypeface(sCFBold);
            category.setTypeface(sCFBold);
            description.setTypeface(sCFRegular);

            valueButton.setTag(item.getId());

            if (sHero.getMoney() < item.getValue()) name.setTextColor(getResources().getColor(R.color.cl_gray_name_unavailable));
            else name.setTextColor(getResources().getColor(R.color.cl_orange_name_available));

            valueButton.setTypeface(sCFBold);
            if (sHero.getMoney() < item.getValue()) valueButton.setBackgroundResource(R.drawable.dr_shop_item_value_background_unavailable);
            else valueButton.setBackgroundResource(R.drawable.dr_shop_item_value_background_available);

            category.setAllCaps(true);
            if (item.getId() >= 0 || item.getId() < 25) {
                name.setText(item.getName());
                category.setText(item.getCategoryName());
                description.setText(item.getDescription());
                valueButton.setText(String.valueOf(item.getValue()));
                image.setImageResource(item.getDrawableIcon());
            }
            return itemView;
        }
    }
}