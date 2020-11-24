package com.example.toptabsnavigation.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import android.widget.Button

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    private val favorites = MutableLiveData<Button>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun filterList(list: List<Button>) {
        fa
    }
}