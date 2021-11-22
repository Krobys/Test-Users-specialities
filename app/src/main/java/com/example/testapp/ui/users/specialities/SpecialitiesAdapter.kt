package com.example.testapp.ui.users.specialities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.network.response.UsersResponse
import com.example.testapp.databinding.ItemSpecialityBinding

class SpecialitiesAdapter(private val specialtyClickCallback: ((UsersResponse.User.Specialty) -> Unit)) :
    RecyclerView.Adapter<SpecialitiesAdapter.SpecialityViewHolder>() {

    private val specialitiesList: ArrayList<UsersResponse.User.Specialty> = ArrayList()

    fun setSpecialities(list: List<UsersResponse.User.Specialty>) {
        specialitiesList.run {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        val binding =
            ItemSpecialityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        val specialty = specialitiesList[position]
        holder.bind(specialty)
    }

    override fun getItemCount(): Int = specialitiesList.size

    inner class SpecialityViewHolder(private val binding: ItemSpecialityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentSpecialty: UsersResponse.User.Specialty? = null

        init {
            binding.root.setOnClickListener {
                currentSpecialty?.let(specialtyClickCallback)
            }
        }


        fun bind(specialty: UsersResponse.User.Specialty) {
            currentSpecialty = specialty
            binding.specialityTextView.text = specialty.name
        }
    }
}