package com.example.paylog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.ViewModelProviders
import com.example.paylog.constants.*

class NewEmployeeActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var newEmployeeType: Spinner;
    private lateinit var firstName: EditText;
    private lateinit var lastName: EditText;
    private lateinit var hourlyPay: EditText;
    private lateinit var saveButton: Button;

    val newEmployeeViewModel: NewEmployeeViewModel by lazy {
        ViewModelProviders.of(this)[NewEmployeeViewModel::class.java]
    }

    private var employeeTypes: Array<String> = arrayOf<String>("Dish Washer","Cashier", "Chef", "Organizer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_employee)
        this.setControls()
    }

    private fun setControls() {
        this.setSpinner()
        this.setFirstName()
        this.setLastName()
        this.setHourlyPay()
        this.setSaveButton()
    }

    private fun setFirstName() {
        this.firstName = this.findViewById(R.id.new_employee_firstName)
        this.firstName.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    newEmployeeViewModel.firstName = p0.toString()
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun setLastName() {
        this.lastName = this.findViewById(R.id.new_employee_lastName)
        this.lastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newEmployeeViewModel.lastName = p0.toString()
            }

            override fun afterTextChanged(s: Editable) { }
        })
    }

    private fun setHourlyPay() {
        this.hourlyPay = this.findViewById(R.id.hourlyPay)
        this.hourlyPay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newEmployeeViewModel.hourlyPay = p0.toString()
            }

            override fun afterTextChanged(s: Editable) { }
        })
    }

    private fun setSpinner() {
        this.newEmployeeType = this.findViewById(R.id.new_employee_type)
        newEmployeeType.onItemSelectedListener = this

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,employeeTypes)
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        newEmployeeType.adapter = ad
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        this.newEmployeeViewModel.employeeType = position
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun setSaveButton() {
        this.saveButton = this.findViewById(R.id.save)
        this.saveButton.setOnClickListener { view: View ->

            var hashMap = HashMap<String, Any>();

            hashMap.put(COL_FNAME,this.newEmployeeViewModel.firstName)
            hashMap.put(COL_LNAME,this.newEmployeeViewModel.lastName)
            hashMap.put(COL_HPAY,this.newEmployeeViewModel.hourlyPay.toInt())
            hashMap.put(COL_EMPTYPE,this.newEmployeeViewModel.employeeType.toString())

            var db = DBStorage(this)
            var result = db.insertData(EMPLOYEE_TABLE,hashMap)

            if(result == -1.toLong())
                Toast.makeText(this, DATA_ENTRY_FAILED, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, DATA_ENTRY_SUCCESSFUL, Toast.LENGTH_SHORT).show()
            db.close()
            finish()
        }
    }
}