package com.example.cardDataObserverTestApplication.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardDataObserverTestApplication.data.*
import com.example.cardDataObserverTestApplication.databinding.FragmentMainBinding
import com.example.cardDataObserverTestApplication.model.CardData
import com.example.cardDataObserverTestApplication.utils.DataConverter
import com.example.cardDataObserverTestApplication.utils.RequestHistoryRecyclerViewAdapter
import java.time.LocalDateTime

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var cardNumber: String
    private val database by lazy {
        activity?.let {
            CardDataDatabase.getInstance(it.applicationContext).cardDataDatabaseDao()
        }
    }
    private val dataConverter = DataConverter.getInstance()
    private var dataSet: CardData? = null
    private lateinit var recyclerViewAdapter: RequestHistoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        with(binding) {
            requestHistory.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                recyclerViewAdapter = RequestHistoryRecyclerViewAdapter{cardData -> adapterOnClick(cardData)}
                adapter = recyclerViewAdapter
            }

            viewModel.getDataFromDatabase().observe(viewLifecycleOwner) {
                recyclerViewAdapter.setRequestHistoryList(it)
                recyclerViewAdapter.notifyDataSetChanged()
            }
            viewModel.loadRequestHistory(database)

            viewModel.getCardData().observe(viewLifecycleOwner) {
                if (it != null) {
                    dataSet = it
                }

                schemeTV.text = it?.scheme.toString()
                cardTypeTV.text = it?.type.toString()
                brandTV.text = it?.brand.toString()
                prepaidTV.text = it?.prepaid.toString()
                lengthCardNumberTV.text = it?.number?.length.toString()
                luhnCardNumberTV.text = it?.number?.luhn.toString()
                countryNameTV.text = it?.country?.name.toString()
                val locationString =
                    "(latitude: ${it?.country?.latitude.toString()}, longitude: ${it?.country?.longitude.toString()})"
                locationTV.text = locationString
                bankNameTV.text = it?.bank?.name.toString()
                bankEmailTV.text = it?.bank?.url.toString()
                bankPhoneNumberTV.text = it?.bank?.phone.toString()
            }

            saveCardDataButton.setOnClickListener {
                if (dataSet != null) {
                    database?.insert(
                        dataConverter.convertDataSet(
                            dataSet,
                            cardNumber,
                            LocalDateTime.now()
                        )
                    )
                    viewModel.loadRequestHistory(database)
                }
            }

            deleteAllButton.setOnClickListener {
                database?.deleteAllNotes()
                viewModel.loadRequestHistory(database)
            }

            cardNumberET.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    cardNumber = cardNumberET.text.toString()
                }

                override fun afterTextChanged(s: Editable) {
                    viewModel.loadCardData(cardNumber)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adapterOnClick(cardData: CardDataDatabaseEntity) {
        viewModel.loadCardData(cardData.cardNumberEntity)
    }
}