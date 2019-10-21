package com.realstudio.here.impl

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

object ParcelUtil {
    /**
     * 将list列表转换为字节数组
     */
    internal fun <T : Parcelable> marshall(list: List<T>?): ByteArray {
        val parcel = Parcel.obtain()
        parcel.setDataPosition(0)
        if (list != null && list.isNotEmpty()) {
            parcel.writeTypedList(list)
        }
        val result = parcel.marshall()
        parcel.recycle()
        return result
    }

    /**
     * 将字节数组转换为list列表
     */
    internal fun <T : Parcelable> unmarshall(data: ByteArray?, creator: Parcelable.Creator<T>): List<T> {
        if (data == null) {
            return emptyList()
        }
        val parcel = Parcel.obtain()
        parcel.unmarshall(data, 0, data.size)
        val list = ArrayList<T>()
        parcel.setDataPosition(0)
        parcel.readTypedList(list, creator)
        parcel.recycle()
        return list
    }
}