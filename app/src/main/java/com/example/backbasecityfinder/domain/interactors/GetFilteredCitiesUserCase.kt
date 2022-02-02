package com.example.backbasecityfinder.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.backbasecityfinder.common.Resource
import com.example.backbasecityfinder.common.UseCaseScope
import com.example.backbasecityfinder.common.UseCaseWithParameter
import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.repository.CityRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Suppress("TooGenericExceptionCaught")
class GetFilteredCitiesUserCase(private val cityRepository: CityRepository) :
    UseCaseWithParameter<String, LiveData<Resource<List<City>>>>,
    UseCaseScope {
    override fun execute(parameter: String): LiveData<Resource<List<City>>> {
        val result = MutableLiveData<Resource<List<City>>>()
        result.postValue(Resource.Loading())
        launch {
            try {
                val cities =
                    filterList(searchText = parameter, cities = cityRepository.getFetchedCities())
                result.postValue(Resource.Success(cities))
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
        return cities.filter {
            it.name.startsWith(searchText)
        }.sortedBy {
            it.name
        }
    }
}
