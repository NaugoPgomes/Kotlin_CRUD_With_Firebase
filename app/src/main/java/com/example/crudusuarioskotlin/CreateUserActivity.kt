package com.example.crudusuarioskotlin

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.example.crudusuarioskotlin.Model.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_user.*
import java.text.SimpleDateFormat
import java.util.*

class CreateUserActivity : AppCompatActivity(),  View.OnClickListener,
    DatePickerDialog.OnDateSetListener
{
    private lateinit var bd: DatabaseReference
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        getSupportActionBar()?.hide()

        bd = FirebaseDatabase.getInstance().getReference("Usuarios")

        buttonCadastrar.setOnClickListener(this)
        birthData.setOnClickListener(this)
    }

    override fun onClick(bt: View?)
    {
        when(bt?.id)
        {
            buttonCadastrar.id ->
            {
                CreateUser()
            }

            birthData.id ->
            {
                showDatePicker()
            }
        }
    }

    private fun CreateUser()
    {
        val nome = userName.text.toString()
        val email = userEmail.text.toString()
        val numeroDeTelefoneCelular = cellPhoneNumber.text.toString()
        val dataDeNascimento = birthData.text.toString()

        val id = bd.push().key!!

        if(!nome.trim().isEmpty() && !email.trim().isEmpty() && !dataDeNascimento.trim().isEmpty() && !numeroDeTelefoneCelular.trim().isEmpty())
        {

            val model = UserModel(id, nome, email, numeroDeTelefoneCelular, dataDeNascimento)

            bd.child(id).setValue(model)
                .addOnCompleteListener {
                    Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                    val intente = Intent(this, MainActivity ::class.java)
                    startActivity(intente)
                    finish()
                }.addOnFailureListener { error ->
                    val toast = Toast.makeText(this, "Error $error", Toast.LENGTH_SHORT)
                    toast.show()
                }

        }
        else
        {
            Toast.makeText(this, "Nenhum Campo pode estar vazio.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showDatePicker()
    {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this, year, month, day).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int)
    {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        birthData.text = mDateFormat.format(calendar.time)
    }

}