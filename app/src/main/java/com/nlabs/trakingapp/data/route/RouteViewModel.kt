package com.nlabs.trakingapp.data.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel(application: Application): AndroidViewModel(application) {

    val getData: LiveData<List<Route>>
    private val repository: RouteRepository

    init {
        val routeDao = RouteDatabase.getDatabase(application).dao()
        repository = RouteRepository(routeDao)
        getData = repository.getRoutesData
    }

    fun upsertRoute(route: Route){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRoute(route)
        }
    }

    fun deleteRoute(route: Route){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteRoute(route)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllData()
        }
    }

}