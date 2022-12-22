package com.example.paylog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.ViewModelProviders
import com.example.paylog.constants.*
import com.example.paylog.helpers.PaylogHelper
import java.sql.Time
import java.util.Calendar

class DetailsEntryActivity : AppCompatActivity(), OnItemSelectedListener {

    private var employeeNames: ArrayList<String> = ArrayList()
    private var employeesIds: ArrayList<Int> = ArrayList()
    private var hourlyPays: ArrayList<Int> = ArrayList()

    private lateinit var selectemployee: Spinner;
    private lateinit var workDate: EditText;
    private lateinit var workStartTime: EditText;
    private lateinit var workEndTime: EditText;
    private lateinit var saveWorkEntry: Button;
    private var hourlyPay : Int = 0;

    val workEntryViewModel: WorkEntryViewModel by lazy {
        ViewModelProviders.of(this)[WorkEntryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_entry)
        this.empListUpdate()
        this.setControls()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        this.workEntryViewModel.empId = this.employeesIds[p2]
        this.hourlyPay = this.hourlyPays[p2]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun empListUpdate() {
        val context = this
        var db = DBStorage(context)
        var empData = db.readEmpsData(EMPLOYEE_TABLE)

        for (employee in empData) {
            employeeNames.add(employee.empFullName)
            employeesIds.add(employee.empId)
            hourlyPays.add(employee.hPay)
        }
    }

    private fun setControls() {
        this.setSpinner()
        this.setWorkDate()
        this.setWorkStartTime()
        this.setWorkEndTime()
        this.setSaveWorkEntry()
    }

    private fun setWorkDate() {
        this.workDate = this.findViewById(R.id.workDate)
        this.workDate.setOnClickListener{
            this.setDate()
        }
    }

    private fun setWorkStartTime() {
        this.workStartTime = this.findViewById(R.id.workStartTime)
        this.workStartTime.setOnClickListener {
            this.setStartTime()
        }
    }

    private fun setWorkEndTime() {
        this.workEndTime = this.findViewById(R.id.workEndTime)
        this.workEndTime.setOnClickListener {
            this.setEndTime()
        }
    }

    private fun setSaveWorkEntry() {
        this.saveWorkEntry = this.findViewById(R.id.saveWorkEntry)
        this.saveWorkEntry.setOnClickListener { view: View ->

            var hashMap = HashMap<String, Any>();

            hashMap.put(COL_EMPLOYEEID,this.workEntryViewModel.empId)
            hashMap.put(COL_STARTTIME,this.workEntryViewModel.startTime)
            hashMap.put(COL_ENDTIME,this.workEntryViewModel.endTime)
            hashMap.put(COL_WORKDATE,this.workEntryViewModel.date)
            hashMap.put(COL_PAYMENTSTATUS,if(workEntryViewModel.paymentStatus) 1 else 0)
            hashMap.put(COL_AMOUNT,PaylogHelper.amountCalculator(PaylogHelper.timeDifference(workEntryViewModel.endTime,workEntryViewModel.startTime),hourlyPay))

            var db = DBStorage(this)
            var result = db.insertData(WORKLOG_TABLE,hashMap)

            if(result == -1.toLong())
                Toast.makeText(this, DATA_ENTRY_FAILED, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, DATA_ENTRY_SUCCESSFUL, Toast.LENGTH_SHORT).show()
            db.close()
            finish()
        }
    }

    private fun setSpinner() {
        this.selectemployee = this.findViewById(R.id.select_employee)
        selectemployee.onItemSelectedListener = this

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item, employeeNames as List<Any?>
        )
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectemployee.adapter = ad
    }

    private fun setDate(){
        var c = Calendar.getInstance();
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        var dpc = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,year,month,day ->
            workDate.setText("${month + 1}/${day}/${year}")
            workEntryViewModel.date = "${month + 1}/${day}/${year}"
    },year,month,day)
        dpc.show()
    }

    private fun setStartTime() {
        var c = Calendar.getInstance()

        var hour = c.get(Calendar.HOUR_OF_DAY)
        var minitue = c.get(Calendar.MINUTE)

        TimePickerDialog(this, { view, hourOfDay, minite ->
            var time = "${hourOfDay}:${minite}"
            workStartTime.setText(time)
            workEntryViewModel.startTime = time
        },hour,minitue,false).show()
    }

    private fun setEndTime() {
        var c = Calendar.getInstance()

        var hour = c.get(Calendar.HOUR_OF_DAY)
        var minitue = c.get(Calendar.MINUTE)

        TimePickerDialog(this, { view, hourOfDay, minite ->
            var time = "${hourOfDay}:${minite}"
            this.workEndTime.setText(time)
            workEntryViewModel.endTime = time
        },hour,minitue,false).show()
    }

}