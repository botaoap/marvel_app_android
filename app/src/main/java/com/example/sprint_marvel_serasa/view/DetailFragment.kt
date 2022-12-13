package com.example.sprint_marvel_serasa.view

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sprint_marvel_serasa.R
import com.example.sprint_marvel_serasa.databinding.DeatilFragmentBinding
import com.example.sprint_marvel_serasa.model.Results
import com.example.sprint_marvel_serasa.utils.showFragment
import com.example.sprint_marvel_serasa.view_model.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.deatil_fragment) {

    companion object {
        fun newInstance(id: Int) : DetailFragment {
            return DetailFragment().apply {
                Bundle().let {
                    it.putInt("id_to_detail", id)
                    this.arguments = it
                }
            }
        }
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DeatilFragmentBinding

    private var observerCharacterDetail = Observer<List<Results>> {
        chargeDetailOfHero(it[0])
        binding.incDeatailHero.cvDetailHero.setupBiographyVisible(VISIBLE)
        binding.incDeatailHero.cvDetailHero.setupGradientVisible(VISIBLE)
//        binding.incDeatailHero.tvBiography.visibility = VISIBLE
//        binding.incDeatailHero.ivGradientHeroDetail.visibility = VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadComponents(view)
        setupArrowBack()
    }

    private fun loadComponents(view: View) {
        binding = DeatilFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.characterDetail.observe(viewLifecycleOwner, observerCharacterDetail)
        viewModel.fetchCharacterId(id=arguments?.getInt("id_to_detail")!!)
    }

    private fun chargeDetailOfHero(list: Results){
        binding.incDeatailHero.cvDetailHero.setupNameHero(list.name)
//        binding.incDeatailHero.tvNameHeroDetail.text = list.name
        if (list.description.isNullOrEmpty()) {
            binding.incDeatailHero.cvDetailHero.setupDescription(getString(R.string.havent_description))
//            binding.incDeatailHero.tvDescription.text = getString(R.string.havent_description)
        }else {
            binding.incDeatailHero.cvDetailHero.setupDescription(list.description)
//            binding.incDeatailHero.tvDescription.text = list.description
        }
        binding.incDeatailHero.cvDetailHero.setupImageHero(list.thumbnail.mergeImage())
//        Glide.with(this)
//            .load(list.thumbnail.mergeImage())
//            .into(binding.incDeatailHero.ivHeroDetail)
    }

    private fun setupArrowBack() {
//        binding.ivArrowBack.setOnClickListener {
//            requireActivity().showFragment(MainFragment.newInstance())
//            requireActivity().supportFragmentManager.popBackStack()
//        }
        binding.dsMarvelToolBarDetail.getupArrowBack().setOnClickListener {
            requireActivity().showFragment(MainFragment.newInstance())
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}