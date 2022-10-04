package com.example.crudusuarioskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.crudusuarioskotlin.Model.UserModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        getSupportActionBar()?.hide()


        setValuesToViews()

        buttonDetailsUpdate.setOnClickListener {
            openUpdateDialog(intent.getStringExtra("id").toString())
        }

        buttonDetailsDelete.setOnClickListener {
            DeleteRecord(intent.getStringExtra("id").toString())

        }

    }

    private fun setValuesToViews()
    {
        nomeDetails.text = intent.getStringExtra("nome")
        emailDetails.text = intent.getStringExtra("email")
        phoneDetails.text = intent.getStringExtra("telefone")
        dataDetails.text = intent.getStringExtra("data")

    }

    private fun openUpdateDialog(id: String)
    {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update, null)

        mDialog.setView(mDialogView)

        val editNome = mDialogView.findViewById<EditText>(R.id.editName)
        val editEmail = mDialogView.findViewById<EditText>(R.id.editEmail)
        val editPhone = mDialogView.findViewById<EditText>(R.id.editPhone)
        val editData = mDialogView.findViewById<EditText>(R.id.editData)
        val editButton = mDialogView.findViewById<Button>(R.id.buttonEdit)

        editNome.setText(intent.getStringExtra("nome").toString())
        editEmail.setText(intent.getStringExtra("email").toString())
        editPhone.setText(intent.getStringExtra("telefone").toString())
        editData.setText(intent.getStringExtra("data").toString())


        mDialog.setTitle("Editar Item")

        val alertDialog = mDialog.create()
        alertDialog.show()


        editButton.setOnClickListener {
            updateEmpData(id, editNome.text.toString(), editEmail.text.toString(), editPhone.text.toString(), editData.text.toString())

            Toast.makeText(this, "Item editado com sucesso", Toast.LENGTH_SHORT).show()

            nomeDetails.text = editNome.text.toString()
            emailDetails.text = editEmail.text.toString()
            phoneDetails.text = editPhone.text.toString()
            dataDetails.text = editData.text.toString()

            alertDialog.dismiss()
            val intente = Intent(this, MainActivity::class.java)
            startActivity(intente)
            finish()
        }

    }

    private fun updateEmpData(id: String, nomeDoProduto: String, email: String, phone: String, data: String)
    {
        val bd = FirebaseDatabase.getInstance().getReference("Usuarios").child(id)
        val empInfo = UserModel(id,  nomeDoProduto, email, phone , data)
        bd.setValue(empInfo)
    }


    private fun DeleteRecord(id: String)
    {

        val bd = FirebaseDatabase.getInstance().getReference("Usuarios").child(id)
        val mDeletar = bd.removeValue()

        mDeletar.addOnSuccessListener {
            Toast.makeText(this, "Item deletado com sucesso", Toast.LENGTH_SHORT).show()
            val intente = Intent(this, MainActivity::class.java)
            startActivity(intente)
            finish()
        }.addOnFailureListener { error ->
            Toast.makeText(this, "error : ${error.message}", Toast.LENGTH_SHORT).show()
        }



    }

}