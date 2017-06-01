package com.wellographics.tapknight;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class WelloFontManager extends TextView {

    private Activity wfmContext;
    private Typeface wtvFontEnemyName, wtvFontMainRegular, wtvFontMainBold;

    public WelloFontManager(Activity context) {
        super(context);
        wfmContext = context;
        wtvFontEnemyName = Typeface.createFromAsset(context.getAssets(), "fonts/ft_enemy_name.ttf");
        wtvFontMainRegular = Typeface.createFromAsset(context.getAssets(), "fonts/ft_main_regular.ttf");
        wtvFontMainBold = Typeface.createFromAsset(context.getAssets(), "fonts/ft_main_bold.ttf");
    }

    private Typeface getFont(int fontId) {
        Typeface font = null;
        switch (fontId) {
            case 1: font = wtvFontEnemyName; break;
            case 2: font = wtvFontMainRegular; break;
            case 3: font = wtvFontMainBold;
        }
        return font;
    }

    public TextView getTextView(int resId, int fontId, boolean allCaps) {
        TextView textView = (TextView) wfmContext.findViewById(resId);
        Typeface font = getFont(fontId);
        if (font != null) textView.setTypeface(font);
        textView.setAllCaps(allCaps);
        return textView;
    }

    public Button getButton(int resId, int fontId, boolean allCaps) {
        Button button = (Button) wfmContext.findViewById(resId);
        Typeface font = getFont(fontId);
        if (font != null)
            button.
                    setTypeface(font);
        button.setAllCaps(allCaps);
        return button;
    }

}
