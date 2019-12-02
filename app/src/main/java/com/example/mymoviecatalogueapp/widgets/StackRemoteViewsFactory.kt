package com.example.mymoviecatalogueapp.widgets

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.data.database.MyDatabase
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.BASE_BACKDROP_URL
import com.example.mymoviecatalogueapp.internal.glide.GlideApp
import java.util.concurrent.ExecutionException

internal class StackRemoteViewsFactory(
    private val mContext: Context
) : RemoteViewsService.RemoteViewsFactory {

    private val widgetItems = ArrayList<String>()
    private val widgetItemsName = ArrayList<String>()
    private lateinit var movies: List<Movie>
    private lateinit var tvShows: List<TvShow>
    private lateinit var database: MyDatabase

    override fun onCreate() {
        database = MyDatabase(mContext)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        movies = database.movieDao().selectByFavoriteForWidget()
        tvShows = database.tvShowDao().selectByFavoriteforWidget()

        for (movie in movies) {
            widgetItems.add(movie.backdropPath ?: "")
            widgetItemsName.add(movie.title)
        }
        for (tvShow in tvShows) {
            widgetItems.add(tvShow.backdropPath ?: "")
            widgetItemsName.add(tvShow.name)
        }

        Binder.restoreCallingIdentity(identityToken)

    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {

        val item = widgetItems[position]
        val itemName = widgetItemsName[position]

        val backdropPath = BASE_BACKDROP_URL + item

        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        var bitmap: Bitmap? = null
        try {
            bitmap = GlideApp
                .with(mContext)
                .asBitmap()
                .load(backdropPath)
                .submit()
                .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        rv.setImageViewBitmap(R.id.img_widget_backdrop, bitmap)

        val extras = bundleOf(
            FavoriteBannerWidget.EXTRA_ITEM to itemName
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.img_widget_backdrop, fillInIntent)
        return rv
    }

    override fun getCount(): Int = widgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }

}