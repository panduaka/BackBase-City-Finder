package com.example.backbasecityfinder.ui

import androidx.lifecycle.ViewModel
import com.example.backbasecityfinder.domain.interactors.GetCitiesUserCase

class MainViewModel(
    private val getCitiesUserCase: GetCitiesUserCase
): ViewModel() {

}