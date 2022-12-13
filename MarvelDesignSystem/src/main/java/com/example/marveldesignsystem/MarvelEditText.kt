package com.example.marveldesignsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.marveldesignsystem.databinding.MarvelEditTextBinding

class MarvelEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    private val binding = MarvelEditTextBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        setupLayout(attrs)
    }

    private fun setupLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            context.obtainStyledAttributes(
                attributeSet,
                R.styleable.MarvelEditText
            ).apply {

                this.getColor(R.styleable.MarvelEditText_boxBackgroundColor, 0).let {
                    binding.etSearch.boxBackgroundColor = it
                }

                this.getDrawable(R.styleable.MarvelEditText_startIconDrawable)?.let {
                    binding.etSearch.startIconDrawable = it
                }

                this.getInt(R.styleable.MarvelEditText_imeOptions, 0).let {
                    binding.etInputSearch.imeOptions = it
                }

                this.getInt(R.styleable.MarvelEditText_inputType, 0).let {
                    binding.etInputSearch.inputType = it
                }

                this.getInt(R.styleable.MarvelEditText_maxLines, 0).let {
                    binding.etInputSearch.maxLines = it
                }

                this.recycle()
            }
        }
    }

    fun setNullableInput(): Boolean {
        if (!binding.etInputSearch.text.isNullOrEmpty()) {
            return true
        }
        return false
    }

    fun getEditTextInput() : EditText {
        return binding.etInputSearch
    }
}