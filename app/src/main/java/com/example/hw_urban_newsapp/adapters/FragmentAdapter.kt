package com.example.hw_urban_newsapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hw_urban_newsapp.Models.Constants.TOTAL_NEWS_TAB
import com.example.hw_urban_newsapp.fragments.BusinessFragment
import com.example.hw_urban_newsapp.fragments.EntertainmentFragment
import com.example.hw_urban_newsapp.fragments.GeneralFragment
import com.example.hw_urban_newsapp.fragments.HealthFragment
import com.example.hw_urban_newsapp.fragments.ScienceFragment
import com.example.hw_urban_newsapp.fragments.SportsFragment
import com.example.hw_urban_newsapp.fragments.TechFragment

class FragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){

    override fun getItemCount(): Int = TOTAL_NEWS_TAB

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                return GeneralFragment()
            }
            1 -> {
                return BusinessFragment()
            }
            2 -> {
                return EntertainmentFragment()
            }
            3 -> {
                return ScienceFragment()
            }
            4 -> {
                return SportsFragment()
            }
            5 -> {
                return TechFragment()
            }
            6 -> {
                return HealthFragment()
            }

            else -> return BusinessFragment()

        }
    }
}