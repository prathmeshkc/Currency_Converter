package com.example.currencyconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setupDropDownArrayAdapter()
        hideProgressBar()

        etFromAmt.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                tilFromAmt.error = "Please enter a number!"
            } else if (text.isNotEmpty()) {
                tilFromAmt.error = null
            }
        }

        btnConvert.setOnClickListener {

            val from = convertFullCurrencytoShort(actvFromCurrency.text)
            val to = convertFullCurrencytoShort(actvToCurrency.text)
            val query = "${from.trim()}_${to.trim()}"
            setupDropDownArrayAdapter()
            if (etFromAmt.text!!.isEmpty()) {
                tilFromAmt.error = "Please enter a number!"
            } else {
                tilFromAmt.error = null
                showProgressBar()
                fetchData(query)
                closeKeyBoard()
                Log.d(
                    "Query",
                    "https://free.currconv.com/api/v7/convert?q=${query.trim()}&compact=ultra&apiKey=ab8ebe0173aac42a9665"
                )

            }

        }

        btnInvert.setOnClickListener {

            val currentFrom = actvFromCurrency.text
            val currentTo = actvToCurrency.text
            val reversedQuery = "${convertFullCurrencytoShort(currentTo).trim()}_${
                convertFullCurrencytoShort(currentFrom).trim()
            }"
            actvFromCurrency.text = currentTo
            actvToCurrency.text = currentFrom
            Log.d("Variables", "NewFrom : ${actvFromCurrency.text}, NewTo : ${actvToCurrency.text}")
            setupDropDownArrayAdapter()
            if (etFromAmt.text!!.isEmpty()) {
                tilFromAmt.error = "Please enter a number!"
            } else {
                showProgressBar()
                fetchData(reversedQuery)
                closeKeyBoard()
                Log.d(
                    "Query",
                    "https://free.currconv.com/api/v7/convert?q=${reversedQuery.trim()}&compact=ultra&apiKey=ab8ebe0173aac42a9665"
                )
            }

        }


    }

    private fun fetchData(query: String) {

        val url =
            "https://free.currconv.com/api/v7/convert?q=${query.trim()}&compact=ultra&apiKey=ab8ebe0173aac42a9665"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val amount = etFromAmt.text.toString().toDouble()
                val result = String.format("%.3f", response.getDouble(query) * amount)
                val conversion =
                    "$amount ${convertFullCurrencytoShort(actvFromCurrency.text)} = $result ${
                        convertFullCurrencytoShort(actvToCurrency.text)
                    }"
                tvResult.text = conversion
                hideProgressBar()

            },
            { error ->
                Toast.makeText(
                    this,
                    "Error: ${error.networkResponse.statusCode}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun convertFullCurrencytoShort(fullcurrencyName: Editable?): String {
        when (fullcurrencyName.toString()) {
            "Dirham AED" -> return "AED"
            "Afghani AFN" -> return "AFN"
            "Lek ALL" -> return "ALL"
            "Dram AMD" -> return "AMD"
            "Netherlands Antilles Guilder ANG" -> return "ANG"
            "Angolan kwanza AOA" -> return "AOA"
            "Peso ARS" -> return "ARS"
            "Australian Dollars AUD" -> return "AUD"
            "Aruban florin AWG" -> return "AWG"
            "Manat AZN" -> return "AZN"
            "Bosnia-Herzegovina Convertible Marka BAM" -> return "BAM"
            "Barbadian Dollar BBD" -> return "BBD"
            "Taka BDT" -> return "BDT"
            "Lev BGN" -> return "BGN"
            "Bahraini Dinar BHD" -> return "BHD"
            "Burundi Franc BIF" -> return "BIF"
            "Bermudian Dollar BMD" -> return "BMD"
            "Bruneian Dollar BND" -> return "BND"
            "Boliviano BOB" -> return "BOB"
            "Brazil BRL" -> return "BRL"
            "Bahamian Dollar BSD" -> return "BSD"
            "Bhutan Ngultrum BTN" -> return "BTN"
            "Pula BWP" -> return "BWP"
            "Belarusian ruble BYN" -> return "BYN"
            "Belizean Dollar BZD" -> return "BZD"
            "Canadian Dollar CAD" -> return "CAD"
            "Congolese Frank CDF" -> return "CDF"
            "Swiss Franc CHF" -> return "CHF"
            "Chilean Peso CLP" -> return "CLP"
            "Yuan Renminbi CNY" -> return "CNY"
            "Colombian peso COP" -> return "COP"
            "Costa Rican Colon CRC" -> return "CRC"
            "Cuban Peso CUC" -> return "CUC"
            "Cuban Peso CUP" -> return "CUP"
            "Escudo CVE" -> return "CVE"
            "Koruna CZK" -> return "CZK"
            "Djiboutian Franc DJF" -> return "DJF"
            "Danish Krone DKK" -> return "DKK"
            "Dominican Peso DOP" -> return "DOP"
            "Algerian Dinar DZD" -> return "DZD"
            "Egyptian Pound EGP" -> return "EGP"
            "Eritrean nakfa ERN" -> return "ERN"
            "Ethiopian Birr ETB" -> return "ETB"
            "Euro EUR" -> return "EUR"
            "Fijian Dollar FJD" -> return "FJD"
            "Falkland Pound FKP" -> return "FKP"
            "Sterling GBP" -> return "GBP"
            "Lari GEL" -> return "GEL"
            "Guernsey pound GGP" -> return "GGP"
            "Ghana cedi GHS" -> return "GHS"
            "Gibraltar Pound GIP" -> return "GIP"
            "Dalasi GMD" -> return "GMD"
            "Guinean Franc GNF" -> return "GNF"
            "Quetzal GTQ" -> return "GTQ"
            "Guyanaese Dollar GYD" -> return "GYD"
            "Hong Kong Dollar HKD" -> return "HKD"
            "Lempira HNL" -> return "HNL"
            "Croatian Dinar HRK" -> return "HRK"
            "Gourde HTG" -> return "HTG"
            "Forint HUF" -> return "HUF"
            "Indonesian Rupiah IDR" -> return "IDR"
            "Shekel ILS" -> return "ILS"
            "Manx pound IMP" -> return "IMP"
            "Indian Rupee INR" -> return "INR"
            "Iraqi Dinar IQD" -> return "IQD"
            "Ethiopian Birr IRR" -> return "IRR"
            "Icelandic Krona ISK" -> return "ISK"
            "Jersey Pound JEP" -> return "JEP"
            "Jamaican Dollar JMD" -> return "JMD"
            "Jordanian Dinar JOD" -> return "JOD"
            "Japanese Yen JPY" -> return "JPY"
            "Kenyan Shilling KES" -> return "KES"
            "Kyrgyzstani som KGS" -> return "KGS"
            "Cambodian Riel KHR" -> return "KHR"
            "Comorian Franc KMF" -> return "KMF"
            "South Korean Won KRW" -> return "KRW"
            "Kuwaiti Dinar KWD" -> return "KWD"
            "Cayman Islands Dollar KYD" -> return "KYD"
            "Kazakhstani Tenge KZT" -> return "KZT"
            "Lao Kip LAK" -> return "LAK"
            "Lebanese Pound LBP" -> return "LBP"
            "Sri Lankan Rupee LKR" -> return "LKR"
            "Liberian Dollar LRD" -> return "LRD"
            "Lesotho Loti LSL" -> return "LSL"
            "Libyan Dinar LYD" -> return "LYD"
            "Moroccan Dirham MAD" -> return "MAD"
            "Moldovan Leu MDL" -> return "MDL"
            "Malagasy Ariary MGA" -> return "MGA"
            "Macedonian Denar MKD" -> return "MKD"
            "Myanmar Kyat MMK" -> return "MMK"
            "Mongolian Tögrög MNT" -> return "MNT"
            "Macanese Pataca MOP" -> return "MOP"
            "Mauritanian Ouguiya MRU" -> return "MRU"
            "Mauritian Rupee MUR" -> return "MUR"
            "Maldivian Rufiyaa MVR" -> return "MVR"
            "Malawian Kwacha MWK" -> return "MWK"
            "Mexican Peso MXN" -> return "MXN"
            "Malaysian Ringgit MYR" -> return "MYR"
            "Mozambican Metical MZN" -> return "MZN"
            "Namibian Dollar NAD" -> return "NAD"
            "Nigerian Naira NGN" -> return "NGN"
            "Nicaraguan Córdoba NIO" -> return "NIO"
            "Norwegian Krone NOK" -> return "NOK"
            "Nepalese Rupee NPR" -> return "NPR"
            "New Zealand Dollar NZD" -> return "NZD"
            "Omani Rial OMR" -> return "OMR"
            "Panamanian Balboa PAB" -> return "PAB"
            "Sol PEN" -> return "PEN"
            "Papua New Guinean Kina PGK" -> return "PGK"
            "Philippine Peso PHP" -> return "PHP"
            "Pakistani Rupee PKR" -> return "PKR"
            "Polish Złoty PLN" -> return "PLN"
            "Paraguayan Guaraní PYG" -> return "PYG"
            "Qatari Riyal QAR" -> return "QAR"
            "Romanian Leu RON" -> return "RON"
            "Serbian Dinar RSD" -> return "RSD"
            "Russian Ruble RUB" -> return "RUB"
            "Rwandan Franc RWF" -> return "RWF"
            "Saudi Riyal SAR" -> return "SAR"
            "Solomon Islands Dollar SBD" -> return "SBD"
            "Seychellois Rupee SCR" -> return "SCR"
            "Sudanese Pound SDG" -> return "SDG"
            "Swedish Krona SEK" -> return "SEK"
            "Singapore Dollar SGD" -> return "SGD"
            "Saint Helenian Pound SHP" -> return "SHP"
            "Sierra Leonean Leone SLL" -> return "SLL"
            "Somali Shilling SOS" -> return "SOS"
            "Surinamese Dollar SRD" -> return "SRD"
            "South Sudanese Pound SSP" -> return "SSP"
            "Sao Tomean Dobra STN" -> return "STN"
            "Salvadoran Colón SVC" -> return "SVC"
            "Syrian Pound SYP" -> return "SYP"
            "Swazi Lilangeni SZL" -> return "SZL"
            "Thai Baht THB" -> return "THB"
            "Tajikistani Somoni TJS" -> return "TJS"
            "Turkmenistani Manat TMT" -> return "TMT"
            "Tunisian Dinar TND" -> return "TND"
            "Tongan Paʻanga TOP" -> return "TOP"
            "Turkish Lira TRY" -> return "TRY"
            "Trinidad and Tobago Dollar TTD" -> return "TTD"
            "New Taiwan Dollar TWD" -> return "TWD"
            "Tanzanian Shilling TZS" -> return "TZS"
            "Ukrainian Hryvnia UAH" -> return "UAH"
            "Ugandan Shilling UGX" -> return "UGX"
            "United States Dollar USD" -> return "USD"
            "Uruguayan Peso UYU" -> return "UYU"
            "Uzbekistani Som UZS" -> return "UZS"
            "Venezuelan Bolívar VES" -> return "VES"
            "Vietnamese Dong VND" -> return "VND"
            "Vanuatu Vatu VUV" -> return "VUV"
            "Samoan Tala WST" -> return "WST"
            "Central African CFA Franc XAF" -> return "XAF"
            "Silver Ounce XAG" -> return "XAG"
            "Gold XAU" -> return "XAU"
            "East Caribbean Dollar XCD" -> return "XCD"
            "Special Drawing Rights XDR" -> return "XDR"
            "West African CFA Franc XOF" -> return "XOF"
            "CFP Franc XPF" -> return "XPF"
            "Yemeni Rial YER" -> return "YER"
            "South African Rand ZAR" -> return "ZAR"
            "Zambian Kwacha ZMW" -> return "ZMW"
            "Zimbabwean Dollar ZWL" -> return "ZWL"
            else -> return ""
        }
    }

    private fun setupDropDownArrayAdapter() {
        val currencies = resources.getStringArray(R.array.currency_codes)
        val dropDownArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, currencies)
        actvFromCurrency.setAdapter(dropDownArrayAdapter)
        actvToCurrency.setAdapter(dropDownArrayAdapter)
    }


    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}


