package com.davidvelez.mercadolibrecostsimulator.ui.costcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.davidvelez.mercadolibrecostsimulator.databinding.ActivityCostCalculatorBinding
import kotlin.math.round

class CostCalculator : AppCompatActivity() {

    private lateinit var costCalculatorBinding: ActivityCostCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        costCalculatorBinding = ActivityCostCalculatorBinding.inflate(layoutInflater)
        val view = costCalculatorBinding.root
        setContentView(view)


        val icaValue = 1.5f
        val retValue = 0.414f
        costCalculatorBinding.icaEditText.setText(icaValue.toString())
        costCalculatorBinding.retEditText.setText(retValue.toString())
        costCalculatorBinding.fixedcostEditText.setText(" ")
        costCalculatorBinding.sellingPriceEditText.setText(" ")
        costCalculatorBinding.icaEditText.isEnabled = false
        costCalculatorBinding.retEditText.isEnabled = false
        costCalculatorBinding.fixedcostEditText.isEnabled = false
        costCalculatorBinding.sellingPriceEditText.isEnabled = false


        costCalculatorBinding.calculateButton.setOnClickListener {
            val productName = costCalculatorBinding.productnameEditText.text.toString()
            val productCost = costCalculatorBinding.productcostEditText.text.toString()
            val shippingCost = costCalculatorBinding.shippingcostEditText.text.toString()
            val desiredUtility = costCalculatorBinding.desiredutilityEditText.text.toString()
            val limitFreePrice = 90000
            var fixedCost: Int

            if (productCost.isNotEmpty() && productCost.toInt() > 0) {

                if (productCost.toInt() >= limitFreePrice) fixedCost = 0
                else fixedCost = 2100
                costCalculatorBinding.fixedcostEditText.setText("$ $fixedCost")
                if (productName.isNotEmpty() &&
                    productCost.isNotEmpty() &&
                    shippingCost.isNotEmpty() &&
                    desiredUtility.isNotEmpty()
                ) {

                    val sumAllCosts =
                        productCost.toInt() + shippingCost.toInt() + fixedCost + desiredUtility.toInt()
                    val salesComission = if (costCalculatorBinding.clasic16RadioButton.isChecked) 16
                    else 20

                    val calculatePrice =
                        sumAllCosts / (1 - ((salesComission + retValue + icaValue) / 100))
                    costCalculatorBinding.sellingPriceEditText.setText("$ " + (round(calculatePrice * 100) / 100).toString())
                } else {

                    Toast.makeText(
                        applicationContext,
                        "Invalid value. Please fill in the required fields",
                        Toast.LENGTH_LONG
                    ).show()

                    costCalculatorBinding.sellingPriceEditText.setText("$ 0")

                }

            } else {
                costCalculatorBinding.productcostEditText.error =
                    "Invalid Value. Product cost must be greater than 0"
                costCalculatorBinding.sellingPriceEditText.setText("$ 0")

            }


        }
    }
}