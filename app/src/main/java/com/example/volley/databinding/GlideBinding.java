package com.example.volley.databinding;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.volley.R;

public class GlideBinding {
    @BindingAdapter("imageResourceUrl")
    public static void setImageResource(ImageView view, String imageUrl){
        Context context = view.getContext();
        RequestOptions option = new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);
        Glide.with(context)
                .setDefaultRequestOptions(option)
                .load(imageUrl)
                .into(view);
    }
}
