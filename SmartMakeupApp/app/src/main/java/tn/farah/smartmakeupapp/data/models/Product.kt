package tn.farah.smartmakeupapp.data.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Product(@Json(name="_id")val _id:String,
                    @Json(name="name")val name:String,
                    @Json(name="description")val description:String,
                    @Json(name="image")val image:String,
                    @Json(name="price")val price: Double,
                    @Json(name="quantity")val quantity:Int,
                    @Json(name="new")val new:Boolean,
                    @Json(name="promotion")val promotion:Boolean,
                    @Json(name="subCategory")val subCategory:String,
                   @Json(name="category")val category:String,
                   @Json(name="isFaved")val isFaved:Boolean,
                    @Json(name="rating")val rating:Int)   : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
       parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeDouble(price)
        parcel.writeInt(quantity)
        parcel.writeByte(if (new) 1 else 0)
        parcel.writeByte(if (promotion) 1 else 0)
        parcel.writeString(subCategory)
       parcel.writeString(category)
        parcel.writeByte(if (isFaved) 1 else 0)
        parcel.writeInt(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}

//@Json(name="category")val category:String)

 class NewProducts(@Json (name="new")val new: Boolean)
class ProductsByCategory(@Json (name="category")val category: String)
