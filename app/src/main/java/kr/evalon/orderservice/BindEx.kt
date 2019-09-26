package kr.evalon.orderservice

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

object BindEx {

    interface GlideListener{
        fun create(request:RequestBuilder<Drawable>):RequestBuilder<Drawable>
    }

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

    @BindingAdapter("android:activated")
    @JvmStatic
    fun activated(v:View, activated:Boolean){
        v.isActivated = activated
    }

    @BindingAdapter("customImgBuilder")
    @JvmStatic
    fun imgCustom(v:ImageView,createFunc:GlideListener?){
        createFunc ?: return
        createFunc.create(Glide.with(v).asDrawable()).into(v)
    }

    @BindingAdapter("android:tint")
    @JvmStatic
    fun tintNullable(v:ImageView, value:Int?){
        value ?: return
        v.imageTintList = ColorStateList.valueOf(value)
    }
}