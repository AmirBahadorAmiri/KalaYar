package com.amirbahadoramiri.kalayar.presentation.ui.fragments.home;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

public class ImageBindingAdapter {

    @BindingAdapter("imageRes")
    public static void setImageRes(ImageView view, int drawable) {
        if (drawable != 0) {
            view.setImageResource(drawable);
        } else {
            view.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }

    @BindingAdapter("app:tint")
    public static void setTint(ImageView view, int color) {
        view.setImageTintList(ColorStateList.valueOf(color));
    }

    @BindingAdapter("app:srcCompat")
    public static void setSrcCompat(AppCompatImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("android:backgroundTint")
    public static void setBackgroundTint(View view, int color) {
        view.setBackgroundTintList(ColorStateList.valueOf(color));
    }
}


