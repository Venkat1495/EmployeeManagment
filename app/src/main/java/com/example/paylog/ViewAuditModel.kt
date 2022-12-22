package com.example.paylog

import android.os.Parcel
import android.os.Parcelable

data class ViewAuditModel(var logId : Int) : Parcelable {
    var name: String = ""
    var employeeId: Int = -1
    var startTime: String = ""
    var endTime: String = ""
    var date: String = ""
    var toBePaid: String = ""
    var hourlyPay: Int = -1
    var paymentStatus : Boolean = false

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        name = parcel.readString().toString()
        employeeId = parcel.readInt()
        startTime = parcel.readString().toString()
        endTime = parcel.readString().toString()
        date = parcel.readString().toString()
        toBePaid = parcel.readString().toString()
        paymentStatus = parcel.readByte() != 0.toByte()
        hourlyPay = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(logId)
        parcel.writeString(name)
        parcel.writeInt(employeeId)
        parcel.writeString(startTime)
        parcel.writeString(endTime)
        parcel.writeString(date)
        parcel.writeString(toBePaid)
        parcel.writeByte(if (paymentStatus) 1 else 0)
        parcel.writeInt(hourlyPay)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ViewAuditModel> {
        override fun createFromParcel(parcel: Parcel): ViewAuditModel {
            return ViewAuditModel(parcel)
        }

        override fun newArray(size: Int): Array<ViewAuditModel?> {
            return arrayOfNulls(size)
        }
    }
}