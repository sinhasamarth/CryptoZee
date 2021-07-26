package com.samarth.cryptozee.ui.base.fragments.wallet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.entities.WalletInfoEntity
import com.samarth.cryptozee.databinding.WalletFragmentBinding
import com.samarth.cryptozee.ui.dataFormatter.SetWalletData
import com.samarth.cryptozee.viewModelShared
import java.text.SimpleDateFormat

import java.util.*


private lateinit var binding: WalletFragmentBinding

class WalletFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WalletFragmentBinding.inflate(layoutInflater)
        viewModelShared.getWalletInfo()

        viewModelShared.walletInfo.observe(viewLifecycleOwner,{

            if (viewModelShared.walletInfo.value == null)
                showWelcomeBox()
            else {
                binding.WalletIntroFrameLayout.visibility = View.GONE
                binding.walletDetailsLayout.visibility = View.VISIBLE
                SetWalletData.setWalletValue(binding , it)
            }
        })

        return binding.root
    }

    @SuppressLint("InflateParams")
    private fun showWelcomeBox() {
        val customView = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.welcome_material_box_wallet, null, false)
        val checkBox = customView.findViewById<CheckBox>(R.id.checkboxTermsAndCondition)
        val name: TextInputEditText = customView.findViewById(R.id.userName)
        val button = customView.findViewById<Button>(R.id.startButton)
        binding.walletDetailsLayout.visibility = View.GONE
        name.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                if (checkBox.isChecked) {
                    button.isEnabled = true
                }
            }
            else
                button.isEnabled = false
        }
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (name.text!!.isNotEmpty()) {
                    button.isEnabled = true
                }
            } else
                button.isEnabled = false

        }


        binding.WalletIntroFrameLayout.addView(customView)
        button.setOnClickListener {
            Snackbar.make(binding.root, name.text.toString(), Snackbar.LENGTH_LONG).show()
            binding.WalletIntroFrameLayout.visibility = View.GONE
            viewModelShared.createWallet(
                WalletInfoEntity(
                    0,
                    name.text.toString(),
                    "10000",
                    "10000",
                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())
                )
            )
            binding.walletDetailsLayout.visibility = View.VISIBLE
        }
    }


}