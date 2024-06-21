package com.example.mygame22048.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mygame22048.R
import com.example.mygame22048.databinding.DialogBinding

class MyDialog:DialogFragment(R.layout.dialog) {
    private val binding by viewBinding(DialogBinding::bind)
    var newGameListen :((Int)->Unit)?= null
    var exitListen :((Int)->Unit)?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.exitBtn.setOnClickListener {
            exitListen?.invoke(1)
            dismiss()

        }

        binding.newGameBtn.setOnClickListener {
            newGameListen?.invoke(1)
            dismiss()

        }

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.dialog, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }



}