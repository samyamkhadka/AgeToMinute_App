package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvDateLabel: TextView
    private lateinit var tvDateToMinute: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvDateLabel = findViewById(R.id.tvDateLabel)
        tvDateToMinute = findViewById(R.id.tvDateToMinute)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance() // getting the calendar

        val year = myCalendar.get(Calendar.YEAR) // getting all the years

        val month = myCalendar.get(Calendar.MONTH) // getting all the months

        val day = myCalendar.get(Calendar.DAY_OF_MONTH) // getting all the days of the months

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this,
                    "Year was $selectedYear, month was ${selectedMonth+1}, date was $selectedDayOfMonth.", Toast.LENGTH_LONG).show() // small pop shows at the bottom of the screen for a bit

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                tvDateLabel.text = selectedDate // changing the text

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) // we are creating a date storing format for english times

                val theDate = sdf.parse(selectedDate) // parsing the dates string onto the format

                theDate?.let{
                    val selectedDateInMinutes = theDate.time / 60000 // getting the selected date in minutes

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time / 60000 // getting today's date in minutes

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes // finding the difference between today's date in minutes and selected date in minutes

                        tvDateToMinute.text = differenceInMinutes.toString() // changing the text
                    } // runs only if currentDate is not empty (safety)
                } // runs only if theDate is not empty (safety)


            }, // datePickerDialog.OnDateSetListener is triggered when the user selects the date
            year,
            month,
            day
            ) // DatePickerDialog method adds a date picker pop up onto the screen

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // limits date selection to yesterday

        dpd.show() // shows the date picker on the screen


    }

}