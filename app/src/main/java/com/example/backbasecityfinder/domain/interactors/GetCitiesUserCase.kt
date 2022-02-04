package com.example.backbasecityfinder.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.backbasecityfinder.common.Resource
import com.example.backbasecityfinder.common.UseCase
import com.example.backbasecityfinder.common.UseCaseScope
import com.example.backbasecityfinder.data.mapper.mapToUIModel
import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.model.CityDomainModel
import com.example.backbasecityfinder.domain.repository.CityRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Suppress("TooGenericExceptionCaught")
class GetCitiesUserCase(private val cityRepository: CityRepository) :
    UseCase<LiveData<Resource<List<CityDomainModel>>>>,
    UseCaseScope {
    override fun execute(): LiveData<Resource<List<CityDomainModel>>> {
        val result = MutableLiveData<Resource<List<CityDomainModel>>>()
        result.postValue(Resource.Loading())
        launch {
            try {
                val cities = cityRepository.getCities()
                val citiesDomain: List<CityDomainModel> = cities.sortedBy { it.name }
                    .sortedBy { it.country }.map { it.mapToUIModel() }
                result.postValue(Resource.Success(citiesDomain))
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
