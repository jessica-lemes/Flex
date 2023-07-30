package com.aplicativo.flex

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.aplicativo.flex.databinding.ActivityMainBinding
import com.aplicativo.flex.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        binding = inflate(layoutInflater)
        setContentView(binding.root)

        //setando o modo Dark / Light utilizando Switch
        binding.btnDarkLightMode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.editPrecoGasolina.setOnEditorActionListener{ v, actionId, event ->
            when(actionId){
            EditorInfo.IME_ACTION_DONE -> {
                calcularPreco(binding.root)
                true}
            else -> false
            }
        }

        binding.editPrecoAlcool.setOnEditorActionListener{ v, actionId, event ->
            when(actionId){
                EditorInfo.IME_ACTION_DONE -> {
                    binding.editPrecoGasolina.findFocus()
                    true}
                else -> false
            }
        }
    }

    fun calcularPreco(view: View){

        val precoAlcool = binding.editPrecoAlcool.text.toString()
        val precoGasolina = binding.editPrecoGasolina.text.toString()

        val validaCampos = validarCampos(precoAlcool, precoGasolina)

        if(validaCampos){
            calcularMelhorPreco(precoAlcool, precoGasolina)
            binding.root.hideKeyboard()
            binding.editPrecoAlcool.clearFocus()
            binding.editPrecoGasolina.clearFocus()

        }else{
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_LONG).show()
        }
    }

    fun validarCampos(precoAlcool : String, precoGasolina : String): Boolean {
        var camposValidados = true

        if(precoAlcool.isEmpty() || precoAlcool.toDouble() == 0.0){
            camposValidados = false

        }else if(precoGasolina.isEmpty() || precoGasolina.toDouble() == 0.0){
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
            binding.textResultadoAlcool.visibility = View.INVISIBLE
            binding.textResultadoGasolina.visibility = View.VISIBLE
            binding.textResultadoGasolina.text = "Melhor utilizar Gasolina \nParidade: " + resultadoPorcentagemInt + "%"
        }else{
            binding.cardViewResultado.visibility = View.VISIBLE
            binding.textResultadoGasolina.visibility = View.INVISIBLE
            binding.textResultadoAlcool.visibility = View.VISIBLE
            binding.textResultadoAlcool.text = "Melhor utilizar Álcool \nParidade: " + resultadoPorcentagemInt  + "%"
        }

    }




}