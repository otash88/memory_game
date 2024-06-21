package com.example.mygame22048.ui.screen.start

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mygame22048.R
import com.example.mygame22048.databinding.ScreenStartBinding
import com.example.mygame22048.domain.MySharedPref


class StartScreen:Fragment(R.layout.screen_start) {
    private val navController by lazy { findNavController() }
    private val binding:ScreenStartBinding by viewBinding(ScreenStartBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.playBtn.setOnClickListener {
            navController.navigate(R.id.action_startScreen_to_homeScreen,bundleOf(Pair("VAL1",true)))
        }
      /*  if(MySharedPref.getInstance().getButtonVis()){
            binding.resumeBtn.visibility = View.GONE
        }
        else{
            binding.resumeBtn.visibility=View.VISIBLE
        }*/
        binding.resumeBtn.setOnClickListener {
            //navController.navigate(R.id.action_startScreen_to_homeScreen, bundleOf(Pair("VAL",true)))
        }
        binding.buyBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.theopen.android"))
            startActivity(intent)
        }
        binding.exitBnt.setOnClickListener {
            activity?.finish()
        }
    }
}