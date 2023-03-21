package com.davidvelez.mercadolibrecostsimulator.ui.costcalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CostCalculatorViewModel : ViewModel() {

    private val icaValue = 1.5f
    private val retValue = 0.414f
    private val limitFreePrice = 90000

    val fixedCost: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val calculatePrice: MutableLiveData<Float> by lazy {
        MutableLiveData<Float>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val errorProductCost: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun calcularPrecioVenta(
        productName: String,
        productCost: String,
        shippingCost: String,
        desiredUtility: String,
        checkButton: Boolean
    ) {
        if (productCost.isNotEmpty() && productCost.toInt() > 0) {
            calcularPrecioFijo(productCost, limitFreePrice)
            //costCalculatorBinding.fixedcostEditText.setText("$ $fixedCost")
            validarCampos(
                productName,
                productCost,
                shippingCost,
                desiredUtility,
                fixedCost,
                retValue,
                icaValue,
                checkButton
            )
        } else errorProductCost.postValue("String")

    }

    private fun calcularPrecioFijo(productCost: String, limitFreePrice: Int) {

        if (productCost.toInt() >= limitFreePrice) fixedCost.value = 0
        else fixedCost.value = 2100

    }

    private fun validarCampos(
        productName: String,
        productCost: String,
        shippingCost: String,
        desiredUtility: String,
        fixedCost: MutableLiveData<Int>,
        retValue: Float,
        icaValue: Float,
        checkButton: Boolean
    ) {
        if (productName.isNotEmpty() &&
            productCost.isNotEmpty() &&
            shippingCost.isNotEmpty() &&
            desiredUtility.isNotEmpty()
        ) {

            val sumAllCosts = sumarCostos(productCost, shippingCost, desiredUtility)
            val salesComission = calcularComision(checkButton)
            calculatePrice.value = calcularPrecio(sumAllCosts, salesComission)
        } else errorMessage.postValue("Up")

    }

    private fun sumarCostos(
        productCost: String,
        shippingCost: String,
        desiredUtility: String
    ): Float {

        return productCost.toFloat() + shippingCost.toFloat() + (fixedCost.value?.toFloat()
            ?: 0.0f) + desiredUtility.toFloat()

    }

    private fun calcularComision(checkButton: Boolean): Float {
        return if (checkButton) 16.0f
        else 20.0f

    }

    private fun calcularPrecio(sumAllCosts: Float, salesComission: Float): Float {
        return sumAllCosts / (1 - ((salesComission + retValue + icaValue) / 100))
    }
}