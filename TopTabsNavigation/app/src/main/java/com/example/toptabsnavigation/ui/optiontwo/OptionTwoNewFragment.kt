package com.example.toptabsnavigation.ui.optiontwo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toptabsnavigation.R

class OptionTwoNewFragment : Fragment() {

    companion object {
        fun newInstance() = OptionTwoNewFragment()
    }

    private lateinit var viewModel: OptionTwoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.option_two_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OptionTwoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}