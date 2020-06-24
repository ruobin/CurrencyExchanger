package com.ruobin.currencyexchanger.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruobin.currencyexchanger.CurrencyExchangerApplication
import com.ruobin.currencyexchanger.R
import kotlinx.android.synthetic.main.currency_selection_list_fragment.*
import javax.inject.Inject


class CurrencySelectionListFragment : Fragment(), CurrencyViewHolder.CurrencySelectionListener {

    companion object {
        fun newInstance() = CurrencySelectionListFragment()
    }

    @Inject
    lateinit var viewModel: CurrencySelectionListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.currency_selection_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as CurrencyExchangerApplication).getComponent()?.inject(this)

        if (viewModel.getCurrencyList() != null) {
            currency_selection_list_recyclerview.adapter = CurrencySelectionListAdapter(viewModel.getCurrencyList()!!, this)
            currency_selection_list_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onItemClick(currencySelection: String) {
        viewModel.addToRecentCurrencyList(currencySelection)
        (activity as MainActivity).backToMainFragmentFrom(this, currencySelection)
    }

}