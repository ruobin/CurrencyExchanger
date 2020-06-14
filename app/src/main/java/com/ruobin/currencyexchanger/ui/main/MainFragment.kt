package com.ruobin.currencyexchanger.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ruobin.currencyexchanger.CurrencyExchangerApplication
import com.ruobin.currencyexchanger.ICurrencyConverter
import com.ruobin.currencyexchanger.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(),
    ICurrencyConverter {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var recentlyUsedCurrencyList: MutableList<String>
    private val gridViewColumnCount: Int = 3

    private lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as CurrencyExchangerApplication).getComponent()?.inject(this)

        source_label.setOnClickListener {
            (activity as MainActivity).sourceCurrencySelected()
        }
        convert_label.setOnClickListener {
            (activity as MainActivity).destinationCurrencySelected()
        }
        source_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                convert_amount.text = viewModel
                    .convertCurrency(source_label.text.toString(), source_amount.text.toString().toFloat(), convert_label.text.toString())
                    .toString()
                recent_used_list_recyclerview.adapter?.notifyDataSetChanged()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        recentlyUsedCurrencyList = viewModel.getRecentCurrencyList().toMutableList()
        recent_used_list_recyclerview.adapter = RecentUsedListAdapter(recentlyUsedCurrencyList, this)
        recent_used_list_recyclerview.layoutManager = GridLayoutManager(activity, gridViewColumnCount)
    }

    override fun onResume() {
        super.onResume()
        loadData()

    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private fun loadData() {
        disposable = viewModel.getAllLiveCurrencyRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.cacheAllCurrencyRatesToLocal(it)
                updateCurrencyViews()
            }, { error ->
                println(error.message)
            })
    }

    private fun saveData() {
        viewModel.saveRecentCurrencyListToPersistentCache()
    }

    fun updateCurrencyViews() {
        val selectedCurrency = arguments?.getString("selection")
        if (arguments?.getString("type").equals("source")) {
            source_label.text = selectedCurrency
        } else if (arguments?.getString("type").equals("destination")) {
            convert_label.text = selectedCurrency
        }
        convert_amount.text = viewModel
            .convertCurrency(source_label.text.toString(), source_amount.text.toString().toFloat(), convert_label.text.toString())
            .toString()
        recentlyUsedCurrencyList.clear()
        recentlyUsedCurrencyList.addAll(viewModel.getRecentCurrencyList())
        recent_used_list_recyclerview.adapter?.notifyDataSetChanged()
    }

    override fun convertCurrency(destinationCurrency: String): Float {
        return viewModel.convertCurrency(source_label.text.toString(), source_amount.text.toString().toFloat(), destinationCurrency)
    }

}
