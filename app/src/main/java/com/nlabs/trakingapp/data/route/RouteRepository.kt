package com.nlabs.trakingapp.data.route

import androidx.lifecycle.LiveData

class RouteRepository (private val routeDao: RouteDao) {

    val getRoutesData: LiveData<List<Route>> = routeDao.getRoutesData()

    suspend fun addRoute(route: Route){
        routeDao.upsertRoute(route)
    }

    suspend fun deleteRoute(route: Route){
        routeDao.deleteRouteInstance(route)
    }

    suspend fun deleteAllData(){
        routeDao.deleteAllData()
    }
}