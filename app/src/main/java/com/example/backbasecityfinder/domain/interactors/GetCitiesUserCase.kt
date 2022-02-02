package com.example.backbasecityfinder.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.backbasecityfinder.common.Resource
import com.example.backbasecityfinder.common.UseCase
import com.example.backbasecityfinder.common.UseCaseScope
import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.repository.CityRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Suppress("TooGenericExceptionCaught")
class GetCitiesUserCase(private val cityRepository: CityRepository) :
    UseCase<LiveData<Resource<List<City>>>>,
    UseCaseScope {
    override fun execute(): LiveData<Resource<List<City>>> {
        val result = MutableLiveData<Resource<List<City>>>()
        result.postValue(Resource.Loading())
        launch {
            try {
                val cities = cityRepository.getCities()
                result.postValue(Resource.Success(cities.sortedBy { it.name }))
            } catch (exception: Exception) {
                result.postValue(Resource.Error(exception))
            }
        }
        return result
    }

    override fun cancel() {
        coroutineContext.cancel()
    }
}
