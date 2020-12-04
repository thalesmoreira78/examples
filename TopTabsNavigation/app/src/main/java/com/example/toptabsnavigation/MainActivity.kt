package com.example.toptabsnavigation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.toptabsnavigation.files.data
import com.example.toptabsnavigation.files.filename
import com.example.toptabsnavigation.ui.main.PageViewModel
import com.example.toptabsnavigation.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import java.io.*

class MainActivity : AppCompatActivity() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val file = File(getFileStreamPath(""), filename)

        if (!file.isFile) {
            file.bufferedWriter().use { out -> out.write(data) }
        }

        try {
            baseContext!!.openFileInput(filename).use { stream ->
                val text = stream.bufferedReader().use {
                    it.readText()
                }
                Log.d("FILE", "$filename, file: $text")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {


            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPageSelected(position: Int) {
                // val intent = Intent(baseContext, MainActivity::class.java)
                // startActivity(intent)
                viewPager.offscreenPageLimit = 0
                baseContext!!.openFileInput(filename).use { stream ->
                    val text = stream.bufferedReader().use {
                        it.readText()
                    }
                    // val listButtonType = object : TypeToken<List<ButtonClass>>() {}.type
                    // var buttons: List<ButtonClass> = Gson().fromJson(text, listButtonType)
                    pageViewModel.setButtonsList(text)
                    (viewPager.adapter as SectionsPagerAdapter).notifyDataSetChanged();
                    Log.d("MainActivity", "onPageSelected, file: $text")
                    // viewPager.currentItem = 1
                }
            }
        })
    }
}