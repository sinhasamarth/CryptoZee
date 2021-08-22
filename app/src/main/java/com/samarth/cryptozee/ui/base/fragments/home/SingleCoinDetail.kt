package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.FavouriteEntity
import com.samarth.cryptozee.data.model.localStorage.TransactionEntity
import com.samarth.cryptozee.data.model.localStorage.WalletCoinEntity
import com.samarth.cryptozee.data.model.localStorage.WalletInfoEntity
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import com.samarth.cryptozee.ui.dataFormatter.DataFormat
import com.samarth.cryptozee.ui.dataFormatter.SetSingleCoinData
import com.samarth.cryptozee.ui.dataFormatter.SetWalletData.pinkSnackBar
import com.samarth.cryptozee.viewModelShared
import java.text.SimpleDateFormat
import java.util.*

private lateinit var binding: SingleCoinDetailFragmentBinding

class SingleCoinDetail : Fragment() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // View Binding
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)

        // Getting Coin ID
        val coinId = viewModelShared.coinIDForSharing!!

        // Getting Wallet Coin Details
        viewModelShared.getSingleCoin(coinId)

        // API Response Variable
        var coinDetailResponse: SingleCoinDetailResponse? = null

//        //Progress Bar Start
        binding.loadingScreen.visibility = View.VISIBLE
        binding.finalScreen.visibility = View.INVISIBLE

        // API Calling
        viewModelShared.getSingleCoinDetail(coinId)


        // Getting Live Data
        viewModelShared.singleCoinResponse.observe(viewLifecycleOwner, { response ->

            // Setting Value of Response To All the Text
            SetSingleCoinData.setAllTextDataToView(
                response,
                binding,
            )
            // Caching Response
            coinDetailResponse = response
        })

        // API Call for the Chart
        viewModelShared.getCoinChart(coinId)


        // Getting Live Data Of Chart
        viewModelShared.singleCoinChartResponse.observe(viewLifecycleOwner, { response ->
            // Setting chart To View
            response?.let {
                SetSingleCoinData.setAllChartsToView(response, binding, coinDetailResponse)
                viewModelShared.singleCoinChartResponse.postValue(null)
            }

        })


        // Checking Already Favourite
        viewModelShared.allFavouriteCoin.value?.forEach {
            if (it.coinId == coinId) {
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.favtoggleButton.tag = "ON"
            }
        }

        // Handling the Favourite Button
        binding.favtoggleButton.setOnClickListener {

            // Favourite Listener
            favouriteListener(coinDetailResponse!!)

        }


        //Buy Button
        //Getting The Live walletInfo
        var walletInfo: WalletInfoEntity? = null

        // Database Call
        viewModelShared.getWalletInfo()

        viewModelShared.walletInfo.observe(viewLifecycleOwner, {
            walletInfo = it
        })


        // Buy Button
        binding.buyButton.setOnClickListener {

            // Checking Wallet Created
            if (checkWalletCreated(walletInfo)) {
                //Buy Box
                buyCoinBox(walletInfo!!, coinDetailResponse!!)
            }
        }



        // Sell Button Listener
        binding.sellButton.setOnClickListener {

            // Checking Wallet
            if (checkWalletCreated(walletInfo)) {

                //Getting Coin Detail From Database
                viewModelShared.getSingleCoin(coinDetailResponse!!.id)
                // Showing Dialog Box
                sellCoinBox(walletInfo!!, coinDetailResponse!!)
            }
        }

        return binding.root
    }


    // Favourite Listener
    private fun favouriteListener(data: SingleCoinDetailResponse) {
        // Creating Favourite Entity
        val element = FavouriteEntity(
            data.id,
            data.name,
            data.marketData.current_price.usd.toString(),
            data.imageLink.url,
            data.marketData.priceChangePercentage24h.toString()
        )

        if (binding.favtoggleButton.tag != "ON") {
            // Changing  the Image To Filled
            binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            //  Adding To DB
            viewModelShared.addToFavourites(element)
            // Changing the TAG to ON
            binding.favtoggleButton.tag = "ON"
        } else {
            // Deleting From Database
            viewModelShared.removeCoinFromFavourite(element)
            //Changing the Image To Border
            binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            // Setting TAG to OFF
            binding.favtoggleButton.tag = "OFF"
        }
    }


    // Sell Listener
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun sellCoinBox(
        walletInfo: WalletInfoEntity,
        coinDetailResponse: SingleCoinDetailResponse,
    ) {

        //Getting Coin Detail From Database
        viewModelShared.getSingleCoin(viewModelShared.coinIDForSharing!!)
        var walletcoinResponse: WalletCoinEntity? = null

        viewModelShared.walletSingleCoin.observe(viewLifecycleOwner, {
            if (it == null)
                pinkSnackBar(binding.root, "Please Buy Coin First", requireContext())
            else {
                walletcoinResponse = it
            }
        })

        //Getting View
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.sell_box_layout, null, false)

        //Getting View elements
        val priceText = view.findViewById<TextInputEditText>(R.id.SellPriceText)
        val quantityText = view.findViewById<TextInputEditText>(R.id.SellQuantityText)
        val availableQuantity = view.findViewById<TextView>(R.id.availableQuantity)
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val sellCoinButton = view.findViewById<Button>(R.id.SellCoin)

        walletcoinResponse?.let { data ->
//            Log.d("HII", walletcoinResponse.toString())
            availableQuantity.text = "Available - " + DataFormat.formatQuantity(data.quantity)
            //Setting CustomView On Material Box
            val materialBox = MaterialAlertDialogBuilder(requireContext())

            //Setting Background Color Transparent and showing it
            val box =
                materialBox.setView(view).setBackground(ColorDrawable(Color.TRANSPARENT)).show()


            // Quantity Text Changer Listener
            quantityText.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank() && quantityText.hasFocus()) {
                    val value = "0$text".toDouble()
                        .times(coinDetailResponse.marketData.current_price.usd)
                    priceText
                        .setText(value.toString())
                } else if (text.isNullOrBlank()) {
                    priceText
                        .setText("0")
                }
            }
            // Live text Changer Listener
            priceText.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank() && priceText.hasFocus()) {

                    val value = "0$text".toDouble()
                        .div(coinDetailResponse.marketData.current_price.usd)

                    quantityText.setText(value.toString())

                } else if (text.isNullOrBlank()) {
                    quantityText.setText(getString(R.string.zeroValue))
                }
            }


            //Cancel Button

            cancelButton.setOnClickListener {
                box.dismiss()
            }


            //Sell Button function

            sellCoinButton.setOnClickListener {

                //Checking wallet is Created Or Not

                if (quantityText.text.toString().toDouble() <= data.quantity
                    && !quantityText.text.isNullOrBlank() && !priceText.text.isNullOrBlank()
                ) {
                    coinDetailResponse.let {

                        val transaction = TransactionEntity(
                            0,
                            it.name,
                            quantityText.text.toString().toDouble(),
                            SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date()),
                            it.id,
                            it.symbol,
                            false,
                            priceText.text.toString().toDouble()
                        )

                        val updatedPrice =
                            walletInfo.usableMoney.toDouble() + priceText.text.toString()
                                .toDouble()

                        val updatedQuantity =
                            data.quantity - quantityText.text.toString().toDouble()

                        // Updating Wallet

                        viewModelShared.upDateWallet(updatedPrice.toString())

                        //Updating Coin In Wallet

                        viewModelShared.updateCoinQuantity(updatedQuantity, data)

                        //Adding Transaction
                        viewModelShared.addToTransaction(transaction)

                        //Update wallet
                        viewModelShared.getWalletInfo()

                        box.dismiss()
                        Toast.makeText(requireContext(), "Successful", Toast.LENGTH_LONG).show()
                    }
                }


            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun buyCoinBox(
        walletInfo: WalletInfoEntity,
        coinDetailResponse: SingleCoinDetailResponse
    ) {
        //Getting View
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.buy_box_layout, null, false)

        //Getting View elements
        val priceText = view.findViewById<TextInputEditText>(R.id.PriceText)
        val quantityText = view.findViewById<TextInputEditText>(R.id.QuantityText)
        val balanceText = view.findViewById<TextView>(R.id.currentBalance)
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val buyButton = view.findViewById<Button>(R.id.buyCoin)

        //Setting CustomView On Material Box
        val materialBox = MaterialAlertDialogBuilder(requireContext())

        //Setting Background Color Transparent and showing it
        val box =
            materialBox.setView(view).setBackground(ColorDrawable(Color.TRANSPARENT)).show()
        balanceText.text = "Balance - " + DataFormat.formatPrice(walletInfo.usableMoney)


        //Quantity Text Changer Listener
        quantityText.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrBlank() && quantityText.hasFocus()) {
                val value = "0$text".toDouble()
                    .times(coinDetailResponse.marketData.current_price.usd)
                priceText.setText(value.toString())
            } else if (text.isNullOrBlank()) {
                priceText.setText("0")
            }
        }

        // Live text Changer Listener

        priceText.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrBlank() && priceText.hasFocus()) {

                val value = "0$text".toDouble()
                    .div(coinDetailResponse.marketData.current_price.usd)

                quantityText.setText(value.toString())

            } else if (text.isNullOrBlank()) {
                quantityText.setText(getString(R.string.zeroValue))
            }
        }


        //Cancel Button

        cancelButton.setOnClickListener {
            box.dismiss()
        }


        //Buy Button function

        buyButton.setOnClickListener {

            //Checking wallet is Created Or Not

            if (walletInfo.usableMoney.toDouble() >= priceText.text.toString()
                    .toDouble() && !quantityText.text.isNullOrBlank() && !priceText.text.isNullOrBlank()
            ) {
                coinDetailResponse.let {

                    val transaction = TransactionEntity(
                        0,
                        it.name,
                        DataFormat.formatQuantity(quantityText.text.toString().toDouble()).toDouble(),
                        SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date()),
                        it.id,
                        it.symbol,
                        true,
                        priceText.text.toString().toDouble()
                    )

                    val addToWallet = WalletCoinEntity(
                        transaction.coinId,
                        transaction.coinName,
                        transaction.coinSymbol,
                        it.marketData.current_price.usd,
                        quantityText.text.toString().toDouble(),
                        transaction.dateOfTransaction
                    )

                    val remainingPrice =
                        walletInfo.usableMoney.toDouble() - priceText.text.toString()
                            .toDouble()

                    // Updating Wallet

                    viewModelShared.upDateWallet(remainingPrice.toString())

                    //Adding Coin To Wallet

                    viewModelShared.addWalletSingleCoin(addToWallet)

                    //Adding Transaction
                    viewModelShared.addToTransaction(transaction)

                    //Update wallet
                    viewModelShared.getWalletInfo()


                    viewModelShared.getSingleCoinDetail(coinDetailResponse.id)
                    box.dismiss()
                    Toast.makeText(requireContext(), "Successful", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun checkWalletCreated(walletInfo: WalletInfoEntity?): Boolean {
        if (walletInfo == null) {
            // Shifting Screen to Wallet
            Toast.makeText(requireContext(), "Create Wallet First", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_singleCoinDetail_to_walletFragment)
            return false
        }
        return true
    }

}