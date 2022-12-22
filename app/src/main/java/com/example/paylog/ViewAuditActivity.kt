package com.example.paylog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class ViewAuditActivity : AppCompatActivity() {

    private lateinit var pageViewer: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var allAudits: ArrayList<ViewAuditModel>
    private lateinit var pendingAudits: ArrayList<ViewAuditModel>

    private val auditViewModel: AuditViewModel by lazy {
        ViewModelProviders.of(this)[AuditViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_audit)
        this.connectViews()
        this.loadData()
        this.setUpTabs()
    }

    private fun connectViews() {
        this.tabLayout = this.findViewById(R.id.tabLayout)
        this.pageViewer = this.findViewById(R.id.pageViewer)
    }

    private fun loadData() {
        var db = DBStorage(this)
        this.allAudits = db.getWorkLog()
        Log.d("All Audits",this.allAudits.toString())
        println(this.allAudits.toString())
        this.pendingAudits = allAudits.filter { !it.paymentStatus } as ArrayList<ViewAuditModel>
        Log.d("ViewAuditActivity", allAudits.toString())
        Log.d("PendingAudits", pendingAudits.toString())
    }

    private fun setUpTabs() {
        val adapter = ViewPageAdapter(supportFragmentManager)

        //set data to all tab fragment
        var allFragment = AuditFragment()
        var allBundle = Bundle()
        allBundle.putParcelableArrayList("audits", allAudits)
        allFragment.arguments = allBundle

        // set data to pending tab fragment
        var pendingFragment = AuditFragment()
        var pendingBundle = Bundle()
        pendingBundle.putParcelableArrayList("audits", pendingAudits)
        pendingFragment.arguments = pendingBundle

        // add fragments to adapter
        adapter.addFragment(allFragment, "All")
        adapter.addFragment(pendingFragment, "Pending")
        pageViewer.adapter = adapter
        tabLayout.setupWithViewPager(pageViewer)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("ViewAuditActivity","Resumed")
        finish()
    }
}