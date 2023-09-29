package com.example.cs388lab3.ui.planets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the planets Fragment"
    }
    val text: LiveData<String> = _text
}