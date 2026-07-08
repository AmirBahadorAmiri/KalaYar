package com.amirbahadoramiri.kalayar.presentation.ui.fragments.home;

import android.widget.ImageView;
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
}
