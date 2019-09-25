package kr.evalon.orderservice

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindEx {

    @BindingAdapter("thumbnail")
    @JvmStatic
    fun imgUrl(v:ImageView, thumbnail:String?){
        if(thumbnail == null){
            Glide.with(v)
                .load(ColorDrawable(Color.BLACK))
                .into(v)
        }
        else
        Glide.with(v)
            .load(thumbnail)
            .into(v)
    }

    @BindingAdapter("android:tint")
    @JvmStatic
    fun tintNullable(v:ImageView, value:Int?){
        value ?: return
        v.imageTintList = ColorStateList.valueOf(value)
    }
}