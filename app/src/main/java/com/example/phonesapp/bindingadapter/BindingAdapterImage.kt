package com.example.phonesapp.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapterImage{
        @BindingAdapter(value = ["imageUrl"])
        @JvmStatic
        fun loadImage(view: ImageView, url: String) {
                Glide.with(view)
                    .load(url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(view)
        }
}