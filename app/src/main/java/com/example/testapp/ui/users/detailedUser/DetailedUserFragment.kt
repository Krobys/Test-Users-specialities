package com.example.testapp.ui.users.detailedUser

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.base.BaseFragmentWithoutViewModel
import com.example.testapp.base.SharedActivityParent
import com.example.testapp.base.SharedFragmentChild
import com.example.testapp.data.network.response.UsersResponse
import com.example.testapp.databinding.FragmentDetailedUserBinding
import com.example.testapp.ui.MainActivity
import com.example.testapp.tools.setPaddingBottom
import com.example.testapp.tools.setPaddingTop
import com.example.testapp.tools.setVisible
import com.example.testapp.tools.validateName
import java.text.SimpleDateFormat
import java.util.*

class DetailedUserFragment(override val layoutId: Int = R.layout.fragment_detailed_user)
    : BaseFragmentWithoutViewModel<FragmentDetailedUserBinding>(), SharedFragmentChild{

    override val sharedParentActivity: SharedActivityParent?
        get() = activity as? MainActivity

    override fun initViews() {
        initReceiver(viewLifecycleOwner)
    }

    override fun applyInsetsPadding(
        systemStatusBarSize: Int,
        systemNavigationBarSize: Int,
        isKeyboardOpen: Boolean
    ) {
        binding.rootLayout.setPaddingTop(systemStatusBarSize)
        binding.rootLayout.setPaddingBottom(systemNavigationBarSize)
    }

    private fun setUpUser(user: UsersResponse.User){
        binding.run {
            Glide.with(binding.photoImageView)
                .load(user.avatrUrl)
                .error(ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_foreground))
                .into(binding.photoImageView)

            firstNameTv.text = user.fName.validateName()
            secondNameTv.text = user.lName.validateName()
            birthdayTv.text = if (user.birthday.isNullOrEmpty()) "-" else user.birthday

            if (user.birthday.isNullOrEmpty()){
                yearsTv.setVisible(false)
            }else{
                yearsTv.setVisible(true)

                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(user.birthday)?.let { birthdayDate ->
                    val years = getDiffYears(birthdayDate, Calendar.getInstance().time)
                    if (years < 150){
                        binding.yearsTv.text = resources.getQuantityString(R.plurals.years_plural, years, years)
                    }else{
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(user.birthday)?.let { birthdayDateOtherType ->
                            val yearsOther = getDiffYears(birthdayDateOtherType, Calendar.getInstance().time)
                            binding.yearsTv.text = resources.getQuantityString(R.plurals.years_plural, yearsOther, yearsOther)
                        }
                    }

                }
            }

            binding.specialtyTv.text = user.specialty.map { it.name }.joinToString(", ")
        }
    }

    private fun getDiffYears(first: Date, last: Date): Int {

        fun Date.toCalendar() : Calendar{
            return Calendar.getInstance().apply {
                timeInMillis = this@toCalendar.time
            }
        }

        val a: Calendar = first.toCalendar()
        val b: Calendar = last.toCalendar()
        var diff = b[Calendar.YEAR] - a[Calendar.YEAR]
        if (a[Calendar.DAY_OF_YEAR] > b[Calendar.DAY_OF_YEAR]) {
            diff--
        }
        return diff
    }

    override fun sharedDataReceived(data: Any, identifier: String) {
        if (data is UsersResponse.User && identifier == USER_KEY){
            setUpUser(data)
        }
    }

    companion object{
        final val USER_KEY = "USER_KEY"
    }
}