package com.example.paylog

import androidx.lifecycle.ViewModel

class WorkEntryViewModel : ViewModel() {

    var empId: Int = -1
    var date: String = ""
    var startTime: String = ""
    var endTime: String = ""
    var paymentStatus: Boolean = false

    override fun onCleared() {
        super.onCleared()
    }

}