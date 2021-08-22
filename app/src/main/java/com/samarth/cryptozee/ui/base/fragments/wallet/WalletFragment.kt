package com.samarth.cryptozee.ui.base.fragments.wallet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.WalletInfoEntity
import com.samarth.cryptozee.databinding.WalletFragmentBinding
import com.samarth.cryptozee.ui.adapters.WalletCoinRecyclerViewAdapter
import com.samarth.cryptozee.ui.dataFormatter.SetWalletData
import com.samarth.cryptozee.ui.listeners.SingleCoinItemClickListeners
import com.samarth.cryptozee.viewModelShared
import java.text.SimpleDateFormat

import java.util.*


private lateinit var binding: WalletFragmentBinding

class WalletFragment : Fragment(), SingleCoinItemClickListeners {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // View Binding
        binding = WalletFragmentBinding.inflate(layoutInflater)

        // Tool Bar
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = "Market"


        // Show Coins in RecyclerView
        viewModelShared.getAllWalletCoin()

        // Getting Wallet Details
        viewModelShared.getWalletInfo()

        // Checking the wallet is Created Or Not
        viewModelShared.walletInfo.observe(viewLifecycleOwner, {

            if (viewModelShared.walletInfo.value == null)
            // Wallet Create Box
                showWelcomeBox()
            else
            // Show Wallet Details
                showWalletDetails(it)
        })

        return binding.root
    }

    private fun showWalletDetails(walletInfoEntity: WalletInfoEntity) {

        // Setting Views
        binding.WalletIntroFrameLayout.visibility = View.GONE
        binding.walletDetailsLayout.visibility = View.VISIBLE

        // Setting WalletData
        SetWalletData.setWalletDeatils(binding, walletInfoEntity)

        // Setting Layout Manager
        binding.coinRecyclerView.layoutManager = LinearLayoutManager(context)

        // Setting Adapter
        viewModelShared.allWalletCoin.observe(viewLifecycleOwner, {

            // Handling When Wallet is Empty
                if(it.isNullOrEmpty()){
                    binding.nothingUi.visibility = View.VISIBLE
                    binding.WalletsCoinDetails.visibility = View.GONE
                }
                else{
                    binding.nothingUi.visibility = View.GONE
                    binding.WalletsCoinDetails.visibility = View.VISIBLE
                    binding.coinRecyclerView.adapter = WalletCoinRecyclerViewAdapter(it, it.size, this)
                }
        })

        // Handling Buy Now Button

        binding.moveToHomeButton.setOnClickListener {
            findNavController().navigate(R.id.bottom_navigation_main)
        }
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
            } else
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
            val walletEntity = WalletInfoEntity(
                0,
                name.text.toString(),
                "10000",
                "10000",
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())
            )
            viewModelShared.createWallet( walletEntity)
            showWalletDetails(walletEntity)
            binding.walletDetailsLayout.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(position: Int) {
        // Setting argument
        // Replacing Fragment
        findNavController().navigate(R.id.action_walletFragment_to_singleCoinDetail)

    }
}


