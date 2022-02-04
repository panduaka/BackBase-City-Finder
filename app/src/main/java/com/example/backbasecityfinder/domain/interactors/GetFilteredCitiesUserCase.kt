package com.example.backbasecityfinder.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.backbasecityfinder.common.Resource
import com.example.backbasecityfinder.common.UseCaseScope
import com.example.backbasecityfinder.common.UseCaseWithParameter
import com.example.backbasecityfinder.data.mapper.mapToUIModel
import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.model.CityDomainModel
import com.example.backbasecityfinder.domain.repository.CityRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

@Suppress("TooGenericExceptionCaught")
class GetFilteredCitiesUserCase(private val cityRepository: CityRepository) :
    UseCaseWithParameter<String, LiveData<Resource<List<CityDomainModel>>>>,
    UseCaseScope {

    override fun execute(parameter: String): LiveData<Resource<List<CityDomainModel>>> {
        val result = MutableLiveData<Resource<List<CityDomainModel>>>()
        result.postValue(Resource.Loading())
        launch {
            try {
                val cities =
                    filterList(
                        searchText = parameter,
                        cities = cityRepository.getFetchedCities().sortedBy { it.name }
                            .sortedBy { it.country })
                result.postValue(Resource.Success(cities.map { it.mapToUIModel() }))
            } catch (exception: Exception) {
                result.postValue(Resource.Error(exception))
            }
        }
        return result
    }

    override fun cancel() {
        coroutineContext.cancel()
    }

    private fun filterList(searchText: String, cities: List<City>): List<City> {
        return when (searchText.length) {
            0 -> cities
            else -> {
                cities.filter {
                    it.name.startsWith(searchText, ignoreCase = true)
                }.sortedBy {
                    it.name
                }.sortedBy {
                    it.country
                }
            }
        }
    }
}
