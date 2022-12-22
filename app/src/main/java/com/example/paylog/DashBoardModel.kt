package com.example.paylog

class DashBoardEmpModel() {
    var totalAmount : Double = 0.0
    var name: String = " "
}

class DashBoardModel() {
    var totalModel : DashBoardEmpModel = DashBoardEmpModel()
    var employeeAmounts: ArrayList<DashBoardEmpModel> = ArrayList<DashBoardEmpModel>()

    init {
        totalModel.name = "Total Amount"
    }
}