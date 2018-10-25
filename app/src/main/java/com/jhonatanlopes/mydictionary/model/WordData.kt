package com.jhonatanlopes.mydictionary.model

import android.os.Parcel
import android.os.Parcelable

class WordData(
        val wordId: String,
        val exemple: String,
        pronunciation: String,
        val definition: String
) : Parcelable {

    val pronunciation: String = pronunciation
        get() = "/$field/"

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(wordId)
        dest.writeString(exemple)
        dest.writeString(pronunciation)
        dest.writeString(definition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WordData> {
        override fun createFromParcel(parcel: Parcel): WordData {
            return WordData(parcel)
        }

        override fun newArray(size: Int): Array<WordData?> {
            return arrayOfNulls(size)
        }
    }
}