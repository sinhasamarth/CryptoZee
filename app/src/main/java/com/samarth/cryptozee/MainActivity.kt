package com.samarth.cryptozee

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samarth.cryptozee.databinding.ActivityMainBinding
import android.graphics.Color

import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.samarth.cryptozee.data.offlineDatabase.database.LocalStorageDatabase
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.data.viewModelFactory.ViewModelFactorys
import com.samarth.cryptozee.data.viewmodel.MainViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


private lateinit var binding: ActivityMainBinding
lateinit var viewModelShared: MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startLoading()
        createSharedViewModel()
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
//        val appConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.newsFragment,R.id.favouriteFragment,R.id.walletFragment , R.id.alertFragment))
//        setupActionBarWithNavController(navHostFragment.navController,appConfiguration)
    }


    private fun createSharedViewModel() {
        val gettingDataBaseReference = LocalStorageDatabase.getDatabase(this)
        val repository = Repository(
            gettingDataBaseReference.favouriteDao(),
            gettingDataBaseReference.walletInfoDao(),
            gettingDataBaseReference.transactionDao(),
            gettingDataBaseReference.walletCoinDao()
        )
        viewModelShared =
            ViewModelProvider(this, ViewModelFactorys(repository)).get(MainViewModel::class.java)
    }

    private fun setActionBarColor() {
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES) {
            val actionBar = supportActionBar
            val colorDrawable = ColorDrawable(Color.parseColor("#001434"))
            actionBar?.setBackgroundDrawable(colorDrawable)
        }
    }

    companion object {

        fun startLoading() {
            binding.loadingScreen.visibility = View.VISIBLE
            binding.finalScreen.visibility = View.INVISIBLE
        }

        fun stopLoading() {
            binding.loadingScreen.visibility = View.INVISIBLE
            binding.finalScreen.visibility = View.VISIBLE

        }
    }
}