package com.example.crudusuarioskotlin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudusuarioskotlin.Adapter.UsersAdapter
import com.example.crudusuarioskotlin.Model.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity()
{

    private lateinit var bd : DatabaseReference

    private lateinit var recycler : RecyclerView

    private var userArrayList : ArrayList<UserModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intente = Intent(this, CreateUserActivity ::class.java)
            startActivity(intente)
        }

        recycler = findViewById(R.id.recyclerview)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        userArrayList = arrayListOf<UserModel>()

        getDados()

    }


    private fun getDados()
    {

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.show()

        bd = FirebaseDatabase.getInstance().getReference("Usuarios")

        bd.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                progressDialog.dismiss()

                if(snapshot.exists())
                {
                    for(itensSnapshot in snapshot.children)
                    {
                        val user = itensSnapshot.getValue(UserModel::class.java)
                        userArrayList.add(user!!)
                    }

                    val adapter = UsersAdapter( userArrayList)
                    recycler.adapter = adapter


                    adapter.setOnItemClickListener(object : UsersAdapter.onItemClickListener
                    {
                        override fun onItemClick(position: Int)
                        {
                            val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)

                            intent.putExtra("nome",  userArrayList[position].nome)
                            intent.putExtra("email",  userArrayList[position].email)
                            intent.putExtra("telefone",  userArrayList[position].numeroTelefoneCelular)
                            intent.putExtra("data",  userArrayList[position].dataDeNascimento)
                            intent.putExtra("id",  userArrayList[position].id)

                            startActivity(intent)
                            finish()
                        }

                    })

                }
            }

            override fun onCancelled(error: DatabaseError)
            {

            }

        })

    }
}