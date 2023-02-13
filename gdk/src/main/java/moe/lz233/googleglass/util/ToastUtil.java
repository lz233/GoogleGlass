package moe.lz233.googleglass.util;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static TextView textView;
    private static Toast toast = null;

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void show(Context context, CharSequence message, int duration) {
        Toast mToast = getToast(context);
        mToast.setView(getView(context, message));
        mToast.setDuration(duration);
        mToast.show();
    }

    private static View getView(Context mContext, CharSequence message) {
        if (textView == null) {
            textView = new TextView(mContext);
            textView.setTextSize(40.0f);
            textView.setTextColor(0xff000000);
            textView.setGravity(17);
            textView.setBackgroundColor(-1);
            textView.setWidth(180);
            textView.setHeight(getHeight(String.valueOf(message)) + 50);
            //chanTextView.setHeight(mContext.getResources().getConfiguration().locale.equals(Locale.SIMPLIFIED_CHINESE) ? 40 : 50);
        }
        textView.setText(message);
        return textView;
    }

    private static int getHeight(String text) {
        TextPaint myTextPaint = new TextPaint();
        myTextPaint.setAntiAlias(true);
        myTextPaint.setTextSize(40.0f);
        int width = 180;
        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
        float spacingMultiplier = 1;
        float spacingAddition = 0;
        boolean includePadding = false;
        StaticLayout myStaticLayout = new StaticLayout(text, myTextPaint, width, alignment, spacingMultiplier, spacingAddition, includePadding);
        return myStaticLayout.getHeight();
    }

    private static Toast getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        return toast;
    }
}
