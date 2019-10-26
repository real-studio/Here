package org.realstudio.here.impl

import android.os.Bundle
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

    internal fun <T : Parcelable> marshall(map: Map<String, T>?): ByteArray {
        val parcel = Parcel.obtain()
        parcel.setDataPosition(0)
        if (map != null && map.isNotEmpty()) {
            val bundle = Bundle()
            map.forEach{
               bundle.putParcelable(it.key, it.value)
            }
            parcel.writeBundle(bundle)
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

    internal fun <T : Parcelable> unmarshall(data: ByteArray?): Map<String, T?> {
        if (data == null) {
            return emptyMap<String, T>()
        }

        val parcel = Parcel.obtain()
        parcel.unmarshall(data, 0, data.size)
        parcel.setDataPosition(0)

        val bundle = parcel.readBundle(javaClass.classLoader)
        val result = mutableMapOf<String, T?>()
        bundle?.keySet()?.let {
            for (key in it) {
                result[key] = bundle.getParcelable(key)
            }
        }
        parcel.recycle()
        return result
    }
}