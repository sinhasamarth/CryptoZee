package com.samarth.cryptozee.ui.dataFormatter

import com.samarth.cryptozee.data.model.localStorage.entities.WalletInfoEntity
import com.samarth.cryptozee.databinding.WalletFragmentBinding

object SetWalletData {
    fun setWalletValue(binding: WalletFragmentBinding, it: WalletInfoEntity?) {
        it?.let {
            binding.displayNameOFOwner.text = "Welcome  ${it.name}"
            binding.currentBalance.text = DataFormat.formatPrice(it.totalPortfolio)
        }
    }

}