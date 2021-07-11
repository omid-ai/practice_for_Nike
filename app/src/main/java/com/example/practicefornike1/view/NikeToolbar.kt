package com.example.practicefornike1.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.practicefornike1.R

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var onBackBtnPressedCallback:OnClickListener?=null
    set(value) {
        field=value
        val backBtn=findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener(onBackBtnPressedCallback)
    }

    init {
        inflate(context, R.layout.view_toolbar,this)
        if (attrs!=null){
            val toolBar=context.obtainStyledAttributes(attrs,R.styleable.NikeToolbar)
            val title=toolBar.getString(R.styleable.NikeToolbar_nt_title)
            val titleToolbar=findViewById<TextView>(R.id.titleToolbarTv)
            if (title!=null && title.isNotEmpty()){
                titleToolbar.text=title
            }
            toolBar.recycle()
        }
    }
}