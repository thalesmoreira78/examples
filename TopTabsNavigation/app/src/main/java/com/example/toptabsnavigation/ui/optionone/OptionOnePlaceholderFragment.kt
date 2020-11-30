package com.example.toptabsnavigation.ui.optionone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.toptabsnavigation.R

/**
 * A placeholder fragment containing a simple view.
 */
class OptionOnePlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: OptionOnePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(OptionOnePageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_option_one, container, false)
        val textView: TextView = root.findViewById(R.id.section_label_option_one)
        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })

        val buttonOptionNewActivity: Button = root.findViewById(R.id.buttonOptionNewActivity)
        buttonOptionNewActivity.setOnClickListener { view ->
            val intent = Intent(context, OptionOneNewActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): OptionOnePlaceholderFragment {
            return OptionOnePlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}