package com.example.practicefornike1.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practicefornike1.common.NikeFragment
import com.example.practicefornike1.databinding.FragmentUserBinding

class UserFragment:NikeFragment() {

    var binding:FragmentUserBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUserBinding.inflate(inflater,container,false)
        return binding!!.root
    }
}