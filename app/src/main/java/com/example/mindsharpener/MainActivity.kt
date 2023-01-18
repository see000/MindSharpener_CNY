package com.example.mindsharpener

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.mindsharpener.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var point = 0
    private var randomFirstNumber = 0
    private var randomSecondNumber = 0
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // bind onclick listener to check button
        binding.checkButton.setOnClickListener{checkAnswer()}

        //bind listener to input field
        binding.inputAnswer.setOnKeyListener{ view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        generateQuestion()


    }
    private fun displayFinalPoint(point: String) {

        binding.point.text = getString(R.string.point, point)
    }

    // generate random question based on user chosen level
    private  fun generateQuestion(){

        var levelChosen = when(binding.radioGroup.checkedRadioButtonId){
            R.id.radio_easy -> 9
            R.id.radio_medium ->99
            R.id.radio_hard ->999
            else ->9
        }
        var randomOperator = Random.nextInt(0,3)
        var operator = when(randomOperator){
            0-> "+"
            1-> "-"
            2->"*"
            3->"/"
            else->"error"
        }
         randomFirstNumber = Random.nextInt(0,levelChosen)
         randomSecondNumber = Random.nextInt(0,levelChosen)

        binding.firstNumber.text = randomFirstNumber.toString()
        binding.secondNumber.text = randomSecondNumber.toString()
        binding.operator.text = operator




    }

    // check the user answer with the system answer
    fun checkAnswer(){

        //get user answer
        val userInputFieldString = binding.inputAnswer.text.toString()
        //convert user answer to double
        val userAnswerDouble = userInputFieldString.toDoubleOrNull()
        print(userInputFieldString)

        fun getSystemAnswer(){
            print(randomFirstNumber.toString())
            print(randomSecondNumber.toString())

                if(binding.operator.toString() =="+")
                    randomFirstNumber + randomSecondNumber
                else if (binding.operator.toString() =="-")
                    randomFirstNumber - randomSecondNumber
                else if (binding.operator.toString() =="*")
                    randomFirstNumber * randomSecondNumber
                else if (binding.operator.toString() =="/")
                    randomFirstNumber / randomSecondNumber
                else 0
        }
        val systemAnswer = getSystemAnswer()

        if (userAnswerDouble.toString() == systemAnswer.toString()){
            point = point + 1
            print(point)
        }

        displayFinalPoint(point.toString())

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_easy ->
                    if (checked) {
                        generateQuestion()
                    }
                R.id.radio_medium ->
                    if (checked) {
                        generateQuestion()
                    }
                R.id.radio_hard ->
                    if (checked) {
                        generateQuestion()
                    }
            }
        }
    }






    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}