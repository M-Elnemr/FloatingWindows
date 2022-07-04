package com.elnemr.floatingwindows.layout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class WindowContentLayout : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)
    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    )

    private lateinit var listener: ((active: Boolean) -> Unit)

    fun setWindowListener(listener: (active: Boolean) -> Unit) {
        this.listener = listener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_OUTSIDE -> listener.invoke(false)
            MotionEvent.ACTION_DOWN -> listener.invoke(true)
        }
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_OUTSIDE -> listener.invoke(false)
            MotionEvent.ACTION_DOWN -> listener.invoke(true)
        }
        return super.onInterceptTouchEvent(ev)
    }

}