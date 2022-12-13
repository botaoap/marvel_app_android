package com.example.marveldesignsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.marveldesignsystem.databinding.MarvelCardViewBinding

class MarvelCardView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int = 0
) : CardView(context, attrs, defStyleAttrs) {

    private val binding = MarvelCardViewBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            context.obtainStyledAttributes(
                attributeSet,
                R.styleable.MarvelCardView
            ).apply {

                this.getDimension(R.styleable.MarvelCardView_cardCornerRadius, 0f).let {
                    binding.cvHero.radius = it
                }
                this.getInt(R.styleable.MarvelCardView_textAlignment, 0).let {
                    binding.tvNameHero.textAlignment = it
                }

                this.recycle()
            }
        }
    }
    fun setText(text: String) {
        binding.tvNameHero.text = text
    }

    fun setImage(image: String) {
        Glide.with(this)
            .load(image)
            .into(binding.ivHero)
    }
}