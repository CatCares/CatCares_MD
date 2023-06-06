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

class InputAddress: AppCompatEditText {

    private lateinit var iconFormInput: Drawable

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
        iconFormInput = context.getDrawable(R.drawable.ic_address) as Drawable
        showIconFormInput()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                showIconFormInput()
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    private fun showIconFormInput() {
        setButtonDrawable(startOfTheText = iconFormInput)
    }

    private fun setButtonDrawable(
        startOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        context.apply {
            compoundDrawablePadding = 20
            setTextColor(ContextCompat.getColor(this, R.color.black))
            hint = context.getString(R.string.input_address)
            textSize = 15f
            setHintTextColor(ContextCompat.getColor(this, R.color.gray_800))
            background = getDrawable(R.drawable.form_input)
        }
    }
}