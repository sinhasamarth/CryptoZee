package com.samarth.cryptozee.ui.base.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.NewsFragmentBinding

private lateinit var binding: NewsFragmentBinding
 class NewsFragment : Fragment(R.layout.alert_fragment){
     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding = NewsFragmentBinding.inflate(layoutInflater)
         return binding.root
     }
 }