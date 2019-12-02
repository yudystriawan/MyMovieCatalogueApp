package com.example.mymoviecatalogueapp.ui.setting


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.ui.preferences.SettingPreference

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fm = fragmentManager as FragmentManager
        fm.beginTransaction().replace(R.id.setting_holder, SettingPreference()).commit()
    }
}

