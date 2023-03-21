package com.davidvelez.mercadolibrecostsimulator.ui.costcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.davidvelez.mercadolibrecostsimulator.databinding.ActivityCostCalculatorBinding
import kotlin.math.round

class CostCalculator : AppCompatActivity() {

    private lateinit var costCalculatorBinding: ActivityCostCalculatorBinding
    private lateinit var costCalculatorViewModel: CostCalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        costCalculatorBinding = ActivityCostCalculatorBinding.inflate(layoutInflater)
        val view = costCalculatorBinding.root
        setContentView(view)

        costCalculatorViewModel = ViewModelProvider(this)[CostCalculatorViewModel::class.java]

        val fixedCostObserver = Observer<Int> { fixedCost ->
            costCalculatorBinding.fixedcostEditText.setText("$ $fixedCost")
        }

        costCalculatorViewModel.fixedCost.observe(this, fixedCostObserver)

        val calculatePriceObserver = Observer<Float> { calculatePrice ->
            // costCalculatorBinding.sellingPriceEditText.text = sellingPrice.toString()
            costCalculatorBinding.sellingPriceEditText.setText("$ " + (round(calculatePrice * 100) / 100).toString())

        }
        costCalculatorViewModel.calculatePrice.observe(this, calculatePriceObserver)

        val errorMessageObserver = Observer<String> { errorMessage ->
            Toast.makeText(
                applicationContext,
                "Invalid value. Please fill in the required fields",
                Toast.LENGTH_SHORT
            ).show()

            costCalculatorBinding.sellingPriceEditText.setText("$ 0")

        }
        costCalculatorViewModel.errorMessage.observe(this, errorMessageObserver)

        val errorProductCostObserver = Observer<String> { errorProductCost ->
            costCalculatorBinding.productcostEditText.error =
                "Invalid Value. Product cost must be greater than 0"
            costCalculatorBinding.sellingPriceEditText.setText("$ 0")

        }
        costCalculatorViewModel.errorProductCost.observe(this, errorProductCostObserver)


        costCalculatorBinding.icaEditText.setText((1.5f).toString())
        costCalculatorBinding.retEditText.setText((0.414f).toString())

        costCalculatorBinding.calculateButton.setOnClickListener {
            costCalculatorViewModel.calcularPrecioVenta(
                costCalculatorBinding.productnameEditText.text.toString(),
                costCalculatorBinding.productcostEditText.text.toString(),
                costCalculatorBinding.shippingcostEditText.text.toString(),
                costCalculatorBinding.desiredutilityEditText.text.toString(),
                costCalculatorBinding.clasic16RadioButton.isChecked
            )
//
        }
    }

}