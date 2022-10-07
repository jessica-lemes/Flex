package com.aplicativo.flex

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.aplicativo.flex.databinding.ActivityMainBinding
import com.aplicativo.flex.databinding.ActivityMainBinding.inflate


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun calcularPreco(view: View){

        val precoAlcool = binding.editPrecoAlcool.text.toString()
        val precoGasolina = binding.editPrecoGasolina.text.toString()

        val validaCampos = validarCampos(precoAlcool, precoGasolina)

        if(validaCampos){
            calcularMelhorPreco(precoAlcool, precoGasolina)
        }else{
            binding.cardViewResultado.visibility = View.VISIBLE
            binding.textResultado.text = "Preencha os preços primeiro"
        }

    }
    fun validarCampos(precoAlcool : String, precoGasolina : String): Boolean {
        var camposValidados = true

        if(precoAlcool.isEmpty()){
            camposValidados = false

        }else if(precoGasolina.isEmpty()){
            camposValidados = false
        }
        return camposValidados
    }

    fun calcularMelhorPreco(precoAlcool : String, precoGasolina : String) {
        //Converter preços para Double
        val valorAlcool = precoAlcool.toDouble()
        val valorGasolina = precoGasolina.toDouble()

        /*Calculo: (precoAlcool / precoGasolina)
        Se o resultado >= 0.7 melhor utilizar gasolina
        Senao melhor utilizar alcool
         */

        val resultadoPreco = valorAlcool / valorGasolina
        val resultadoPorcentagem = resultadoPreco * 100
        val resultadoPorcentagemInt = resultadoPorcentagem.toInt()


        if(resultadoPreco >= 0.7){
            binding.cardViewResultado.visibility = View.VISIBLE
            binding.textResultado.text = "Melhor utilizar Gasolina. \n Paridade: " + resultadoPorcentagemInt + "%"
        }else{
            binding.cardViewResultado.visibility = View.VISIBLE
            binding.textResultado.text = "Melhor utilizar Álcool.\n Paridade: " + resultadoPorcentagemInt  + "%"
        }


    }


}