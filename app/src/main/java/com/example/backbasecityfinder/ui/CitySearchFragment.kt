package com.example.backbasecityfinder.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.backbasecityfinder.R
import com.example.backbasecityfinder.data.remote.dto.Coord
import com.example.backbasecityfinder.databinding.CitySearchFragmentBinding


class CitySearchFragment: Fragment(
    R.layout.city_search_fragment
) {
    private lateinit var binding: CitySearchFragmentBinding
    private val sharedMainViewModel: MainViewModel by activityViewModels()
    private var adapter: CityAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CitySearchFragmentBinding.bind(view)
        adapter = CityAdapter() {
            val mapIntent = createLocationIntent(it)
            activity?.packageManager?.let { packageManager ->
                mapIntent.resolveActivity(packageManager)?.let {
                    startActivity(mapIntent)
                }
            }
        }
        binding.listView.adapter = adapter

        binding.searchField.addTextChangedListener {
            sharedMainViewModel.cityCodeFilterRequest.postValue(it.toString())
        }
        sharedMainViewModel.cityCodeRequest.postValue(Unit)

        sharedMainViewModel.run {
            cityCode.observe(viewLifecycleOwner) {
                binding.searchField.visibility = View.VISIBLE
                binding.progressLoader.visibility = View.GONE
                adapter?.citiesList = it
            }

            cityCodeFilter.observe(viewLifecycleOwner) {
                binding.progressLoader.visibility = View.GONE
                adapter?.citiesList = it
            }

            cityCodeLoading.observe(viewLifecycleOwner) {
                binding.progressLoader.visibility = View.VISIBLE
            }
        }
    }

    private fun createLocationIntent(coord: Coord): Intent {
        val gmmIntentUri = Uri.parse("geo:${coord.lat},${coord.lon}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        return mapIntent
    }
}