package com.example.rickandmorty.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.data.local.Character
import com.example.rickandmorty.data.remote.ApiClient
import com.example.rickandmorty.data.remote.ApiHelper
import com.example.rickandmorty.data.remote.ApiService
import com.example.rickandmorty.databinding.HomeFragmentBinding
import com.example.rickandmorty.utils.Status.*

class HomeFragment : Fragment(), HomeAdapter.OnCharacterClickListener {


    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var adapter: HomeAdapter
    private var characterList: ArrayList<Character> = ArrayList()
    private var currentPage: Int = 1
    private var totalAvailablePages: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        setupViewModel()
        setupUI()
        doInitialization()
        return binding.root
    }

    private fun setupViewModel() {
        viewModelFactory =
            HomeViewModelFactory(ApiHelper(ApiClient.getRetrofit().create(ApiService::class.java)))
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun setupUI() {
        binding.rvCharacters.setHasFixedSize(true)
        adapter = HomeAdapter(this)
        adapter.setData(characterList)
        binding.rvCharacters.adapter = adapter
    }

    private fun doInitialization() {

        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvCharacters.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        getCharactersList()
                    }
                }
            }
        })
        getCharactersList()
    }

    private fun getCharactersList() {
        viewModel.getCharacterList(currentPage).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        resource.data?.let { characters ->
                            with(binding){
                                rvCharacters.visibility = View.VISIBLE
                                progressMore.visibility = View.GONE
                                shimmerFrameLayout.stopShimmer()
                                shimmerFrameLayout.visibility = View.GONE
                            }
                            totalAvailablePages = characters.info.pages
                            val oldCount: Int = characterList.size
                            characterList.addAll(characters.results)
                            adapter.notifyItemRangeInserted(oldCount, characterList.size)
                        }
                    }
                    ERROR -> {
                        with(binding){
                            rvCharacters.visibility = View.VISIBLE
                            progressMore.visibility = View.GONE
                            shimmerFrameLayout.visibility = View.GONE
                            shimmerFrameLayout.stopShimmer()
                        }
                        Toast.makeText(
                            requireActivity().applicationContext,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LOADING -> {
                        if (currentPage == 1) {
                            binding.shimmerFrameLayout.startShimmer()
                        } else {
                            binding.progressMore.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }


    override fun onCharacterClick(character: Character, position: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(character)
        )
    }


}