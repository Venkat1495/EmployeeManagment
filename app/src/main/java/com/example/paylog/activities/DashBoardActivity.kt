package com.example.paylog.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import com.example.paylog.DBStorage
import com.example.paylog.DashBoardEmpModel
import com.example.paylog.DashBoardModel
import com.example.paylog.R

class DashBoardActivity : AppCompatActivity() {

    private lateinit var tableLayout : TableLayout;
    private lateinit var dashBoardModel: DashBoardModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        setConnections()
        getData()
        loadData()
    }

    private fun setConnections() {
        tableLayout = findViewById(R.id.tableLayout)
    }

    private fun getData(){
        var dbStore = DBStorage(this)
        dashBoardModel = dbStore.getDashBoardData()
    }

    private fun loadData() {
        addTotal()
        println("Dashboard Size" + dashBoardModel.employeeAmounts.size)
        for ( emp in dashBoardModel.employeeAmounts) {
            addEmpdata(emp)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun addTotal(){
        var v = addTableRow(dashBoardModel.totalModel)
        v.setBackgroundColor(R.color.black)
        tableLayout.addView(v)
    }

    private fun addEmpdata(emp: DashBoardEmpModel) {
        tableLayout.addView(addTableRow(emp))
    }

    private fun addTableRow(dashBoardEmpModel: DashBoardEmpModel): View {
        var tableRow = TableRow(this)
        var params  = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT)
        tableRow.layoutParams = params
        var textViewName = getTextView(dashBoardEmpModel.name)
        var textViewAmount = getTextView(dashBoardEmpModel.totalAmount.toString())
        tableRow.addView(textViewName)
        tableRow.addView(textViewAmount)
        tableRow.setPadding(40)
        return tableRow
    }

    @SuppressLint("ResourceAsColor")
    private fun getTextView(text: String) : TextView {
        var textView = TextView(this)
        var params  = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1f)
        textView.text = text
        println(text)
        textView.setTextColor(R.color.black)
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.layoutParams = params
        return textView
    }
}