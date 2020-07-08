package com.example.phonesapp.ui.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.phonesapp.R
import com.example.phonesapp.databinding.PersonInfoItemBinding
import com.example.phonesapp.network.PersonInfoRandFull

class PersonAdapter(var personClickListener: OnPersonClickListener): RecyclerView.Adapter<PersonAdapter.PersonHolder>() {

    var personsList: ArrayList<PersonInfoRandFull.Result> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val binding = DataBindingUtil.inflate<PersonInfoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.person_info_item,
            parent,
            false
        )
        return PersonHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        holder.binding.personInfo = personsList[position]
    }

    override fun getItemCount(): Int = personsList.size

    inner class PersonHolder(var binding: PersonInfoItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                personClickListener.onPersonClick(binding.personInfo, binding.imageView)
            }
        }

    }
}