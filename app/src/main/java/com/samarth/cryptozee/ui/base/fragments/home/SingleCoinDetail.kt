package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        val coinId = viewModelShared.coinIDForSharing!!
        var coinDetailResponse: SingleCoinDetailResponse? = null
        viewModelShared.getSingleCoinDetail(coinId)
        viewModelShared.singleCoinResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData.setAllTextDataToView(
                response,
                binding,
            )
            coinDetailResponse = response
        })
        viewModelShared.getCoinChart(coinId)
        viewModelShared.singleCoinChartResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData.setAllChartsToView(response, binding, coinDetailResponse)
        })

        binding.favtoggleButton.setOnClickListener {
            val element = FavouriteEntity(
                coinDetailResponse!!.id,
                coinDetailResponse!!.name,
                coinDetailResponse!!.marketData.current_price.usd.toString(),
                coinDetailResponse!!.imageLink.url,
                coinDetailResponse!!.marketData.priceChangePercentage24h.toString()
            )

            if (binding.favtoggleButton.tag != "ON") {
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                viewModelShared.addToFavourites(element)
                binding.favtoggleButton.tag = "ON"
            } else {
                binding.favtoggleButton.tag = "OFF"
                viewModelShared.removeCoinFromFavourite(element)
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        viewModelShared.allFavouriteCoin.value?.forEach {
            if (it.coinId == coinId) {
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.favtoggleButton.tag = "ON"
            }
        }


        //Buy Button
        //Getting The Live Usable Value
        var walletInfo: WalletInfoEntity? = null

        viewModelShared.getWalletInfo()

        viewModelShared.walletInfo.observe(viewLifecycleOwner, {
            walletInfo = it
        })

        //

        binding.buyButton.setOnClickListener {

            if (checkWalletCreated(walletInfo)) {
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
                balanceText.setText("Balance - " + DataFormat.formatPrice(walletInfo!!.usableMoney))


                //Quantity Text Changer Listener
                quantityText.doOnTextChanged { text, _, _, _ ->
                    if (!text.isNullOrBlank() && quantityText.hasFocus()) {
                        val value = text.toString().toDouble()
                            .times(coinDetailResponse!!.marketData.current_price.usd)
                        view.findViewById<TextInputEditText>(R.id.PriceText)
                            .setText(value.toString())
                    } else if (text.isNullOrBlank()) {
                        view.findViewById<TextInputEditText>(R.id.PriceText)
                            .setText("0")
                    }
                }

                // Live text Changer Listener

                priceText.doOnTextChanged { text, _, _, _ ->
                    if (!text.isNullOrBlank() && priceText.hasFocus()) {

                        val value = text.toString().toDouble()
                            .div(coinDetailResponse!!.marketData.current_price.usd)

                        quantityText.setText(value.toString())

                    } else if (text.isNullOrBlank()) {
                        view.findViewById<TextInputEditText>(R.id.QuantityText)
                            .setText(getString(R.string.zeroValue))
                    }
                }


                //Cancel Button

                cancelButton.setOnClickListener {
                    box.dismiss()
                }


                //Buy Button function

                buyButton.setOnClickListener {

                    //Checking wallet is Created Or Not

                    if (walletInfo!!.usableMoney.toDouble() >= priceText.text.toString()
                            .toDouble() && !quantityText.text.isNullOrBlank() && !priceText.text.isNullOrBlank()
                    ) {
                        coinDetailResponse?.let {

                            val transaction = TransactionEntity(
                                0,
                                it.name,
                                quantityText.text.toString().toDouble(),
                                SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date()),
                                it.id,
                                it.symbol,
                                true,
                                priceText.text.toString().toDouble()
                            )

                            val addToWallet = WalletCoinEntity(
                                transaction.id,
                                transaction.coinName,
                                transaction.coinId,
                                transaction.coinSymbol,
                                it.marketData.current_price.usd,
                                quantityText.text.toString().toDouble(),
                                transaction.dateOfTransaction
                            )

                            val remainingPrice =
                                walletInfo!!.usableMoney.toDouble() - priceText.text.toString()
                                    .toDouble()

                            // Updating Wallet

                            viewModelShared.upDateWallet(remainingPrice.toString())

                            //Adding Coin To Wallet

                            viewModelShared.addCoinToWallet(addToWallet)

                            //Adding Transaction
                            viewModelShared.addToTransaction(transaction)

                            //Update wallet
                            viewModelShared.getWalletInfo()
                            box.dismiss()

                        }
                    }
                    Toast.makeText(requireContext(), "INVALID", Toast.LENGTH_LONG).show()
                }
            }
        }

/*            TODO:  Check wallet is created or not if not move to wallet fragement
//         TODO: PENDINNG FETAURES -
            2 ) Live Update of Wallet
            4 ) Check the live balance using Live Data
            5 ) Fix Bug of RecylerView on Home
            6 ) Wallet Pie Chart and More Details
            7 ) Transaction Fragement
            8) Wallet Fragement in Detail
            9 ) Live Upate of Price if possible
            10 ) Loading bar
//
*/
        return binding.root
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