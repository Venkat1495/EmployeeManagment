package com.example.paylog

import androidx.lifecycle.ViewModel

class NewEmployeeViewModel : ViewModel() {

     var firstName: String = "";
     var lastName: String = "";
     var hourlyPay: String = "";
     var employeeType: Int = 0;
    override fun onCleared() {
        super.onCleared()
    }


}