package com.example.paylog

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class RecycleViewAdapter(var audits: ArrayList<ViewAuditModel>, var mListner: RecycleViewAdapter.onItemClickLister) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    interface onItemClickLister {
        fun onPayIconClicked(posistion :Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecycleViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_detail,parent,false)
        return ViewHolder(v,mListner)
    }

    override fun getItemCount(): Int {
        return audits.size
    }

    override fun onBindViewHolder(holder: RecycleViewAdapter.ViewHolder, position : Int) {
        holder.employeeName.text = audits[position].name
        holder.date.text = audits[position].date
        holder.time.text = audits[position].startTime + " - "+audits[position].endTime
        holder.tobePaid.text = audits[position].toBePaid

        if(audits[position].paymentStatus) {
            holder.payIcon.visibility = View.GONE
        }
    }

    inner class ViewHolder(itemView: View,lister: onItemClickLister) : RecyclerView.ViewHolder(itemView) {
        val employeeName: TextView = itemView.findViewById(R.id.employeeName)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
        val tobePaid: TextView = itemView.findViewById(R.id.tobePaid)
        val payIcon:  ImageView = itemView.findViewById(R.id.payIcon)

        init {
            payIcon.setOnClickListener {view:View->
                println(audits[absoluteAdapterPosition])
                println(absoluteAdapterPosition)
                println(audits[absoluteAdapterPosition].logId)
                lister.onPayIconClicked(audits[absoluteAdapterPosition].logId)
            }
        }

    }
}