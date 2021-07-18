package com.example.practicefornike1.data

import androidx.annotation.StringRes

data class EmptyState(val mustShow:Boolean,
@StringRes val messageResId:Int=0
,val mustShowCallToaActionBtn:Boolean=false)
