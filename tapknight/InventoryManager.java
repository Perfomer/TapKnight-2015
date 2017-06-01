package com.wellographics.tapknight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class InventoryManager {
    RelativeLayout imRLBackground, imRLLayout, imRLBagLayout, imRLEquipLayout, imRLMagicLayout, imRLItemLayout;
    LinearLayout imLLBagButtons;
    ImageView imIVProgressExp, imIVItem;
    ImageButton imIBCharView;
    Button imBClose, imBEquip, imBSell, imBHeaderEquip, imBHeaderBag, imBHeaderMagic, imBUnequip;
    TextView imTVLevel, imTVHealth, imTVEnergy, imTVAttack, imTVStats1, imTVStats2, imTVItemName, imTVItemCategory, imTVItemDescription;
    Activity imActivity;
    GridView imGVSpells;
    Animation imAAppearance, imABlackout, imAFade, imALightening;
    Hero imHero;
    SoundManager imSoundManager;
    Typeface imTFBold, imTFRegular;
    int imSelectedItem;

    public InventoryManager(Activity context){
        imActivity = context;
        imHero = new Hero(imActivity);
        imSoundManager = new SoundManager(3, imActivity.getAssets(), "Inventory");
        imRLBackground = (RelativeLayout) imActivity.findViewById(R.id.inventory_background);
        imRLLayout = (RelativeLayout) imActivity.findViewById(R.id.inventory_body);
        imRLBagLayout = (RelativeLayout) imActivity.findViewById(R.id.inventory_bag_layout);
        imRLEquipLayout = (RelativeLayout) imActivity.findViewById(R.id.inventory_equipment_layout);
        imRLMagicLayout = (RelativeLayout) imActivity.findViewById(R.id.inventory_magic_layout);
        imLLBagButtons = (LinearLayout) imActivity.findViewById(R.id.inventory_bag_buttons);
        imTVLevel = (TextView) imActivity.findViewById(R.id.inventory_level_text);
        imTVHealth = (TextView) imActivity.findViewById(R.id.inventory_character_health);
        imTVEnergy = (TextView) imActivity.findViewById(R.id.inventory_character_energy);
        imTVAttack = (TextView) imActivity.findViewById(R.id.inventory_character_attack);
        imTVStats1 = (TextView) imActivity.findViewById(R.id.inventory_stats_main);
        imTVStats2 = (TextView) imActivity.findViewById(R.id.inventory_stats_second);
        imBClose = (Button) imActivity.findViewById(R.id.inventory_button_close);
        imBEquip = (Button) imActivity.findViewById(R.id.inventory_button_equip);
        imBSell = (Button) imActivity.findViewById(R.id.inventory_button_sell);
        imBHeaderEquip = (Button) imActivity.findViewById(R.id.inventory_header_button_1);
        imBHeaderBag = (Button) imActivity.findViewById(R.id.inventory_header_button_2);
        imBHeaderMagic = (Button) imActivity.findViewById(R.id.inventory_header_button_3);
        imBUnequip = (Button) imActivity.findViewById(R.id.inventory_equip_button_unequip);
        imIVProgressExp = (ImageView) imActivity.findViewById(R.id.inventory_experience);
        imIBCharView = (ImageButton) imActivity.findViewById(R.id.inventory_character);
        imGVSpells = (GridView) imActivity.findViewById(R.id.inventory_spell_list);
        imGVSpells.setAdapter(new SpellAdapter(imActivity));

        imTFBold = Typeface.createFromAsset(imActivity.getAssets(), "fonts/ft_main_bold.ttf");
        imTFRegular = Typeface.createFromAsset(imActivity.getAssets(), "fonts/ft_main_regular.ttf");

        imTVLevel.setTypeface(imTFBold);
        imTVHealth.setTypeface(imTFBold);
        imTVEnergy.setTypeface(imTFBold);
        imTVAttack.setTypeface(imTFBold);
        imTVStats1.setTypeface(imTFBold);
        imTVStats2.setTypeface(imTFRegular);
        imBHeaderBag.setTypeface(imTFRegular);
        imBHeaderMagic.setTypeface(imTFRegular);
        imBHeaderEquip.setTypeface(imTFRegular);
        imBClose.setTypeface(imTFBold);
        imBEquip.setTypeface(imTFRegular);
        imBSell.setTypeface(imTFRegular);
        imBUnequip.setTypeface(imTFRegular);

        imBHeaderBag.setAllCaps(true);
        imBHeaderEquip.setAllCaps(true);
        imBHeaderMagic.setAllCaps(true);
        imTVStats1.setAllCaps(true);
        imTVStats2.setAllCaps(true);

        imABlackout = AnimationUtils.loadAnimation(imActivity, R.anim.an_activity_blackout);
        imAAppearance = AnimationUtils.loadAnimation(imActivity, R.anim.an_activity_appearance);
        imAFade = AnimationUtils.loadAnimation(imActivity, R.anim.an_activity_fade);
        imALightening = AnimationUtils.loadAnimation(imActivity, R.anim.an_activity_lightening);

    }

    public String getStringOfHeroStats(boolean main) {
        if (main)
            return imActivity.getString(R.string.st_de_agility) + ": " + String.valueOf(imHero.getAgility()) + "\n" +
                            imActivity.getString(R.string.st_de_power) + ": " + String.valueOf(imHero.getPower()) + "\n" +
                            imActivity.getString(R.string.st_de_spirit) + ": " + String.valueOf(imHero.getSpirit()) + "\n" +
                            imActivity.getString(R.string.st_de_stamina) + ": " + String.valueOf(imHero.getStamina());
        else return imActivity.getString(R.string.st_de_critical) + ": " + String.valueOf(imHero.getCritical()) + "%\n" +
                        imActivity.getString(R.string.st_de_block) + ": " + String.valueOf(imHero.getBlock()) + "%\n" +
                        imActivity.getString(R.string.st_de_dodge) + ": " + String.valueOf(imHero.getDodge()) + "%\n" +
                        imActivity.getString(R.string.st_de_accuracy) + ": " + String.valueOf(imHero.getAccuracy());

    }

    public void onHeroClick() {}

    private void refreshBag() {
        for (int i = 0; i < imHero.getThingsArray().length; i++) {
            String sTag = "inventory_item_slot_" + String.valueOf(i + 1);
            Object tag = (Object) sTag;
            Item item = new Item(imHero.getThingsArray()[i], imActivity);
            ImageButton itemView = (ImageButton) imRLLayout.findViewWithTag(tag);
            itemView.setBackgroundResource(item.getDrawableIcon());
            Log.d("MsThings", String.valueOf(itemView.getId()) + " " + String.valueOf(imHero.getThingsArray()[i]));
            itemView.setImageResource(0);

            Item item2 = null;
            if (imSelectedItem != 0) item = new Item(imHero.getBagThingId(imSelectedItem - 1), imActivity);

            if (imSelectedItem != 0 && item.getId() != - 1) imBSell.setText(imActivity.getString(R.string.st_de_sell) + " (" + String.valueOf(item.getValue() / 4) + ")");
            else imBSell.setText(imActivity.getString(R.string.st_de_sell));
        }
        setPreviewBagItemInfo();
    }

    private void refreshEquipment() {
        int[] array = imHero.getEquipArray();
        for (int i = 0; i < imHero.MAX_THINGS_IN_EQUIPMENT; i++) {
            ImageButton view = (ImageButton) imRLEquipLayout.findViewWithTag((Object)("equip_item_slot_" + String.valueOf(i + 1)));
            Item item = new Item(array[i], imActivity);
            view.setBackgroundResource(item.getDrawableIcon());
            TextView textview = (TextView) imRLEquipLayout.findViewWithTag((Object)("equip_item_slot_name_" + String.valueOf(i + 1)));
            textview.setTypeface(imTFRegular);
            view.setImageResource(0);
        }
        setPreviewEquipmentItemInfo();
    }

    public void removeBagItem() {
        if (imSelectedItem != 0) {
            int id = imHero.getBagThingId(imSelectedItem - 1);
            Item item = new Item(id, imActivity);
            if (imSelectedItem > 0 && item.getId() != -1) {
                imHero.setMoney(item.getValue() / 4, true);
                imSoundManager.playSound(imSoundManager.sCoin);
                imHero.removeThing(imSelectedItem - 1);
                imHero.saveInfo();
                imSelectedItem = 0;
            }
        }
        refreshInventoryInfo();
    }

    public void unequipItem() {imHero.unequipThing(imHero.getEquipArray()[imSelectedItem - 1] + 1); refreshInventoryInfo();}

    public void equipItem() {
        Item item = new Item(imHero.getBagThingId(imSelectedItem - 1), imActivity);
        imHero.equipThingOnHero(item.getId());
        imHero.removeThing(imHero.findThingInBag(item.getId()));
        refreshInventoryInfo();}

    public void setInventoryPage(int page) {
        imBHeaderEquip.setBackgroundResource(R.drawable.dr_activity_inventory_headerbutton_1_regular);
        imBHeaderBag.setBackgroundResource(R.drawable.dr_activity_inventory_headerbutton_2_regular);
        imBHeaderMagic.setBackgroundResource(R.drawable.dr_activity_inventory_headerbutton_3_regular);

        imRLEquipLayout.setVisibility(View.INVISIBLE);
        imRLBagLayout.setVisibility(View.INVISIBLE);
        imRLMagicLayout.setVisibility(View.INVISIBLE);
        switch (page) {
            case 1:
                imRLEquipLayout.setVisibility(View.VISIBLE);
                imBHeaderEquip.setBackgroundResource(R.drawable.dr_activity_inventory_headerbutton_1_active);
                refreshEquipment();
                break;
            case 2:
                imRLBagLayout.setVisibility(View.VISIBLE);
                imBHeaderBag.setBackgroundResource(R.drawable.dr_activity_inventory_headerbutton_2_active);
                refreshBag();
                break;
            case 3:
                imRLMagicLayout.setVisibility(View.VISIBLE);
                imBHeaderMagic.setBackgroundResource(R.drawable.dr_activity_inventory_headerbutton_3_active);
                break;
        }
    }

    public void selectBagItem(Object tag) {
        imSoundManager.playSound(imSoundManager.sButtonUse);
        String id = "";
        imSelectedItem = 0;
        for (int i = String.valueOf(tag).toCharArray().length - 1;; i--) {
            if (String.valueOf(tag).toCharArray()[i] != '_') id += String.valueOf(tag).toCharArray()[i];
            else break;
        }
        if (id.length() == 2) {
            String first, second;
            first = String.valueOf(id.toCharArray()[1]);
            second = String.valueOf(id.toCharArray()[0]);
            id = first + second;
        }
        imSelectedItem = Integer.parseInt(id);
        ImageButton itemView = (ImageButton) imRLBagLayout.findViewWithTag(tag);
        refreshBag();
        setPreviewBagItemInfo();
        itemView.setImageResource(R.drawable.dr_inventory_item_slot_selected);
    }

    public void selectEquipmentItem(Object tag) {
        imSoundManager.playSound(imSoundManager.sButtonUse);
        String id = "";
        imSelectedItem = 0;
        for (int i = String.valueOf(tag).toCharArray().length - 1;; i--) {
            if (String.valueOf(tag).toCharArray()[i] != '_') id += String.valueOf(tag).toCharArray()[i];
            else break;
        }
        char array[] = null;
        for (int i = 0; i <= id.length() / 2; i++) {
            array = id.toCharArray();
            char memory = array[i];
            array[i] = array[id.length() - 1];
            array[id.length() - 1] = memory;
        }
        id = new String(array);

        imSelectedItem = Integer.parseInt(id);
        ImageButton itemView = (ImageButton) imRLEquipLayout.findViewWithTag(tag);
        refreshEquipment();
        setPreviewEquipmentItemInfo();
        itemView.setImageResource(R.drawable.dr_inventory_item_slot_selected);
    }

    private void setPreviewEquipmentItemInfo() {
        imRLItemLayout = (RelativeLayout) imActivity.findViewById(R.id.inventory_equip_item_layout);
        imIVItem = (ImageView) imActivity.findViewById(R.id.inventory_equip_item_icon);
        imTVItemName = (TextView) imActivity.findViewById(R.id.inventory_equip_item_name);
        imTVItemDescription = (TextView) imActivity.findViewById(R.id.inventory_equip_item_description);
        imTVItemCategory = (TextView) imActivity.findViewById(R.id.inventory_equip_item_category);
        imTVItemDescription.setTypeface(imTFRegular);
        imTVItemCategory.setTypeface(imTFBold);
        imTVItemName.setTypeface(imTFBold);
        if (imSelectedItem != 0 && imHero.getEquipmentThingId(imSelectedItem - 1) != -1) {
            imRLItemLayout.setVisibility(View.VISIBLE);
            imBUnequip.setVisibility(View.VISIBLE);
            Item item = new Item(imHero.getEquipmentThingId(imSelectedItem - 1), imActivity);
            imTVItemCategory.setText(item.getCategoryName());
            imTVItemDescription.setText("[" + item.getDescription() + "]");
            imTVItemDescription.setTextColor(imActivity.getResources().getColor(R.color.cl_orange_name_available));
            imTVItemName.setText(item.getName());
            imIVItem.setImageResource(item.getDrawableIcon());
        }
        else {
            imRLItemLayout.setVisibility(View.INVISIBLE);
            imBUnequip.setVisibility(View.INVISIBLE);
        }
    }

    private void setPreviewBagItemInfo() {
        imRLItemLayout = (RelativeLayout) imActivity.findViewById(R.id.inventory_item_layout);
        imIVItem = (ImageView) imActivity.findViewById(R.id.inventory_item_icon);
        imTVItemName = (TextView) imActivity.findViewById(R.id.inventory_item_name);
        imTVItemCategory = (TextView) imActivity.findViewById(R.id.inventory_item_category);
        imTVItemDescription = (TextView) imActivity.findViewById(R.id.inventory_item_description);
        imTVItemDescription.setTypeface(imTFRegular);
        imTVItemCategory.setTypeface(imTFBold);
        imTVItemName.setTypeface(imTFBold);
        if (imSelectedItem != 0 && imHero.getBagThingId(imSelectedItem - 1) != -1) {
            imRLItemLayout.setVisibility(View.VISIBLE);
            imBUnequip.setVisibility(View.VISIBLE);
            imLLBagButtons.setVisibility(View.VISIBLE);
            Item item = new Item(imHero.getBagThingId(imSelectedItem - 1), imActivity);
            imTVItemCategory.setText(item.getCategoryName());
            imTVItemDescription.setText("[" + item.getDescription() + "]");
            imTVItemDescription.setTextColor(imActivity.getResources().getColor(R.color.cl_orange_name_available));
            imTVItemName.setText(item.getName());
            imIVItem.setImageResource(item.getDrawableIcon());
        }
        else {
            imRLItemLayout.setVisibility(View.INVISIBLE);
            imLLBagButtons.setVisibility(View.INVISIBLE);
            imBUnequip.setVisibility(View.INVISIBLE);
        }
    }

    public void refreshInventoryInfo() {
        refreshBag();
        refreshEquipment();
        imIBCharView.setImageResource(imHero.getClassIcon(3));

        imTVLevel.setText(String.valueOf(imHero.getLevel()));
        imTVHealth.setText(String.valueOf((int)imHero.getHealth(true)));
        imTVEnergy.setText(String.valueOf((int)imHero.getEnergy(true)));
        imTVAttack.setText(String.valueOf((int)imHero.getAttack(true)));
        imTVStats1.setText(getStringOfHeroStats(true));
        imTVStats2.setText(getStringOfHeroStats(false));
        imIVProgressExp.setLayoutParams(new RelativeLayout.LayoutParams((int) (imActivity.getResources().getDimension(R.dimen.general_inventory_char_horizontalprogressbar_width) * imHero.getExperience(false) / imHero.getExperience(true)), imIVProgressExp.getLayoutParams().height));
    }

    public void showInventory(boolean open) {
        if (open) {
            refreshInventoryInfo();
            setInventoryPage(1);
            imSoundManager.playSound(imSoundManager.sButtonUse);
            imRLBackground.setVisibility(View.VISIBLE);
            imRLBackground.startAnimation(imABlackout);
            imRLLayout.startAnimation(imAAppearance);
        }
        else {
            imSelectedItem = 0;
            imSoundManager.playSound(imSoundManager.sCloseActivity);
            imRLBackground.startAnimation(AnimationUtils.loadAnimation(imActivity, R.anim.an_activity_wello_disappear));
            imRLLayout.startAnimation(imAFade);
            imRLBackground.setVisibility(View.GONE);
        }
    }
    public class SpellAdapter extends BaseAdapter {

        private Context mContext;
        public Integer[] mThumbIds;

        public SpellAdapter(Context context) {
            mContext = context;
            mThumbIds = new Integer[] {1, 2, 3, 4, 5};
        }

        @Override
        public int getCount() {return mThumbIds.length;}

        @Override
        public Object getItem(int position) {return mThumbIds[position];}

        @Override
        public long getItemId(int position) {return 0;}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View itemView = imActivity.getLayoutInflater().inflate(R.layout.spell_item, null);
            TextView
                    name = (TextView) itemView.findViewById(R.id.inventory_spell_name),
                    description = (TextView) itemView.findViewById(R.id.inventory_spell_description);
            ImageView image = (ImageView) itemView.findViewById(R.id.inventory_spell_view);
            Spell spell = new Spell(mContext, imHero);

            name.setTypeface(imTFBold);
            description.setTypeface(imTFRegular);

            name.setText(spell.getSpellName(mThumbIds[position]));
            int color = 0;
            switch (imHero.getClassId()) {
                case 1: color = imActivity.getResources().getColor(R.color.cl_class_archer); break;
                case 2: color = imActivity.getResources().getColor(R.color.cl_class_wizard); break;
                case 3: color = imActivity.getResources().getColor(R.color.cl_class_paladin); break;
            }
            name.setTextColor(color);
            description.setText(spell.getSpellDescription(mThumbIds[position]));
            image.setImageResource(spell.getDrawableIcon(mThumbIds[position]));

            return itemView;
        }
    }
}
