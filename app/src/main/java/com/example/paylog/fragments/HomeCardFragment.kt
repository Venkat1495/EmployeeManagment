package com.example.paylog.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.paylog.R

class HomeCardFragment : Fragment() {

    interface HomeFragmentListerner {
        fun setOnClickListerner(context: Context)
    }

    private lateinit var cardText: TextView
    private lateinit var icon: ImageView
    private lateinit var card: CardView
    public var homecardListener : HomeFragmentListerner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.home_card_fragment,container,false)
        setConnections(view)
        setData()
        return view
    }

    private fun setConnections(view:View) {
        cardText = view.findViewById(R.id.homeCardText)
        icon = view.findViewById(R.id.homeCardIcon)
        card = view.findViewById(R.id.card)
    }

    private fun setData() {
        var text= arguments?.getInt("cardText") as Int
        cardText.setText(text)
        var iconSrc = arguments?.getInt("icon") as Int
        icon.setImageResource(iconSrc)
        card.setOnClickListener{
            context?.let { it1 -> homecardListener?.setOnClickListerner(it1) }
        }
    }






}