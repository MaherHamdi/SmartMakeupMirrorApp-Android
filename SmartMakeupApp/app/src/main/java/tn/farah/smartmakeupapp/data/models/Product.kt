package tn.farah.smartmakeupapp.data.models

import android.os.Parcel
import android.os.Parcelable

data class Product(val id:String,val name:String,val description:String,val image:String,val price: Double,val quantity:Int,val new:Boolean,val promotion:Boolean,val subCategory:String,val category:String,val isFaved:Boolean,val rating:Int):Parcelable {
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
        parcel.writeString(id)
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
