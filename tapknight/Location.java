package com.wellographics.tapknight;

import android.content.Context;

public class Location {
    private int lIdentifier;
    private int lResIDBackground, lResIDArea, lResIDBottomBar;
    private Context lContext;

    public Location(Context context, int identifier) {
        lIdentifier = identifier;
        lContext = context;
        defineIDs();
    }

    private void defineIDs() {
        setBackgroundResID();
        setAreaResID();
        setBottomBarResID();
    }

    public int getBackground() {return lResIDBackground;}
    public int getArea() {return lResIDArea;}
    public int getBottomBar() {return lResIDBottomBar;}

    public String getName() {
        return lContext.getString(lContext.getResources().getIdentifier(
                "st_location_name_" + String.valueOf(lIdentifier), "string", lContext.getPackageName()
        ));
    }

    public int getIdentifier() {return lIdentifier;}

    private void setBackgroundResID() {
        lResIDBackground = lContext.getResources().getIdentifier(
                "dr_battle_background_" + String.valueOf(lIdentifier), "drawable", lContext.getPackageName()
        );
    }

    private void setAreaResID() {
        lResIDArea = lContext.getResources().getIdentifier(
                "dr_battle_area_" + String.valueOf(lIdentifier), "drawable", lContext.getPackageName()
        );
    }

    private void setBottomBarResID() {
        lResIDBottomBar = lContext.getResources().getIdentifier(
                "dr_battle_actionbar_bottom_background_" + String.valueOf(lIdentifier), "drawable", lContext.getPackageName()
        );
    }
}

