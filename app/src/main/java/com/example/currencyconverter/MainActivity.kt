package com.example.currencyconverter

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        btnConvert.setOnClickListener {
            //showProgressBar()
            val from = actvFromCurrency.text.toString()
            val to = actvToCurrency.text.toString()
            val query = "${from.trim()}_${to.trim()}"
            fetchData(query)
            closeKeyBoard()
            Log.d(
                "Query",
                "https://free.currconv.com/api/v7/convert?q=${query.trim()}&compact=ultra&apiKey=ab8ebe0173aac42a9665"
            )
            //hideProgressBar()

        }


    }

    private fun fetchData(query: String) {

        val url =
            "https://free.currconv.com/api/v7/convert?q=${query.trim()}&compact=ultra&apiKey=ab8ebe0173aac42a9665"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val amount = etFromAmt.text.toString().toDouble()
                val result = String.format("%.3f",response.getDouble(query) * amount)
                val conversion = "$amount ${actvFromCurrency.text} = $result ${actvToCurrency.text}"
                tvResult.text = conversion
            },
            { error ->
                Toast.makeText(this, "MainActivity: $error", Toast.LENGTH_SHORT).show()
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onResume() {
        super.onResume()
        val currencies = resources.getStringArray(R.array.currency_codes)
        val dropDownArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, currencies)
        actvFromCurrency.setAdapter(dropDownArrayAdapter)
        actvToCurrency.setAdapter(dropDownArrayAdapter)

    }

//
//    private fun hideProgressBar() {
//        progressBar.visibility = View.INVISIBLE
//    }
//
//    private fun showProgressBar() {
//        progressBar.visibility = View.VISIBLE
//    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}


