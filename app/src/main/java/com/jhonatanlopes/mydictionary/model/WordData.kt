package com.jhonatanlopes.mydictionary.model

import android.os.Parcel
import android.os.Parcelable

class WordData(
        val wordId: String,
        val exemple: String,
        pronunciation: String,
        val definitions: List<String>
) : Parcelable {

    val pronunciation: String = pronunciation
        get() = "/$field/"

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArrayList()
    )

    fun definitionsAsOneString(): String {
        var definitionString = ""
        for ((index, definition) in definitions.withIndex()) {
            definitionString += "${index + 1}. $definition\n"
        }
        return definitionString
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(wordId)
        dest.writeString(exemple)
        dest.writeString(pronunciation)
        dest.writeStringList(definitions)
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