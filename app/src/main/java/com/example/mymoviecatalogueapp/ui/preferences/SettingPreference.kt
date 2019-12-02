package com.example.mymoviecatalogueapp.ui.preferences

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.alarm.AlarmRepeatingReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import java.text.SimpleDateFormat
import java.util.*

class SettingPreference : PreferenceFragmentCompat(), KodeinAware,
    SharedPreferences.OnSharedPreferenceChangeListener {

    override val kodein by closestKodein()

    private lateinit var viewModel: SettingPreferenceViewModel

    private lateinit var KEY_DAILY_REMINDER: String
    private lateinit var KEY_DAILY_MOVIE: String

    private lateinit var dailyReminderPreference: SwitchPreference
    private lateinit var dailyMoviePreference: SwitchPreference

    private val alarmRepeatingReceiver = AlarmRepeatingReceiver()

    private val viewModelFactory
            : ((String) -> SettingPreferenceViewModelFactory) by factory()


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
    }

    private fun getMovies() {
        val date = getTodayDate()

        viewModel = ViewModelProviders.of(this, viewModelFactory(date))
            .get(SettingPreferenceViewModel::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            val listMovie = viewModel.movies.await()

            listMovie.observe(this@SettingPreference, Observer {
                alarmRepeatingReceiver.setDailyMovie(activity!!, it.results)
            })
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun init() {
        KEY_DAILY_REMINDER = resources.getString(R.string.key_daily_reminder)
        KEY_DAILY_MOVIE = resources.getString(R.string.key_daily_movie)

        dailyReminderPreference =
            findPreference<SwitchPreference>(KEY_DAILY_REMINDER) as SwitchPreference
        dailyMoviePreference =
            findPreference<SwitchPreference>(KEY_DAILY_MOVIE) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            KEY_DAILY_REMINDER -> {
                val state = sharedPreferences!!.getBoolean(KEY_DAILY_REMINDER, false)
                if (state) {
                    alarmRepeatingReceiver.setDailyReminder(activity!!)
                } else {
                    alarmRepeatingReceiver.cancelAlarm(
                        activity!!,
                        AlarmRepeatingReceiver.TYPE_DAILY_REMINDER
                    )
                }
            }
            KEY_DAILY_MOVIE -> {
                val state = sharedPreferences!!.getBoolean(KEY_DAILY_MOVIE, false)
                if (state) {
                    getMovies()
                } else {
                    alarmRepeatingReceiver.cancelAlarm(
                        activity!!,
                        AlarmRepeatingReceiver.TYPE_DAILY_MOVIE
                    )
                }
            }
        }
    }

}