package com.example.paylog

import androidx.lifecycle.ViewModel

class SettlePaymentViewModel : ViewModel() {
    var empId: Int = 1
    var startTime: String = ""
    var endTime: String = ""
    var tobePaid: String = ""
    var date: String = ""
    var paymentStatus: Boolean = false
    var workLogId: Int = -1
    var employeeName: String = ""
}