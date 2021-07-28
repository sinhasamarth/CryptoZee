package com.samarth.cryptozee.ui.dataFormatter

import com.samarth.cryptozee.data.model.localStorage.WalletInfoEntity
import com.samarth.cryptozee.databinding.WalletFragmentBinding

object SetWalletData {
    fun setWalletDeatils (binding: WalletFragmentBinding, walletInfoEntity: WalletInfoEntity) {
            binding.displayNameOFOwner.text = "Welcome  ${walletInfoEntity.name}"
            binding.currentBalance.text = DataFormat.formatPrice(walletInfoEntity.usableMoney)

    }

}