package com.example.rickandmorty.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.data.local.Character
import com.example.rickandmorty.data.local.Episode
import com.example.rickandmorty.databinding.DetailFragmentBinding
import com.squareup.picasso.Picasso
import com.example.rickandmorty.utils.Status.*

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: DetailViewModel
    private var url: String = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DetailFragmentBinding.inflate(layoutInflater)
        setupViewModel()
        setupObservers()

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getCharacterDetail(args.character.id).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        resource.data?.let { character ->
                            getCharacterDetail(character)
                            setupEpisodeObserver()
                        }
                    }
                    ERROR -> {
                        binding.progressDetail.visibility = View.GONE
                        Toast.makeText(
                            requireActivity().applicationContext,
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    LOADING -> {
                        binding.progressDetail.visibility = View.VISIBLE
                    }
                }
            }
        })

    }

    private fun setupEpisodeObserver(){
        viewModel.getCharacterEpisodes(url).observe(viewLifecycleOwner, {
            it?.let { resourceEpisode ->
                when (resourceEpisode.status) {
                    SUCCESS -> {
                        resourceEpisode.data?.let { episodeCharacter ->
                            getCharacterEpisode(episodeCharacter)
                            binding.progressDetail.visibility = View.GONE
                            binding.cardViewDetail.visibility = View.VISIBLE
                            binding.btnUrl.visibility = View.VISIBLE
                        }
                    }
                    ERROR -> {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    LOADING -> {
                    }
                }
            }
        })
    }

    private fun getCharacterDetail(character: Character) {
        with(binding) {
            Picasso.get().load(character.image).into(ivCharacter)
            txtTitle.text = character.name
            "${character.status} - ${character.species} ".also { txtStatusSpecie.text = it }
            txtLocation.text = character.location.name
            txtCreated.text = character.created
            txtOrigin.text = character.origin.name
            url = character.episode[0].toString()

            btnUrl.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(character.url)
                startActivity(intent)
            }
        }
    }

    private fun getCharacterEpisode(episode: Episode) {
        with(binding) {
            txtNameEpisode.text = episode.name
            txtAirDate.text = episode.air_date
        }
    }


}