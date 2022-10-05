package com.example.crudusuarioskotlin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()
{

    private lateinit var user: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()?.hide()

        user = FirebaseAuth.getInstance()

        checkIfTheUserIsLogged()

        button.setOnClickListener{
            Logar()
        }

        cadastro_tela_login.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun checkIfTheUserIsLogged()
    {
        if (user.currentUser !== null)
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun Logar()
    {
        val email = email.text.toString()
        val senha = senha.text.toString()

        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.show()

        if (email.trim().isNotEmpty() && senha.trim().isNotEmpty())
        {
            user.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener { mTask ->

                    progressDialog.dismiss()

                    if (mTask.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "A senha está incorreta ou o usuário não existe", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else
        {
            Toast.makeText(this, "Email e senha Não podem estar vazios", Toast.LENGTH_SHORT)
                .show()
        }
    }


}