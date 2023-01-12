package com.example.cardDataObserverTestApplication.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardDataObserverTestApplication.R
import com.example.cardDataObserverTestApplication.data.CardDataDatabaseEntity

class RequestHistoryRecyclerViewAdapter(private val onClick: (CardDataDatabaseEntity) -> Unit) : RecyclerView.Adapter<RequestHistoryRecyclerViewAdapter.CustomViewHolder>() {
    private var requestHistoryList: List<CardDataDatabaseEntity>? = emptyList()

    fun setRequestHistoryList(requestHistoryList: List<CardDataDatabaseEntity>?) {
        this.requestHistoryList = requestHistoryList
    }

    class CustomViewHolder(view: View, val onClick: (CardDataDatabaseEntity) -> Unit) : RecyclerView.ViewHolder(view) {
        private val storedCardNumber: TextView = view.findViewById(R.id.cardNumberItemTV)
        private val insertDataStamp: TextView = view.findViewById(R.id.insertTimeStampItemTV)
        private var currentDataSet: CardDataDatabaseEntity? = null

        init{
            itemView.setOnClickListener{
                currentDataSet?.let{
                    onClick(it)
                }
            }
        }

        fun bind(data: CardDataDatabaseEntity) {
            currentDataSet = data
            storedCardNumber.text = data.cardNumberEntity
            insertDataStamp.text = data.insertTimeMark
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_history_item, parent, false)
        return CustomViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        requestHistoryList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return requestHistoryList?.size!!
    }
}