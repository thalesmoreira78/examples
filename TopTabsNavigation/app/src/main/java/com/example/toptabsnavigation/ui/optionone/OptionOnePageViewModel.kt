package com.example.toptabsnavigation.ui.optionone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OptionOnePageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Option One section: $it"
    }

    val activity: LiveData<Class<*>> = Transformations.map(_index) {
        if (it == 1) {
            OptionOneNewActivity::class.java
        } else {
            OptionOneNew2Activity::class.java
        }
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}