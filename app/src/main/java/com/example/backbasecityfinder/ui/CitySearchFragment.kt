package com.example.backbasecityfinder.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.backbasecityfinder.R
import com.example.backbasecityfinder.databinding.CitySearchFragmentBinding


class CitySearchFragment : Fragment(
    R.layout.city_search_fragment
) {
    private lateinit var binding: CitySearchFragmentBinding
    private val sharedMainViewModel: MainViewModel by sharedViewModel()
    private var adapter: CityAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CitySearchFragmentBinding.bind(view)
        adapter = CityAdapter { lat, lon ->
            val mapIntent = createLocationIntent(latitude = lat, longitude = lon)
            activity?.packageManager?.let { packageManager ->
                mapIntent.resolveActivity(packageManager)?.let {
                    startActivity(mapIntent)
                }
            }
        }
        binding.listView.adapter = adapter

        initListeners()

        sharedMainViewModel.cityCodeRequest.postValue(Unit)

        sharedMainViewModel.run {
            cityCode.observe(viewLifecycleOwner) {
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

    private fun initListeners() {
        binding.viewSearchBar.tvSearch.addTextChangedListener {
            sharedMainViewModel.cityCodeFilterRequest.postValue(it.toString())
            val icon = if (it.toString().isNotEmpty()) R.drawable.ic_close else R.drawable.ic_search
            binding.viewSearchBar.imgSearch.setImageResource(icon)
        }

        binding.viewSearchBar.imgSearch.setOnClickListener {
            binding.viewSearchBar.tvSearch.setText("")
        }
    }

    private fun createLocationIntent(latitude: Double, longitude:Double): Intent {
        val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        return mapIntent
    }
}