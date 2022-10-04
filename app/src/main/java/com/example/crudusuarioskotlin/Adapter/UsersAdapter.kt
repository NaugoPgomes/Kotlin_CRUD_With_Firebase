package com.example.crudusuarioskotlin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudusuarioskotlin.Model.UserModel
import com.example.crudusuarioskotlin.R

class UsersAdapter (private val usersList : ArrayList<UserModel>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>()
{

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener
    {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener)
    {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_lista, parent, false)

        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val currentUser = usersList[position]

        holder.nome.text = currentUser.nome
        holder.email.text = currentUser.email
        holder.telefone.text = currentUser.numeroTelefoneCelular
        holder.data.text = currentUser.dataDeNascimento
    }

    override fun getItemCount(): Int
    {
        return usersList.size
    }



    class ViewHolder (userView : View, clickListener: onItemClickListener ) : RecyclerView.ViewHolder(userView)
    {

        val nome : TextView = userView.findViewById(R.id.name)
        val email : TextView = userView.findViewById(R.id.email)
        val telefone : TextView = userView.findViewById(R.id.phone)
        val data : TextView = userView.findViewById(R.id.data)

        init {
            userView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}