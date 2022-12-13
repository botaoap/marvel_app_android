package com.example.marveldesignsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.marveldesignsystem.databinding.MarvelDetailHeroBinding

class MarvelDetailHero @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    private var binding = MarvelDetailHeroBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        setupLayout(attrs)
    }

    private fun setupLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            context.obtainStyledAttributes(
                attributeSet,
                R.styleable.MarvelDetailHero
            ).apply {

                this.getDrawable(R.styleable.MarvelDetailHero_image_gradient)?.let {
                    binding.ivGradientHeroDetail.setImageDrawable(it)
                }

                this.getInt(R.styleable.MarvelDetailHero_visibility_gradient, VISIBLE).let {
                    binding.ivGradientHeroDetail.visibility = it
                }

                this.getString(R.styleable.MarvelDetailHero_name_biography)?.let {
                    binding.tvBiography.text = it
                }

                this.getInt(R.styleable.MarvelDetailHero_visibility_biography, VISIBLE).let {
                    binding.tvBiography.visibility = it
                }

                setBackgroundResource(R.color.dark_black)

                this.recycle()
            }
        }
    }

    fun setupImageHero(url: String) {
        Glide.with(context)
            .load(url)
            .into(binding.ivHeroDetail)
    }

    fun  setupNameHero(name: String) {
        binding.tvNameHeroDetail.text = name
    }

    fun setupDescription(description: String) {
        binding.tvDescription.text = description
    }

    fun setupBiographyVisible(visibility: Int) {
        binding.tvBiography.visibility = visibility
    }

    fun setupGradientVisible(visibility: Int) {
        binding.ivGradientHeroDetail.visibility = visibility
    }
}
