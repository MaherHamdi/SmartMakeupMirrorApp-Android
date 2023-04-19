package tn.farah.smartmakeupapp.data.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class SubCategory(@Json(name="_id")val _id:String,
                       @Json(name="name")val name:String, @Json(name="description")val description:String, @Json(name="image")val image:String, @Json(name="category")val category:String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubCategory> {
        override fun createFromParcel(parcel: Parcel): SubCategory {
            return SubCategory(parcel)
        }

        override fun newArray(size: Int): Array<SubCategory?> {
            return arrayOfNulls(size)
        }
    }
}
data class  SubCategoryByCategory(@Json (name="category")val  category:String)
