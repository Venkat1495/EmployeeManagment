package com.example.paylog

import android.os.Parcelable
import androidx.lifecycle.ViewModel

class AuditViewModel: ViewModel() {

    private var logList = ArrayList<ViewAuditModel>()

    override fun onCleared() {
        super.onCleared()
    }

    fun getAllList() : ArrayList<ViewAuditModel> {
        return this.logList
    }

}