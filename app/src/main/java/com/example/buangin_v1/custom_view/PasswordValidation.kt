package com.example.buangin_v1.custom_view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.Selection
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.buangin_v1.R

class PasswordValidation: AppCompatEditText, View.OnTouchListener {
    private var isPasswordVisible = true

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

    }
    private fun init() {
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = if (s.length < 8){
                    "Password must be more or at least 8 characters"
                }else{
                    null
                }
            }
            override fun afterTextChanged(s: Editable) {

                error = if (s.length < 8){
                    "Password must be more or at least 8 characters"
                }else{
                    null
                }
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
//        if (event.action == MotionEvent.ACTION_UP) {
//            val drawableRight = compoundDrawablesRelative[2]
//            if (drawableRight != null && event.rawX >= (right - drawableRight.bounds.width())) {
//                togglePasswordVisibility()
//                return true
//            }
//        }
        return false
    }


//    private fun togglePasswordVisibility() {
//        isPasswordVisible = !isPasswordVisible
//        val drawable = if (isPasswordVisible) {
//            ContextCompat.getDrawable(context, R.drawable.hide_eye)
//        } else {
//            ContextCompat.getDrawable(context, R.drawable.remove_eye)
//        }
//        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
//
//        inputType = if (isPasswordVisible) {
//            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//        } else {
//            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//        }
//        text?.let { setSelection(it.length) }
//    }
}
