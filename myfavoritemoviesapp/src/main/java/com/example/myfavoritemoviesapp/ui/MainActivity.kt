package com.example.myfavoritemoviesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfavoritemoviesapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdaper = FavoritePagerAdaper(this, supportFragmentManager)
        view_pager.adapter = pagerAdaper
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }
}
