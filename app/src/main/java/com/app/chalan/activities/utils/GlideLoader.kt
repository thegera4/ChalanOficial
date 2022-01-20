package com.app.chalan.activities.utils

import android.content.Context
import android.widget.ImageView
import com.app.chalan.R
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(image: Any, imageView: ImageView){
        try {
            //Load the user image in the imageview
            Glide
                .with(context)
                .load(image)//URI of the image
                .centerCrop()//Scalable type of the image
                .placeholder(R.drawable.ic_image_24)//default placeholder image to load
                .into(imageView) //view in which the image will be loaded
        } catch (e: IOException){
            e.printStackTrace()
        }
    }

    /*fun loadProductPicture(image: Any, imageView: ImageView){
        try {
            //Load the user image in the imageview
            Glide
                .with(context)
                .load(image)//URI of the image
                .centerCrop()//Scalable type of the image
                .into(imageView) //view in which the image will be loaded
        } catch (e: IOException){
            e.printStackTrace()
        }
    }*/


}