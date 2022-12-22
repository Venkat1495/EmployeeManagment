package com.example.paylog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.paylog.DBStorage
import com.example.paylog.R
import com.example.paylog.SettlePaymentViewModel
import com.example.paylog.ViewAuditModel
import com.example.paylog.constants.*
import org.w3c.dom.Text

class SettlePaymentActivity : AppCompatActivity() {

    private lateinit var dbStorage: DBStorage;
    private lateinit var name: TextView;
    private lateinit var date: TextView;
    private lateinit var time: TextView;
    private lateinit var tobePaid: TextView;
    private lateinit var backBtn : Button;
    private lateinit var settleBtn: Button;

    private val settlePaymentViewModel : SettlePaymentViewModel by lazy {
        ViewModelProviders.of(this)[SettlePaymentViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settle_payment)
        var bundle: Bundle = intent.getBundleExtra(SETTLE_PAY_ARGS) as Bundle
        var empId = bundle.get(EMP_ID)
        println("Settlement $empId")
        setConnections()
        setCallbacks()
        connectDatabase()
        loadEmployeeData(empId as Int)
    }

    private fun connectDatabase() {
        this.dbStorage = DBStorage(this)
    }

    private fun setCallbacks() {
        this.backBtn.setOnClickListener{
            finish()
        }

        this.settleBtn.setOnClickListener{
            var result = this.dbStorage.settleUp(settlePaymentViewModel.workLogId)

            if(result != 0) {
                Toast.makeText(this, PAY_SETTLEMENT_SUCCESSFUL,Toast.LENGTH_SHORT)
            }else {
                Toast.makeText(this, PAY_SETTLEMENT_FAILED,Toast.LENGTH_SHORT)
            }
            finish()
        }
    }

    private fun setConnections() {
        name = findViewById(R.id.name_value)
        tobePaid = findViewById(R.id.amount_value)
        date = findViewById(R.id.date_value)
        time = findViewById(R.id.time_value)
        backBtn = findViewById(R.id.btn_back)
        settleBtn = findViewById(R.id.btn_pay)
    }

    private fun loadEmployeeData(workLogId: Int) {
        var workLog:ViewAuditModel = dbStorage.getWorkLogDetailsById(workLogId)
        settlePaymentViewModel.date = workLog.date
        settlePaymentViewModel.empId = workLog.employeeId
        settlePaymentViewModel.workLogId = workLog.logId
        settlePaymentViewModel.startTime = workLog.startTime
        settlePaymentViewModel.endTime = workLog.endTime
        settlePaymentViewModel.tobePaid = workLog.toBePaid
        settlePaymentViewModel.employeeName = workLog.name
        this.updateViews()
    }

    private fun updateViews(){
        date.text = settlePaymentViewModel.date
        time.text = settlePaymentViewModel.startTime + " - " + settlePaymentViewModel.endTime
        name.text = settlePaymentViewModel.employeeName
        tobePaid.text = settlePaymentViewModel.tobePaid
    }
}