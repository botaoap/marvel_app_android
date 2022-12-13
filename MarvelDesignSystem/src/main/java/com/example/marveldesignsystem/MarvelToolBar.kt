package com.example.marveldesignsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.marveldesignsystem.databinding.MarvelToolBarBinding

class MarvelToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs){

    private var binding = MarvelToolBarBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?){
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.MarvelToolBar
            )

            attributes.getDrawable(R.styleable.MarvelToolBar_image_logo)?.let{
                binding.ivLogo.setImageDrawable(it)
            }

            attributes.getDrawable(R.styleable.MarvelToolBar_image_arrow_back)?.let {
                binding.ivArrowBack.setImageDrawable(it)
            }

            attributes.recycle()
        }
    }

    fun getupArrowBack() : ImageView{
        return  binding.ivArrowBack
    }

}