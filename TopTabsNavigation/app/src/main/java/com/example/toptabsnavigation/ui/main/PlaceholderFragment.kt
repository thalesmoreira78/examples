package com.example.toptabsnavigation.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.toptabsnavigation.OptionOneActivity
import com.example.toptabsnavigation.OptionTwoScrollingActivity
import com.example.toptabsnavigation.R
import com.example.toptabsnavigation.classes.ButtonClass
import java.io.IOException

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        var section = arguments?.getInt(ARG_SECTION_NUMBER)

        val jsonFileString = context?.let { getJsonDataFromAsset(it, "data.json") }
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }

        val gson = Gson()
        val listbuttonType = object : TypeToken<List<ButtonClass>>() {}.type

        var buttons: List<ButtonClass> = gson.fromJson(jsonFileString, listbuttonType)
        val layout = root.findViewById(R.id.fragmentMainLinearLayout) as LinearLayout
        buttons.forEachIndexed { idx, button ->
            Log.i("data", "> Item $idx:\n$button")
            var buttonToAdd: Button
            if (section === 1 && button.favorite) {
                if (button.type === 1) {
                    buttonToAdd  = createButton(button.label ,Intent(context, OptionOneActivity::class.java), button.favorite)
                } else {
                    buttonToAdd = createButton(button.label, Intent(context, OptionTwoScrollingActivity::class.java), button.favorite)
                }
                layout.addView(buttonToAdd)
            } else {
                if (button.type === 1) {
                    buttonToAdd  = createButton(button.label ,Intent(context, OptionOneActivity::class.java), button.favorite)
                } else {
                    buttonToAdd = createButton(button.label, Intent(context, OptionTwoScrollingActivity::class.java), button.favorite)
                }
                layout.addView(buttonToAdd)
            }

        }

        // val button1 = createButton("Option 1", Intent(context, OptionOneActivity::class.java))
        // val button2 = createButton("Name", Intent(context, OptionOneActivity::class.java))
        // val button3 = createButton("Name", Intent(context, OptionTwoScrollingActivity::class.java))
        // val button4 = createButton("Option 2",Intent(context, OptionTwoScrollingActivity::class.java))

        // layout.addView(button1)
        // layout.addView(button2)
        // layout.addView(button3)
        // layout.addView(button4)

        return root
    }

    fun createButton(text: String, intent: Intent, favorite: Boolean): Button {
        var icon = R.drawable.ic_action_star_empty
        if (favorite) {
            icon = R.drawable.ic_action_star_full
        }
        val button = Button(context)
        // setting layout_width and layout_height using layout parameters
        button.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        button.text = text

        val drawableLeft = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_album, null);
        val drawableLeftH = drawableLeft!!.intrinsicHeight
        val drawableLeftW = drawableLeft!!.intrinsicWidth
        drawableLeft.setBounds(0, 0, drawableLeftW, drawableLeftH)

        val drawableRight =
            ResourcesCompat.getDrawable(resources, icon, null);
        val drawableRightH = drawableRight!!.intrinsicHeight
        val drawableRightW = drawableRight!!.intrinsicWidth
        drawableRight.setBounds(0, 0, drawableRightW, drawableRightH)

        button.setCompoundDrawables(drawableLeft, null, drawableRight, null);
        button.setOnClickListener {
            startActivity(intent)
        }
        return button;
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
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}