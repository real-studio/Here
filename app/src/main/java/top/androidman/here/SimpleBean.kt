package top.androidman.here

import android.os.Parcel
import android.os.Parcelable

data class SimpleBean(var name: String, var age: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "SimpleBean(name='$name', age=$age)"
    }

    companion object CREATOR : Parcelable.Creator<SimpleBean> {
        override fun createFromParcel(parcel: Parcel): SimpleBean {
            return SimpleBean(parcel)
        }

        override fun newArray(size: Int): Array<SimpleBean?> {
            return arrayOfNulls(size)
        }
    }

}