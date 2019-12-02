package com.example.mymoviecatalogueapp.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoviecatalogueapp.R
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val pagerAdaper = FavoritePagerAdaper(this, supportFragmentManager)
        view_pager_favorite.adapter = pagerAdaper
        tabs_favorite.setupWithViewPager(view_pager_favorite)

        supportActionBar?.elevation = 0f
    }
}
