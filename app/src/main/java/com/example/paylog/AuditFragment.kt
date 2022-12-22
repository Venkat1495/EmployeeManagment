package com.example.paylog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paylog.activities.SettlePaymentActivity
import com.example.paylog.constants.EMP_ID
import com.example.paylog.constants.SETTLE_PAY_ARGS

class AuditFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var recycleViewForAudit : RecyclerView
    private lateinit var allAuditList : ArrayList<ViewAuditModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            allAuditList = arguments?.getParcelableArrayList<ViewAuditModel>("audits") as ArrayList<ViewAuditModel>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.fragment_all_audit, container, false)
        this.setConnections(view)
        this.layoutManager = LinearLayoutManager(context)
        this.recycleViewForAudit.layoutManager = this.layoutManager
        var adapter = RecycleViewAdapter(allAuditList, object :  RecycleViewAdapter.onItemClickLister {
            override fun onPayIconClicked(logId: Int) {
                var bundle = Bundle()
                println("Settlement Started $logId")
                bundle.putInt(EMP_ID,logId)
                var intent = Intent(context,SettlePaymentActivity::class.java).also {
                    it.putExtra(SETTLE_PAY_ARGS, bundle)
                }
                startActivity(intent)
            }
        })
        recycleViewForAudit.adapter = adapter
        return view
    }

    private fun setConnections(view: View) {
        this.recycleViewForAudit = view.findViewById(R.id.allRecyleView)
    }
}