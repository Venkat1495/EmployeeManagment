package com.example.paylog

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.paylog.constants.*
import com.example.paylog.helpers.PaylogHelper

class DBStorage(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createEmployeeTable = "CREATE TABLE " + EMPLOYEE_TABLE + " (" +
                COL_EMPLOYEEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_FNAME + " VARCHAR(256)," +
                COL_LNAME + " VARCHAR(256)," +
                COL_HPAY + " INTEGER," +
                COL_EMPTYPE + " VARCHAR(256))";
        p0?.execSQL(createEmployeeTable)

        val createWorkTable = "CREATE TABLE " + WORKLOG_TABLE + " (" +
                COL_WORKID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_WORKDATE + " VARCHAR(256)," +
                COL_STARTTIME + " VARCHAR(256)," +
                COL_ENDTIME + " VARCHAR(256)," +
                COL_PAYMENTSTATUS + " NUMBER(1)," +
                COL_AMOUNT + " DECIMAL(10,2)," +
                COL_EMPLOYEEID + " BIGINT, FOREIGN KEY (${COL_EMPLOYEEID}) REFERENCES $EMPLOYEE_TABLE (${COL_EMPLOYEEID}))";
        p0?.execSQL(createWorkTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(tableName: String, insertValues: HashMap<String, Any>): Long {
        val db = this.writableDatabase
        var cv = ContentValues()

        for (key in insertValues.keys) {
            if (insertValues[key] is String) {
                cv.put(key, insertValues[key] as String)
            } else if (insertValues[key] is Int) {
                cv.put(key, insertValues[key] as Int)
            }else if (insertValues[key] is Double) {
                cv.put(key, insertValues[key] as Double)
            }
        }
        return db.insert(tableName, null, cv)
    }

    @SuppressLint("Range")
    fun readEmpsData(tableName: String): ArrayList<Employee> {
        var list: ArrayList<Employee> = ArrayList()
        val db = this.readableDatabase
        val query = SELECT_ALL_QUERY + tableName
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var employee = Employee(result.getInt(result.getColumnIndex(COL_EMPLOYEEID)))
                employee.empFName = result.getString(result.getColumnIndex(COL_FNAME))
                employee.empLname = result.getString(result.getColumnIndex(COL_LNAME))
                employee.hPay = result.getInt(result.getColumnIndex(COL_HPAY))
                employee.empType = result.getString(result.getColumnIndex(COL_EMPTYPE)).toInt()
                employee.empFullName = employee.empFName + employee.empLname
                list.add(employee)
            } while (result.moveToNext())
        }
//        db.close()
        return list
    }

    @SuppressLint("Range")
    fun getWorkLog(): ArrayList<ViewAuditModel> {
        var wLog = ArrayList<ViewAuditModel>()

        var readDb = this.readableDatabase
        var query =
            "SELECT * FROM $WORKLOG_TABLE as w LEFT JOIN $EMPLOYEE_TABLE as emp on w.${COL_EMPLOYEEID} = emp.${COL_EMPLOYEEID} ORDER BY w.$COL_WORKID DESC"
        var cursor = readDb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                var viewAuditModel =
                    ViewAuditModel(cursor.getInt(cursor.getColumnIndex(COL_WORKID)))
                viewAuditModel.date = cursor.getString(cursor.getColumnIndex(COL_WORKDATE))
                viewAuditModel.employeeId = cursor.getInt(cursor.getColumnIndex(COL_EMPLOYEEID))
                viewAuditModel.toBePaid = "$" + cursor.getDouble(cursor.getColumnIndex(COL_AMOUNT)).toString()
                viewAuditModel.endTime = cursor.getString(cursor.getColumnIndex(COL_ENDTIME))
                viewAuditModel.startTime = cursor.getString(cursor.getColumnIndex(COL_STARTTIME))
                viewAuditModel.date = cursor.getString(cursor.getColumnIndex(COL_WORKDATE))
                viewAuditModel.paymentStatus = cursor.getInt(cursor.getColumnIndex(COL_PAYMENTSTATUS)) == 1
                viewAuditModel.name = cursor.getString(cursor.getColumnIndex(COL_FNAME)) + " " + cursor.getString(cursor.getColumnIndex(COL_LNAME))
//                viewAuditModel.hourlyPay = cursor.getInt((cursor.getColumnIndex(COL_HPAY)))
                wLog.add(viewAuditModel)
            } while (cursor.moveToNext())
        }
//        readDb.close()
        return wLog
    }

    @SuppressLint("Range")
    fun getWorkLogDetailsById(worklogId: Int): ViewAuditModel {
        var db = this.readableDatabase
        var query =
            "SELECT  * FROM $WORKLOG_TABLE as w INNER JOIN $EMPLOYEE_TABLE as emp on w.$COL_EMPLOYEEID = emp.$COL_EMPLOYEEID where w.$COL_WORKID = $worklogId ORDER BY w.$COL_WORKID DESC"
        var result = db.rawQuery(query, null)
        var worklog = ViewAuditModel(worklogId)

        if(result.moveToFirst()){
            do{
                worklog.date = result.getString(result.getColumnIndex(COL_WORKDATE))
                worklog.employeeId = result.getInt(result.getColumnIndex(COL_EMPLOYEEID))
                worklog.endTime = result.getString(result.getColumnIndex(COL_ENDTIME))
                worklog.startTime = result.getString(result.getColumnIndex(COL_STARTTIME))
                worklog.hourlyPay = result.getInt(result.getColumnIndex(COL_HPAY))
                worklog.toBePaid = "$" + result.getDouble(result.getColumnIndex(COL_AMOUNT))
                worklog.date = result.getString(result.getColumnIndex(COL_WORKDATE))
                worklog.name =
                    result.getString(result.getColumnIndex(COL_FNAME)) + " " + result.getString(
                        result.getColumnIndex(COL_LNAME))
            }while (result.moveToNext())
        }
//        db.close()
        return worklog
    }

    @SuppressLint("Recycle")
    fun settleUp(worklogId: Int): Int{
        var db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_PAYMENTSTATUS,1)
        println(worklogId)
        var result = db.update(WORKLOG_TABLE,cv, "$COL_WORKID = ?", Array(1){"$worklogId"})
        println("For $worklogId, number of rows updated = $result")
//      db.close()
        return 1
    }

    @SuppressLint("Range")
    fun getDashBoardData(): DashBoardModel {
        var db = readableDatabase
        var query = "SELECT  SUM(mergedTable.amount) as total, * from  (SELECT  * FROM $WORKLOG_TABLE as w INNER JOIN $EMPLOYEE_TABLE as emp on w.$COL_EMPLOYEEID = emp.$COL_EMPLOYEEID WHERE $COL_PAYMENTSTATUS = 0) as mergedTable  GROUP BY mergedTable.$COL_EMPLOYEEID"
        var result = db.rawQuery(query,null)
        var dashboard = DashBoardModel()
        var total= 0.0;
        if(result.moveToFirst()) {
            do{
                var dashBoardEmpModel = DashBoardEmpModel()
                dashBoardEmpModel.name = result.getString(result.getColumnIndex(COL_FNAME)) + ' ' + result.getString(result.getColumnIndex(
                    COL_LNAME))
                dashBoardEmpModel.totalAmount = result.getDouble((result.getColumnIndex(COL_TOTAL)))
                total += dashBoardEmpModel.totalAmount
                dashboard.employeeAmounts.add(dashBoardEmpModel)
            }while (result.moveToNext())
            dashboard.totalModel.totalAmount = total
        }
        println(dashboard.totalModel.totalAmount)
        return dashboard
    }
}