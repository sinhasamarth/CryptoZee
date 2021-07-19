package com.samarth.cryptozee.data.model.api.singleCoinResponse


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("blockchain_site")
    val blockchainSite: List<String>,
    @SerializedName("homepage")
    val homepage: List<String>,
    @SerializedName("official_forum_url")
    val officialForumUrl: List<String>
)