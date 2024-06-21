package com.example.mygame22048.utels

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import com.example.mygame22048.data.SideEnum
import kotlin.math.abs

class MyTouchListener(private val context: Context) : View.OnTouchListener {
    private val gestureDetector = GestureDetector(context, MyGestureDetector())
    private var actionSideEnumListener : ((SideEnum) -> Unit)?= null

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            startEvent: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (startEvent == null || e2 == null) return false

            if (abs(startEvent.x -e2.x) <= 100 && abs(startEvent.y - e2.y) <= 100) return true

            // horizontal
            if (abs(startEvent.x - e2.x) > abs(startEvent.y - e2.y)) {
                if (startEvent.x > e2.x) actionSideEnumListener?.invoke(SideEnum.LEFT)
                else actionSideEnumListener?.invoke(SideEnum.RIGHT)
            } else {
                if (startEvent.y > e2.y) actionSideEnumListener?.invoke(SideEnum.UP)
                else actionSideEnumListener?.invoke(SideEnum.DOWN)
            }
            return super.onFling(startEvent, e2, velocityX, velocityY)
        }
    }


    fun setActionSideEnumListener(block: (SideEnum) -> Unit) {
        this.actionSideEnumListener = block
    }
}