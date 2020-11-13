package com.example.toptabsnavigation


import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.toptabsnavigation.ui.optionone.OptionOneSectionsPagerAdapter

class OptionOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option_one)
        val sectionsPagerAdapter = OptionOneSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager_option_one)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs_option_one)
        tabs.setupWithViewPager(viewPager)

        setSupportActionBar(findViewById(R.id.optionOneToolbar))
        val actionBar = supportActionBar
        actionBar!!.title = "Option 1"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}