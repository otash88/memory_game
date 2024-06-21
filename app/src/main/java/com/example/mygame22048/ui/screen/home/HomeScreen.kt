package com.example.mygame22048.ui.screen.home

import android.annotation.SuppressLint

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mygame22048.R
import com.example.mygame22048.data.SideEnum
import com.example.mygame22048.databinding.ScreenHomeBinding
import com.example.mygame22048.domain.MySharedPref
import com.example.mygame22048.ui.MyDialog
import com.example.mygame22048.ui.RefreshDialog
import com.example.mygame22048.utels.MyBackgroundUtil
import com.example.mygame22048.utels.MyTouchListener

class HomeScreen:Fragment(R.layout.screen_home) {
    private val binding :ScreenHomeBinding by viewBinding(ScreenHomeBinding::bind)

    private var views = ArrayList<AppCompatTextView>(16)
    private val  viewModel:HomeScreenVIewModel by viewModels ()
    private val navController by lazy { findNavController() }

    private var myPref = MySharedPref.getInstance()
    private var count =0L

    private var myDialog =MyDialog()
    private var refreshDialog = RefreshDialog()



    private val dialogObserver = Observer<Boolean> { showDialog ->
        if (showDialog) {
            viewModel.ignoreBoolean(false)
            myDialog.show(childFragmentManager, null)
            myDialog.exitListen ={
                navController.popBackStack()
            }

        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.highScore.text=myPref.getMax2().toString()
        Log.d("BBB","${myPref.getMax2()}")
        myPref.saveButtonVis(true)
        val isResume = arguments?.getBoolean("VAL") ?:false
        val newGame = arguments?.getBoolean("VAL1")?:false

        Log.d("AAA","$isResume")
        if (isResume) {
            viewModel.continueGame()
            binding.count.text = myPref.getMax().toString()
        }
        if(newGame){
            viewModel.continueGame()
            binding.count.text = myPref.getMax().toString()
        }

        loadViews()
        loadData()
        attachTouchListener()

        myDialog.newGameListen={
            val count1 = myPref.getMax2()
            if(count1<count)
                myPref.saveMax2(count)
            viewModel.newGame()
            loadViews()
            loadData()
        }
        myDialog.exitListen={
            navController.popBackStack()
        }

        var b =false
        viewModel.listen.observe(this,dialogObserver)
      /*  if (b){

            binding.lost.visibility = View.VISIBLE
        }*/



        viewModel.sendCount.observe(viewLifecycleOwner){
            count  = it
            binding.count.text = it.toString()
            if(myPref.getMax2()<it){
                binding.highScore.text = it.toString()
            }

        }

        binding.refresh.setOnClickListener {

            refreshDialog.show(childFragmentManager, null)

            refreshDialog.newGameListen={
                val count1 = myPref.getMax2()
                if(count1<count)
                    myPref.saveMax2(count)
                viewModel.newGame()
                loadViews()
                loadData()
            }
            refreshDialog.exitListen={

            }


        }

        binding.home.setOnClickListener {
            navController.popBackStack()
        }



    }
    private fun loadViews() {
        for (i in 0 until binding.mainView.childCount) {
            val liner = binding.mainView[i] as LinearLayoutCompat
            for (j in 0 until liner.childCount) {
                views.add(liner[j] as AppCompatTextView)
            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun attachTouchListener() {
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setActionSideEnumListener {

            when(it) {
                SideEnum.DOWN -> viewModel.moveDown()
                SideEnum.RIGHT -> viewModel.moveRight()
                SideEnum.UP ->  viewModel.moveUp()
                SideEnum.LEFT -> viewModel.moveLeft()
            }
            loadData()
        }
        binding.mainView.setOnTouchListener(myTouchListener)
    }
    private fun loadData() {
        viewModel.loadData()
        viewModel.sendMatrix.observe(viewLifecycleOwner){
            for (i in it.indices) {
                for (j in it[i].indices) {
                    if (it[i][j] == 0) views[i*4 + j].text = ""
                    else views[i*4 + j].text = "${it[i][j]}"

                    views[i*4 + j].setBackgroundResource(MyBackgroundUtil.backgroundByAmount(it[i][j]))
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()

        myPref.saveMax(count.toLong())
        val count1 =myPref.getMax2()
        if(count1<count){
            myPref.saveMax2(count)
        }


        val result = views.joinToString("#") {
            if (it.text.toString() == "") "0" else it.text.toString()
        }
        Log.d("TTT","Save $result")
        myPref.saveLastGame(result)
        myPref.saveButtonVis(false)

    }







}