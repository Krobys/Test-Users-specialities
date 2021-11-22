package com.example.testapp.ui.users.usersList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.network.response.UsersResponse
import com.example.testapp.databinding.ItemUserBinding
import com.example.testapp.tools.validateName

class UsersAdapter(private val userClickCallback: ((UsersResponse.User) -> Unit)) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val usersList: ArrayList<UsersResponse.User> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(list: List<UsersResponse.User>) {
        usersList.run {
            clear()
            addAll(list)
        }
        notifyDataSetChanged() //can be changed with diff util
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = usersList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = usersList.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentUser: UsersResponse.User? = null

        init {
            binding.root.setOnClickListener {
                currentUser?.let(userClickCallback)
            }
        }

        fun bind(user: UsersResponse.User) {
            currentUser = user
            binding.name.text = user.fName.validateName()
            binding.surname.text = user.lName.validateName()
            binding.date.text = if (user.birthday.isNullOrEmpty()) "-" else user.birthday
        }
    }
}