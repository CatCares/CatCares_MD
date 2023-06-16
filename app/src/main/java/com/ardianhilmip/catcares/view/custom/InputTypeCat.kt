package com.ardianhilmip.catcares.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.ardianhilmip.catcares.R

class InputTypeCat : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do nothing.
                            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        context.apply {
            compoundDrawablePadding = 20
            setTextColor(ContextCompat.getColor(this, R.color.black))
            hint = context.getString(R.string.input_type_cat)
            textSize = 15f
            setHintTextColor(ContextCompat.getColor(this, R.color.gray_800))
            background = getDrawable(R.drawable.form_input)
        }
    }
}