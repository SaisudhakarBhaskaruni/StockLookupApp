package com.example.leelasai.stocklookupapp

import StockViewModel
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.leelasai.stocklookupapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var etStockSymbol: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvCompanyName: TextView
    private lateinit var tvStockPrice: TextView
    private lateinit var tvPercentageChange: TextView

    private val stockViewModel: StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        etStockSymbol = findViewById(R.id.et_stock_symbol)
        btnSearch = findViewById(R.id.btn_search)
        tvCompanyName = findViewById(R.id.tv_company_name)
        tvStockPrice = findViewById(R.id.tv_stock_price)
        tvPercentageChange = findViewById(R.id.tv_percentage_change)

        btnSearch.setOnClickListener {
            val symbol = etStockSymbol.text.toString().trim()
            if (symbol.isNotEmpty()) {
                stockViewModel.fetchStockData(symbol)
                resetVisibility()  // Reset UI before search
            } else {
                Toast.makeText(this, "Please enter a valid stock symbol", Toast.LENGTH_SHORT).show()
                resetVisibility()  // Reset UI even if input is invalid
            }
        }


        // Observing ViewModel LiveData
        stockViewModel.companyName.observe(this, Observer { companyName ->
            tvCompanyName.text = "Company: $companyName"
            tvCompanyName.visibility = View.VISIBLE
        })

        stockViewModel.stockPrice.observe(this, Observer { stockPrice ->
            tvStockPrice.text = "Price: $stockPrice"
            tvStockPrice.visibility = View.VISIBLE
        })

        stockViewModel.percentageChange.observe(this, Observer { percentageChange ->
            tvPercentageChange.text = "Change: $percentageChange%"
            tvPercentageChange.visibility = View.VISIBLE
        })

        stockViewModel.errorMessage.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            resetVisibility() // Reset the UI on error
        })
    }

    private fun resetVisibility() {
        tvCompanyName.visibility = View.GONE
        tvStockPrice.visibility = View.GONE
        tvPercentageChange.visibility = View.GONE
    }
}
