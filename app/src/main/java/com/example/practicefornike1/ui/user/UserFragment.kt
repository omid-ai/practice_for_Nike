package com.example.practicefornike1.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practicefornike1.R
import com.example.practicefornike1.common.NikeFragment
import com.example.practicefornike1.data.TokenContainer
import com.example.practicefornike1.databinding.FragmentUserBinding
import com.example.practicefornike1.ui.auth.AuthenticationActivity
import com.example.practicefornike1.ui.favorites.FavoriteListActivity
import com.example.practicefornike1.ui.order.OrderHistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment:NikeFragment() {

    lateinit var binding:FragmentUserBinding
    val viewModel:UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteListBtn.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteListActivity::class.java))
        }

        binding.showOrderHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(),OrderHistoryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (viewModel.isSignedIn){
            binding.signInAndSignOutBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_sign_out,0)
            binding.signInAndSignOutBtn.text=getString(R.string.signOut)
            binding.emailTv.text=viewModel.userName
            binding.signInAndSignOutBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthState()
            }
        }else{
            binding.signInAndSignOutBtn.text=getString(R.string.signIn)
            binding.signInAndSignOutBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_sign_in,0)
            binding.emailTv.text=getString(R.string.guest_user)
            binding.signInAndSignOutBtn.setOnClickListener {
                startActivity(Intent(requireContext(),AuthenticationActivity::class.java))
            }
        }
    }
}