package com.android.dicodingstoryapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("listStory")
    val listStory: List<StoryApp>
) {
    data class StoryApp(
        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("photoUrl")
        val photoUrl: String? = null,

        @field:SerializedName("createdAt")
        val createdAt: String,

        @field:SerializedName("lat")
        val lat: Double? = null,

        @field:SerializedName("lon")
        val lon: Double? = null
    ): Parcelable {
        constructor(parcel: Parcel): this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString().toString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double
        )

        override fun writeToParcel(parcel: Parcel, flag: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
            parcel.writeString(description)
            parcel.writeString(photoUrl)
            parcel.writeString(createdAt)
            parcel.writeValue(lat)
            parcel.writeValue(lon)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR: Parcelable.Creator<StoryApp> {
            override fun createFromParcel(parcel: Parcel): StoryApp {
                return StoryApp(parcel)
            }

            override fun newArray(size: Int): Array<StoryApp?> {
                return arrayOfNulls(size)
            }
        }

    }
}
