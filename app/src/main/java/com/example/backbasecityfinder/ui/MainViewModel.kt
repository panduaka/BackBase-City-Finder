package com.example.backbasecityfinder.ui

import androidx.lifecycle.*
import com.example.backbasecityfinder.common.Resource
import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.interactors.GetCitiesUserCase
import com.example.backbasecityfinder.domain.interactors.GetFilteredCitiesUserCase
import com.example.backbasecityfinder.domain.model.CityDomainModel

class MainViewModel(
    private val getCitiesUserCase: GetCitiesUserCase,
    private val getFilteredCitiesUserCase: GetFilteredCitiesUserCase
) : ViewModel() {

    internal val cityCodeRequest = MutableLiveData<Unit>()
    internal val cityCodeFilterRequest = MutableLiveData<String>()

    internal val cityCode: LiveData<List<CityDomainModel>> get() = _cityCode
    internal val cityCodeFilter: LiveData<List<CityDomainModel>> get() = _cityFilterCode
    internal val cityCodeLoading: LiveData<Unit> get() = _cityCodeLoading

    private val _cityCode = MediatorLiveData<List<CityDomainModel>>()
    private val _cityFilterCode = MediatorLiveData<List<CityDomainModel>>()
    private val _cityCodeLoading = MediatorLiveData<Unit>()

    private val cityCodeResult: LiveData<Resource<List<CityDomainModel>>> =
        Transformations.switchMap(cityCodeRequest) {
            getCitiesUserCase.execute()
        }

    private val cityFilterCodeResult: LiveData<Resource<List<CityDomainModel>>> =
        Transformations.switchMap(cityCodeFilterRequest) {
            getFilteredCitiesUserCase.execute(it)
        }

    init {
        _cityCode.addSource(cityCodeResult) {
            if (it is Resource.Success) {
                _cityCode.postValue(it.data!!)
            } else if (it is Resource.Loading) {
                _cityCodeLoading.postValue(Unit)
            }
        }
        _cityFilterCode.addSource(cityFilterCodeResult) {
            if (it is Resource.Success) {
                _cityFilterCode.postValue(it.data!!)
            } else if (it is Resource.Loading) {
                _cityCodeLoading.postValue(Unit)
            }
        }
    }

    fun getCityList() {
        getCitiesUserCase.execute()
    }
}
