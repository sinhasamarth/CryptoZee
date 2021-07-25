package com.samarth.cryptozee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.samarth.cryptozee.data.model.localStorage.database.LocalStorageDatabase
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.data.viewModelFactory.ViewModelFactorys
import com.samarth.cryptozee.data.viewmodel.MainViewModel
import com.samarth.cryptozee.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding
lateinit var viewModelShared: MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        createSharedViewModel()
        setUpBottomNavigation()

    }




    private fun setUpBottomNavigation() {
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
        val appConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favouriteFragment,
                R.id.walletFragment,
            )
        )
        setupActionBarWithNavController(navHostFragment.navController, appConfiguration)
    }


    private fun createSharedViewModel() {
        val gettingDataBaseReference = LocalStorageDatabase.getDatabase(this)
        val repository =
            Repository(gettingDataBaseReference.favouriteDao())
        viewModelShared =
            ViewModelProvider(this, ViewModelFactorys(repository)).get(MainViewModel::class.java)
    }

//    private fun setActionBarColor() {
//        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES) {
//            val actionBar = supportActionBar
//            val colorDrawable = ColorDrawable(Color.parseColor("#001434"))
//            actionBar?.setBackgroundDrawable(colorDrawable)
//        }
//    }

    // Waiting Loading manager
    companion object {
        fun startLoading() {
            binding.loadingAnimation.visibility = View.VISIBLE
        }

        fun stopLoading() {
            binding.loadingAnimation.visibility = View.GONE
        }
    }

}