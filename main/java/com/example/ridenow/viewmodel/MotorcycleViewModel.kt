package com.example.ridenow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ridenow.model.Motorcycle

class MotorcycleViewModel : ViewModel() {
    private val _items = MutableLiveData<MutableList<Motorcycle>>().apply { value = mutableListOf() }
    //val items: LiveData<List<Motorcycle>> get() = _items
    val items: MutableLiveData<MutableList<Motorcycle>> get() = _items

    init {
        _items.value = mutableListOf(
            Motorcycle(1, "Yamaha", "FZ1", 2006, 150),
            Motorcycle(2, "Yamaha", "MT-07", 2014, 75)
        )
    }

        fun addMotorcycle(motorcycle: Motorcycle) {
            val updatedList = _items.value ?: mutableListOf()
            updatedList.add(motorcycle)
            _items.value = updatedList
        }

        fun updateMotorcycle(updatedMotorcycle: Motorcycle) {
            val updatedList = _items.value?.map { if (it.id == updatedMotorcycle.id) updatedMotorcycle else it }?.toMutableList()
            _items.value = updatedList
        }

        fun deleteMotorcycle(motorcycleId: Int) {
            val updatedList = _items.value?.filter { it.id != motorcycleId }?.toMutableList()
            _items.value = updatedList
        }

    fun getMotorcycleById(motorcycleId: Int): Motorcycle? {
        return _items.value?.find { it.id == motorcycleId }
    }



    private fun MutableLiveData<MutableList<Motorcycle>>.notifyObserver() {
        this.value = this.value
    }
}

 /*
package com.example.ridenow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ridenow.MotorcycleAPI.MotorcycleApi
import com.example.ridenow.model.Motorcycle
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException

class MotorcycleViewModel : ViewModel() {
    private val _items = MutableLiveData<MutableList<Motorcycle>>().apply { value = mutableListOf() }
    val items: MutableLiveData<MutableList<Motorcycle>> get() = _items

    init {
        // Initial data (optional, for testing purposes)
        _items.value = mutableListOf(
            Motorcycle(1, "Yamaha", "FZ1", 2006, 150),
            Motorcycle(2, "Yamaha", "MT-07", 2014, 75)
        )
    }

    // This function will update the list without passing null to _items.value
    fun addMotorcycle(motorcycle: Motorcycle) {
        val updatedList = _items.value ?: mutableListOf() // Ensure non-null list
        updatedList.add(motorcycle)
        _items.value = updatedList
    }

    // Update motorcycle by replacing it with the updated object
    fun updateMotorcycle(updatedMotorcycle: Motorcycle) {
        val updatedList = _items.value?.map { if (it.id == updatedMotorcycle.id) updatedMotorcycle else it }
            ?.toMutableList() ?: mutableListOf() // Ensure non-null list

        _items.value = updatedList
    }

    // Delete motorcycle based on its ID
    fun deleteMotorcycle(motorcycleId: Int) {
        val updatedList = _items.value?.filter { it.id != motorcycleId }
            ?.toMutableList() ?: mutableListOf() // Ensure non-null list

        _items.value = updatedList
    }

    fun getMotorcycleById(motorcycleId: Int): Motorcycle? {
        return _items.value?.find { it.id == motorcycleId }
    }

    private fun MutableLiveData<MutableList<Motorcycle>>.notifyObserver() {
        this.value = this.value
    }
}*/



