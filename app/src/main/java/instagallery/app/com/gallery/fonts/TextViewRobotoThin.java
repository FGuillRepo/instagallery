package instagallery.app.com.gallery.fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import instagallery.app.com.gallery.R;



/*
* Custom TextView for RobotoBold Text
*
* */

public class TextViewRobotoThin extends TextView {

    public TextViewRobotoThin(Context context) {
        super(context);
    }

    public TextViewRobotoThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TextViewRobotoThin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewRobotoThin);
        String customFont = a.getString(R.styleable.TextViewRobotoThin_RobotoThin);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/"+asset);
        } catch (Exception e) {
            return false;
        }

        setTypeface(typeface);
        return true;
    }
}