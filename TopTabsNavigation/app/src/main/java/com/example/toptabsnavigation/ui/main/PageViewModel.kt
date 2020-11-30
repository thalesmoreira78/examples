package com.example.toptabsnavigation.ui.main


import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Button
import androidx.lifecycle.*
import com.example.toptabsnavigation.classes.ButtonClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class PageViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val _index = MutableLiveData<Int>()
    // private val fileName = "data.json"

    fun setIndex(index: Int) {
        _index.value = index
    }

    private val _buttonsList = MutableLiveData<List<ButtonClass>>()

    fun setButtonsList(buttons: String) {
        val listButtonType = object : TypeToken<List<ButtonClass>>() {}.type
        var allButtons: List<ButtonClass> = Gson().fromJson(buttons, listButtonType)
        _buttonsList.value = allButtons
    }

    fun getButtonsList(): List<ButtonClass>? {
        return _buttonsList.value
    }

    var buttonsList: LiveData<List<ButtonClass>> = Transformations.map(_buttonsList) {
        loadButtons(it)
    }

    private fun loadButtons(buttons: List<ButtonClass>): List<ButtonClass> {
        /*
        var jsonFileString = ""
        context!!.openFileInput(fileName).use { stream ->
            val text = stream.bufferedReader().use {
                it.readText()
            }
            jsonFileString = text
            Log.d("PageViewModel", "loadButtons, file: $text")
        }
        */

        // val listButtonType = object : TypeToken<List<ButtonClass>>() {}.type
        // var allButtons: List<ButtonClass> = Gson().fromJson(jsonFileString, listButtonType)
        var listButtons: List<ButtonClass>
        listButtons = if (_index.value === 1) {
            buttons
        } else {
            val favorites: MutableList<ButtonClass> = ArrayList()
            buttons.forEachIndexed { _, button ->
                if (button.favorite){
                    favorites.add(button)
                }
            }
            favorites
        }
        return listButtons
    }
}