package com.example.mygame22048.domain

import android.util.Log
import android.widget.Toast
import kotlin.random.Random

class AppRepository private constructor(){
    // object in singelton
    companion object {
        private lateinit var instance: AppRepository
        fun getInstance(): AppRepository {
            if (!(::instance.isInitialized)) instance = AppRepository()
            return instance
        }
    }
    // yurishlar yigindisini  hisoblaydigan maydon
    private var sum =0L

    // yutazish oynasi
    private var win = false
    //matrix
    private var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    private var addElementAmount = 2

    init {
        addNewElement()
        addNewElement()
    }
    // add element random side
    private fun addNewElement() {
        val empty = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) empty.add(Pair(i, j))
            }
        }

        if (empty.isEmpty()) return
        val randomIndex = Random.nextInt(0, empty.size)
        val findPairByRandomIndex = empty[randomIndex]
        matrix[findPairByRandomIndex.first][findPairByRandomIndex.second] = addElementAmount
    }

    // olish matrixni
    fun getMatrix() : Array<Array<Int>> = matrix
    // shared objecti
    private val myPref = MySharedPref.getInstance()


    // create basic matrix
    private fun createBasicMatrix() = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    // move to left
    fun moveLeft(){
        val newMatrix = createBasicMatrix()
        var index = 0
        var isAdded = false


        for (i in matrix.indices) {
            index = 0
            isAdded = false

            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][0] == 0) {
                    newMatrix[i][0] = matrix[i][j]
                    continue
                }

                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *=2
                    sum += newMatrix[i][index]
                    isAdded = true
                } else {
                    newMatrix[i][++index] = matrix[i][j]
                    isAdded= false
                }
            }
        }

        matrix = newMatrix

        addNewElement()
        if (isFinish()){
           win = true
        }
    }

    // move to right
    fun moveRight() {
        val newMatrix = createBasicMatrix()
        var index = 3
        var isAdded = false


        for (i in matrix.indices) {
            index = 3
            isAdded = false

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][3] == 0) {
                    newMatrix[i][3] = matrix[i][j]
                    continue
                }

                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *=2
                    sum+=newMatrix[i][index]

                    isAdded = true
                } else {
                    newMatrix[i][--index] = matrix[i][j]
                    isAdded= false
                }
            }
        }

        matrix = newMatrix
        addNewElement()
        if (isFinish()){
            win = true
        }

    }

    // move to Up
    fun moveUp() {
        val newMatrix = createBasicMatrix()
        var index = 0
        var isAdded = false

        for (j in matrix[0].indices) {
            index = 0
            isAdded = false

            for (i in matrix.indices) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[0][j] == 0) {
                    newMatrix[0][j] = matrix[i][j]
                    continue
                }

                if (newMatrix[index][j] == matrix[i][j] && !isAdded) {
                    newMatrix[index][j] *= 2
                    sum+=newMatrix[index][j]
                    isAdded = true
                } else {
                    newMatrix[++index][j] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix

        addNewElement()
        if (isFinish()){
            win = true
        }

    }

    // move to Down
    fun moveDown() {
        val newMatrix = createBasicMatrix()
        var index = 3
        var isAdded = false

        for (j in matrix[0].indices) {
            index = 3
            isAdded = false

            for (i in matrix.indices.reversed()) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[3][j] == 0) {
                    newMatrix[3][j] = matrix[i][j]
                    continue
                }

                if (newMatrix[index][j] == matrix[i][j] && !isAdded) {
                    newMatrix[index][j] *= 2
                    sum+=newMatrix[index][j]
                    isAdded = true
                } else {
                    newMatrix[--index][j] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()
        if (isFinish()){
          win = true
        }
    }

    // new Game
    fun newGame (){
        for (i in 0..<matrix.size) {
          matrix = createBasicMatrix()
        }
        addNewElement()
        addNewElement()
        sum = 0;
    }

    // Continue game
    fun continueGame() {
        val savedGame = myPref.getSavedGame()

        if (savedGame.size >= 12) {
            var j = 0 // Initialize j outside the loop
            for (i in 0 until savedGame.size) {
                if (i < 4) {
                    matrix[0][i] = savedGame[j]
                } else if (i < 8) {
                    matrix[1][i - 4] = savedGame[j]
                } else if (i < 12) {
                    matrix[2][i - 8] = savedGame[j]
                } else {
                    matrix[3][i - 12] = savedGame[j]
                }
                j++
            }
        } else {
            Log.e("continueGame", "Insufficient elements in saved game")
        }
        sum = myPref.getMax()
    }


    // check finish game
    private fun isFinish(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) {
                    return false
                }
                if ((j + 1 < matrix[i].size && matrix[i][j] == matrix[i][j + 1]) ||
                    (i + 1 < matrix.size && matrix[i][j] == matrix[i + 1][j])) {
                    return false
                }
            }
        }
        return true
    }

    // sent count to model
    fun getCount():Long{
        return sum
    }

    // sent finish about game to view model
    fun getBoolean():Boolean{

        Log.d("finish","$win")
        return win
    }
    fun ignoreBoolean(boolean: Boolean){
        win=false
        sum =0
    }

}