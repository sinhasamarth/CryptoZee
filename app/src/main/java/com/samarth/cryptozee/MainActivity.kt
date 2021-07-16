package com.samarth.cryptozee

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samarth.cryptozee.databinding.ActivityMainBinding
import android.graphics.Color

import android.graphics.drawable.ColorDrawable
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.samarth.cryptozee.ui.base.fragments.home.HomeFragment
import com.samarth.cryptozee.ui.base.viewmodel.MainViewModel


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setActionBarColor()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2 ) as NavHostFragment
      navHostFragment.navController
    }

    private fun setActionBarColor() {
        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES){
            val actionBar = supportActionBar
            val colorDrawable = ColorDrawable(Color.parseColor("#001434"))
            actionBar?.setBackgroundDrawable(colorDrawable)
        }
    }
}