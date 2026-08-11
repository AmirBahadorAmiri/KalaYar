package com.amirbahadoramiri.kalayar.presentation.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amirbahadoramiri.kalayar.domain.models.PriceItem
import com.amirbahadoramiri.kalayar.domain.models.TasnimResponse
import com.amirbahadoramiri.kalayar.tools.network.Network
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    val pricesLiveData = MutableLiveData<List<PriceItem>>()
    val errorLiveData = MutableLiveData<String>()

    fun fetchPrices() {
        Network.getNetworkInterface().latestPrices.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val bodyString = response.body()?.string()
                    if (bodyString != null) {
                        try {
                            // Tasnim returns a JSON string inside a JSON response sometimes, 
                            // or a double-quoted string.
                            var rawJson = bodyString.trim()
                            if (rawJson.startsWith("\"") && rawJson.endsWith("\"")) {
                                rawJson = rawJson.substring(1, rawJson.length - 1)
                                    .replace("\\\"", "\"")
                                    .replace("\\n", "")
                            }
                            
                            val tasnimData = Gson().fromJson(rawJson, TasnimResponse::class.java)
                            
                            val items = mutableListOf<PriceItem>()
                            val addedNames = mutableSetOf<String>()
                            
                            // Mappings for Tasnim Titles (Gold & Coins first, then common currencies)
                            val mappings = mapOf(
                                "geram18" to "طلای ۱۸ عیار",
                                "geram24" to "طلای ۲۴ عیار",
                                "ons" to "انس طلا",
                                "retail_sekee" to "سکه امامی",
                                "retail_sekeb" to "سکه بهار آزادی",
                                "retail_nim" to "نیم سکه",
                                "retail_rob" to "ربع سکه",
                                "retail_gerami" to "سکه گرمی",
                                "price_usd_rl" to "دلار آمریکا",
                                "price_dollar_rl" to "دلار آمریکا",
                                "price_usd" to "دلار آمریکا",
                                "price_eur_rl" to "یورو",
                                "price_eur" to "یورو",
                                "price_gbp" to "پوند انگلیس",
                                "price_aed" to "درهم امارات",
                                "price_try" to "لیر ترکیه",
                                "price_cad" to "دلار کانادا",
                                "price_aud" to "دلار استرالیا",
                                "price_cny" to "یوان چین"
                            )

                            // Iterate through mappings to ensure defined order and handle priority/duplicates
                            mappings.keys.forEach { key ->
                                val name = mappings[key] ?: ""
                                if (!addedNames.contains(name)) {
                                    val currency = tasnimData.currency.find { it.title == key }
                                    if (currency != null) {
                                        val price = currency.p
                                        val direction = if (currency.dt == "low") "-" else "+"
                                        val changeText = "$direction${currency.d} (${currency.dp}%)"
                                        val isPositive = currency.dt != "low"
                                        
                                        items.add(PriceItem(key, name, price, changeText, isPositive))
                                        addedNames.add(name)
                                    }
                                }
                            }
                            
                            pricesLiveData.postValue(items)
                            
                        } catch (e: Exception) {
                            errorLiveData.postValue(e.message)
                        }
                    }
                } else {
                    errorLiveData.postValue("response not successful")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorLiveData.postValue(t.message ?: "خطای شبکه")
            }
        })
    }
}
