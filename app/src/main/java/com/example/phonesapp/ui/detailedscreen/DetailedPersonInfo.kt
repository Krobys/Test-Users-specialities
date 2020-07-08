package com.example.phonesapp.ui.detailedscreen

import android.os.Parcel
import android.os.Parcelable

class DetailedPersonInfo : Parcelable{

    public constructor(photoSrc: String?,
                personFirstName: String?,
                personLastName: String?,
                personAge: Int,
                personPhoneNumber: String?,
                personEmail: String?,
                personSkype: String?) {

        this.photoSrc = photoSrc
        this.personFirstName = personFirstName
        this.personLastName = personLastName
        this.personAge = personAge
        this.personPhoneNumber = personPhoneNumber
        this.personEmail = personEmail
        this.personSkype = personSkype
    }

    var photoSrc: String? = ""
    var personFirstName: String? = ""
    var personLastName: String? = ""
    var personAge: Int = 0
    var personPhoneNumber: String? = ""
    var personEmail: String? = ""
    var personSkype: String? = ""

    constructor(parcel: Parcel) {
        photoSrc = parcel.readString()
        personFirstName = parcel.readString()
        personLastName = parcel.readString()
        personAge = parcel.readInt()
        personPhoneNumber = parcel.readString()
        personEmail = parcel.readString()
        personSkype = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(photoSrc)
        parcel.writeString(personFirstName)
        parcel.writeString(personLastName)
        parcel.writeInt(personAge)
        parcel.writeString(personPhoneNumber)
        parcel.writeString(personEmail)
        parcel.writeString(personSkype)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailedPersonInfo> {
        override fun createFromParcel(parcel: Parcel): DetailedPersonInfo {
            return DetailedPersonInfo(parcel)
        }

        override fun newArray(size: Int): Array<DetailedPersonInfo?> {
            return arrayOfNulls(size)
        }
    }
}