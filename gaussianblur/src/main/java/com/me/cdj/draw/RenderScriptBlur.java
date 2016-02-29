package com.me.cdj.draw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

/**
 * author: Lance
 * time: 2015/12/31 15:17
 * e-mail: lance.cao@anarry.com
 */
public class RenderScriptBlur {

    private static final String TAG = RenderScriptBlur.class.getSimpleName();

    public RenderScriptBlur() {
    }

    @SuppressLint("NewApi")
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap, Context context){
        Bitmap bitmap;

        if (canReuseInBitmap){
            bitmap = sentBitmap;
        }else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (bitmap.getConfig() == Bitmap.Config.RGB_565) {
            bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }

        try {
            RenderScript renderScript = RenderScript.create(context);
            Allocation input = Allocation.createFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(renderScript, input.getType());
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            blur.setRadius(radius);
            blur.setInput(input);
            blur.forEach(output);
            output.copyTo(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
        }

        return bitmap;
    }
}
