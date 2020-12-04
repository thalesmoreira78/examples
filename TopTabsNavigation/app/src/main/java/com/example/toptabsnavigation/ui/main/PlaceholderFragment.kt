package com.example.toptabsnavigation.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.toptabsnavigation.ui.optionone.OptionOneActivity
import com.example.toptabsnavigation.ui.optiontwo.OptionTwoScrollingActivity
import com.example.toptabsnavigation.R
import com.example.toptabsnavigation.classes.ButtonClass
import com.example.toptabsnavigation.files.filename
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

            var jsonFileString = ""
            context!!.openFileInput(filename).use { stream ->
                val text = stream.bufferedReader().use {
                    it.readText()
                }
                jsonFileString = text
                Log.d("PlaceholderFragment", "onCreate, file: $text")
            }
            setButtonsList(jsonFileString)
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val layout = root.findViewById(R.id.fragmentMainLinearLayout) as LinearLayout

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        addButtons(layout, pageViewModel)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("PlaceholderFragment", "onViewCreated")
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun addButtons(layout: LinearLayout, pageViewModel: PageViewModel) {
        pageViewModel.buttonsList.observe(this, Observer<List<ButtonClass>> {
            it.forEachIndexed { idx, button ->
                Log.i("forEachIndexed", "> Item $idx:\n$button")
                var buttonToAdd: Button
                if (button.type === 1) {
                    buttonToAdd  = createButton(button ,Intent(context, OptionOneActivity::class.java))
                } else {
                    buttonToAdd = createButton(button, Intent(context, OptionTwoScrollingActivity::class.java))
                }
                layout.addView(buttonToAdd)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    fun createButton(buttonClass: ButtonClass, intent: Intent): Button {
        val button = Button(context)
        // setting layout_width and layout_height using layout parameters
        button.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        button.text = buttonClass.label
        addIcons(buttonClass, button)
        button.setOnClickListener {
            startActivity(intent)
        }

        button.setOnTouchListener(OnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= button.getRight() - button.getCompoundDrawables()
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {
                    writeJSONtoFile(button, buttonClass, filename)
                    return@OnTouchListener true
                }
            }
            false
        })

        return button;
    }

    fun addIcons(buttonClass: ButtonClass, button: Button) {
        var icon = R.drawable.ic_action_star_empty
        if (buttonClass.favorite) {
            icon = R.drawable.ic_action_star_full
        }

        val drawableLeft = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_album, null);
        val drawableLeftH = drawableLeft!!.intrinsicHeight
        val drawableLeftW = drawableLeft!!.intrinsicWidth
        drawableLeft.setBounds(0, 0, drawableLeftW, drawableLeftH)

        val drawableRight = ResourcesCompat.getDrawable(resources, icon, null);
        val drawableRightH = drawableRight!!.intrinsicHeight
        val drawableRightW = drawableRight!!.intrinsicWidth
        drawableRight.setBounds(0, 0, drawableRightW, drawableRightH)
        button.setCompoundDrawables(drawableLeft, null, drawableRight, null);
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun writeJSONtoFile(button: Button, buttonClass: ButtonClass, fileName: String) {
        // val jsonFileString = context?.let { getJsonDataFromAsset(it, fileName) }
        var jsonFileString = ""

        context!!.openFileInput(fileName).use { stream ->
            val text = stream.bufferedReader().use {
                it.readText()
            }
            jsonFileString = text
            Log.d("MainActivity", "onCreate, file: $text")
        }

        val listButtonType = object : TypeToken<List<ButtonClass>>() {}.type

        var buttons: List<ButtonClass> = Gson().fromJson(jsonFileString, listButtonType)
        buttons.forEach {
            Log.i("forEach", "> Item: $it")
            Log.i("buttonClass", "> Item: $buttonClass")
            if (it.id == buttonClass.id) {
                it.favorite = !it.favorite
                buttonClass.favorite = !buttonClass.favorite
                addIcons(buttonClass, button)
            }
        }

        var gson = Gson()
        var jsonString: String = gson.toJson(buttons)
        Log.d("writeJSONtoFile", jsonString)

        // val fileName = "buttons.kt"
        context!!.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
            output.write(jsonString!!.toByteArray())
        }
    }
}