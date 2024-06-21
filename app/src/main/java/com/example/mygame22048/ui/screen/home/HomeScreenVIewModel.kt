package com.example.mygame22048.ui.screen.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygame22048.domain.AppRepository

class HomeScreenVIewModel:ViewModel() {


    var sendMatrix = MutableLiveData<Array<Array<Int>>> ()
    var sendCount = MutableLiveData<Long>()
    var listen =MutableLiveData<Boolean>()
    private var repository = AppRepository.getInstance()




    fun loadData() {

            sendMatrix.value = repository.getMatrix()
            sendCount.value = repository.getCount()
            Log.d("finish","${repository.getBoolean()}")
            listen.value = repository.getBoolean()

    }
    fun newGame(){
        repository.newGame()
    }
    fun continueGame(){
        repository.continueGame()
    }
    fun moveRight(){
        repository.moveRight()
    }
    fun moveLeft(){
        repository.moveLeft()
    }
    fun moveUp(){
        repository.moveUp()
    }
    fun moveDown(){
        repository.moveDown()
    }
    fun ignoreBoolean(boolean: Boolean){
        repository.ignoreBoolean(boolean)
    }


}