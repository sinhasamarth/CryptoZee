package com.samarth.cryptozee.ui.base.fragments.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.NewsFragmentBinding

private lateinit var binding: NewsFragmentBinding
 class NewsFragment : Fragment(R.layout.alert_fragment){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewsFragmentBinding.inflate(layoutInflater)
    }
}