package com.samarth.cryptozee.ui.base.fragments.alert

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.samarth.cryptozee.R
import com.samarth.cryptozee.databinding.AlertFragmentBinding

private lateinit var binding:AlertFragmentBinding
class AlertFragment : Fragment(R.layout.alert_fragment){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlertFragmentBinding.inflate(layoutInflater)
    }
}