package com.example.crudusuarioskotlin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity()
{

    private lateinit var user: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        getSupportActionBar()?.hide()

        user = FirebaseAuth.getInstance()

        button.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser()
    {
        val email = emailCadastro.text.toString()
        val senha = senhaCadastro.text.toString()
        val confirmar_senha = confirmarSenhaCadastro.text.toString()

        val progressDialog = ProgressDialog(this@RegisterActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.show()

        if(email.isNotEmpty() && senha.isNotEmpty())
        {
            if(senha.equals(confirmar_senha))
            {
                user.createUserWithEmailAndPassword(email,senha)
                    .addOnCompleteListener(RegisterActivity()) {task ->
                        progressDialog.dismiss()

                        if (task.isSuccessful)
                        {
                            Toast.makeText(this, "Usuario Cadastrado", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, RegisterActivity::class.java))
                            finish()
                        }

                    }
            }
            else
            {
                progressDialog.dismiss()
                Toast.makeText(this, "Os dois campos de senha tem que ser iguais", Toast.LENGTH_SHORT).show()
            }

        }
        else
        {
            Toast.makeText(this, "Email e senha Não podem estar vazios", Toast.LENGTH_SHORT).show()
        }
    }
}