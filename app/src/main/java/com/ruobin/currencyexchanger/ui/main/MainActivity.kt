package com.ruobin.currencyexchanger.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ruobin.currencyexchanger.R

class MainActivity : AppCompatActivity() {

    private var mainFragment: MainFragment? = null
    private var currencySelectionListFragment: CurrencySelectionListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            openMainFragment()
        }
    }

    private fun getMainFragment(): MainFragment {
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance()
        }
        return mainFragment as MainFragment
    }

    private fun getCurrencySelectionListFragment(): CurrencySelectionListFragment {
        if (currencySelectionListFragment == null) {
            currencySelectionListFragment = CurrencySelectionListFragment.newInstance()
        }
        return currencySelectionListFragment as CurrencySelectionListFragment
    }

    private fun openMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, getMainFragment())
            .commitAllowingStateLoss()
    }

    fun backToMainFragmentFrom(fragment: Fragment, selectedCurrency: String) {
        val bundle = getCurrencySelectionListFragment().arguments
        bundle?.putString("selection", selectedCurrency)
        getMainFragment().arguments = bundle
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commitAllowingStateLoss()
        getMainFragment().updateCurrencyViews()
    }

    private fun openCurrencySelectionListFragment() {
        supportFragmentManager?.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
            ?.add(R.id.container, getCurrencySelectionListFragment())
            ?.addToBackStack(CurrencySelectionListFragment::class.java.toString())
            ?.commitAllowingStateLoss()
    }

    fun sourceCurrencySelected() {
        val bundle = Bundle()
        bundle.putString("type", "source")
        getCurrencySelectionListFragment().arguments = bundle
        openCurrencySelectionListFragment()
    }

    fun destinationCurrencySelected() {
        val bundle = Bundle()
        bundle.putString("type", "destination")
        getCurrencySelectionListFragment().arguments = bundle
        openCurrencySelectionListFragment()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 0) {
            super.onBackPressed()
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}
