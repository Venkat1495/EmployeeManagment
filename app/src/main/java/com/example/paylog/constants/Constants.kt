package com.example.paylog.constants

// Employee log table
const val DATABASE_NAME = "paylogDB"
const val EMPLOYEE_TABLE = "employeeDetails"
const val COL_FNAME = "firstName"
const val COL_LNAME = "lastName"
const val COL_HPAY = "hourlyPay"
const val COL_EMPTYPE = "employeeType"

// Work Log table
const val COL_ID = "id"
const val WORKLOG_TABLE = "workLogs"
const val COL_WORKDATE = "workDate"
const val COL_STARTTIME = "startTime"
const val COL_ENDTIME = "endTime"
const val COL_EMPLOYEEID = "employeeID"
const val COL_WORKID = "workID"
const val COL_PAYMENTSTATUS = "paymentStatus"
const val COL_AMOUNT = "amount"
const val COL_TOTAL = "total"

// TOAST MSGS
const val DATA_ENTRY_FAILED = "Saving Failed"
const val DATA_ENTRY_SUCCESSFUL = "Saved Successfully"
const val PAY_SETTLEMENT_SUCCESSFUL = "Pay settlement successful"
const val PAY_SETTLEMENT_FAILED = "Pay settlement failed"

// Query sub-strings
const val SELECT_ALL_QUERY = "SELECT * FROM "

//bundle Ids
const val EMP_ID = "empId"
const val SETTLE_PAY_ARGS = "settlementPayArguments"