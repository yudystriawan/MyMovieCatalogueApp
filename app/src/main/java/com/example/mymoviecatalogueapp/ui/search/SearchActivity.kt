package com.example.mymoviecatalogueapp.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoviecatalogueapp.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "extra_query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = intent.getStringExtra(EXTRA_QUERY) ?: throw Exception()

        val pagerAdapter = SearchPagerAdapter(this, query, supportFragmentManager)
        view_pager_search.adapter = pagerAdapter
        tabs_search.setupWithViewPager(view_pager_search)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.search)
    }
}
