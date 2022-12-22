package com.example.paylog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.MediaController
import androidx.cardview.widget.CardView
import com.example.paylog.activities.DashBoardActivity
import com.example.paylog.fragments.HomeCardFragment

const val MAINACTIVITY = "MainActivity"

class MainActivity : AppCompatActivity() {
//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setCallbacks()
    }

    private fun setCallbacks(){

        var homeCardFragment = HomeCardFragment()
        var bundle = Bundle()
        bundle.putInt("cardText",R.string.newEmployee)
        bundle.putInt("icon",R.drawable.add_employee_icon)
        homeCardFragment.arguments = bundle
        homeCardFragment.homecardListener = object: HomeCardFragment.HomeFragmentListerner {
            override fun setOnClickListerner(context: Context) {
                Log.d(MAINACTIVITY,"New Employee Create Clicked !!")
                var intent = Intent(context, NewEmployeeActivity::class.java)
                startActivity(intent)
            }
        }
        supportFragmentManager.beginTransaction().add(R.id.newEmployeeCard,homeCardFragment).commit()

        var addWorkLog = HomeCardFragment()
        bundle = Bundle()
        bundle.putInt("cardText",R.string.add_work)
        bundle.putInt("icon",R.drawable.work_time_icon)
        addWorkLog.arguments = bundle
        addWorkLog.homecardListener = object: HomeCardFragment.HomeFragmentListerner {
            override fun setOnClickListerner(context: Context) {
                Log.d(MAINACTIVITY,"Add work Clicked !!")
                var intent = Intent(context, DetailsEntryActivity::class.java)
                startActivity(intent)
            }
        }
        supportFragmentManager.beginTransaction().add(R.id.newTimeEntry,addWorkLog).commit()

        var viewWorkLog = HomeCardFragment()
        bundle = Bundle()
        bundle.putInt("cardText",R.string.view_History)
        bundle.putInt("icon",R.drawable.audit_log_icon)
        viewWorkLog.arguments = bundle
        viewWorkLog.homecardListener = object: HomeCardFragment.HomeFragmentListerner {
            override fun setOnClickListerner(context: Context) {
                Log.d(MAINACTIVITY,"View work log Clicked !!")
                var intent = Intent(context, ViewAuditActivity::class.java)
                startActivity(intent)
            }
        }
        supportFragmentManager.beginTransaction().add(R.id.viewWorkHistory,viewWorkLog).commit()

        var dashboardFragment = HomeCardFragment()
        bundle = Bundle()
        bundle.putInt("cardText",R.string.dashBoard)
        bundle.putInt("icon",R.drawable.dashboard_icon)
        dashboardFragment.arguments = bundle
        dashboardFragment.homecardListener = object: HomeCardFragment.HomeFragmentListerner {
            override fun setOnClickListerner(context: Context) {
                var intent = Intent(context, DashBoardActivity::class.java)
                Log.d(MAINACTIVITY,"Dashboard Card Clicked")
                startActivity(intent)
            }
        }
        supportFragmentManager.beginTransaction().add(R.id.dashBoardCard,dashboardFragment).commit()

        var aboutFragment = HomeCardFragment()
        bundle = Bundle()
        bundle.putInt("cardText",R.string.about)

        bundle.putInt("icon",R.drawable.info_icon)
        aboutFragment.arguments = bundle
        aboutFragment.homecardListener = object: HomeCardFragment.HomeFragmentListerner {
            override fun setOnClickListerner(context: Context) {
                Log.d(MAINACTIVITY,"About Card Clicked")
                var intent = Intent(context, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        supportFragmentManager.beginTransaction().add(R.id.about,aboutFragment).commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d(MAINACTIVITY,"onStart triggered")
    }

    override fun onResume() {
        super.onResume()
        Log.d(MAINACTIVITY,"onResume triggered")
    }

    override fun onPause() {
        super.onPause()
        Log.d(MAINACTIVITY,"onPause triggered")
    }

    override fun onStop() {
        super.onStop()
        Log.d(MAINACTIVITY,"onStop triggered")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MAINACTIVITY,"onDestroy triggered")
    }

}