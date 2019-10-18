package com.realstudio.here

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.util.*


/**
 * @author         yanjie
 * @date     2019-10-09 14:44
 * @version        1.0
 * @description 键值存储
 */


object Here {
    private val mmkv: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    @JvmStatic
    fun init(context: Context) {
        MMKV.initialize(context)
    }

    /**
     * 存储列表对象
     */
    @JvmStatic
    fun <E : Parcelable> put(key: String, elementList: List<E>) {
        mmkv.encode(key, marshall(elementList))
    }

    /**
     * 存储Parcelable对象
     */
    @JvmStatic
    fun <E : Parcelable> put(key: String, element: E) {
        mmkv.encode(key, element)
    }

    /**
     * 存储Boolean值
     */
    @JvmStatic
    fun put(key: String, boolean: Boolean) {
        mmkv.encode(key, boolean)
    }

    /**
     * 存储Int值
     */
    @JvmStatic
    fun put(key: String, int: Int) {
        mmkv.encode(key, int)
    }

    /**
     * 存储Long值
     */
    @JvmStatic
    fun put(key: String, long: Long) {
        mmkv.encode(key, long)
    }

    /**
     * 存储Float值
     */
    @JvmStatic
    fun put(key: String, float: Float) {
        mmkv.encode(key, float)
    }

    /**
     * 存储Double值
     */
    @JvmStatic
    fun put(key: String, double: Double) {
        mmkv.encode(key, double)
    }

    /**
     * 存储String值
     */
    @JvmStatic
    fun put(key: String, string: String) {
        mmkv.encode(key, string)
    }

    /**
     * 存储字节数组
     */
    @JvmStatic
    fun put(key: String, array: ByteArray) {
        mmkv.encode(key, array)
    }

    /**
     * 存储字符串集合
     */
    @JvmStatic
    fun put(key: String, set: Set<String>) {
        mmkv.encode(key, set)
    }

    /**
     * 获取列表对象
     */
    @JvmStatic
    fun <E : Parcelable> getList(key: String, creator: Parcelable.Creator<E>): List<E>? {
        val bytes: ByteArray? = mmkv.decodeBytes(key)
        return unmarshall(bytes, creator)
    }

    /**
     * 获取Parcelable对象
     */
    @JvmStatic
    fun <E : Parcelable> getParcelable(key: String, clazz: Class<E>): E? {
        return mmkv.decodeParcelable(key, clazz)
    }

    /**
     * 获取Boolean值
     */
    @JvmStatic
    fun getBool(key: String): Boolean {
        return getBool(key, false)
    }

    @JvmStatic
    fun getBool(key: String, defaultValue: Boolean): Boolean {
        return mmkv.decodeBool(key, defaultValue)
    }

    /**
     * 获取Int值
     */
    @JvmStatic
    fun getInt(key: String): Int {
        return getInt(key, 0)
    }

    @JvmStatic
    fun getInt(key: String, defaultValue: Int): Int {
        return mmkv.decodeInt(key, defaultValue)
    }

    /**
     * 获取Long值
     */
    @JvmStatic
    fun getLong(key: String): Long {
        return getLong(key, 0L)
    }

    @JvmStatic
    fun getLong(key: String, defaultValue: Long): Long {
        return mmkv.decodeLong(key, defaultValue)
    }

    /**
     * 获取String值
     */
    @JvmStatic
    fun getStr(key: String): String {
        return getStr(key, "")
    }

    @JvmStatic
    fun getStr(key: String, defaultValue: String): String {
        return mmkv.decodeString(key, defaultValue)
    }

    /**
     * 获取Double值
     */
    @JvmStatic
    fun getDouble(key: String): Double {
        return getDouble(key, 0.0)
    }

    @JvmStatic
    fun getDouble(key: String, defaultValue: Double): Double {
        return mmkv.decodeDouble(key, defaultValue)
    }

    /**
     * 获取Float值
     */
    @JvmStatic
    fun getFloat(key: String): Float {
        return getFloat(key, 0.0F)
    }

    @JvmStatic
    fun getFloat(key: String, defaultValue: Float): Float {
        return mmkv.decodeFloat(key, defaultValue)
    }

    /**
     * 获取字节数组
     */
    @JvmStatic
    fun getByteArray(key: String): ByteArray? {
        return mmkv.decodeBytes(key, null)
    }

    @JvmStatic
    fun getByteArray(key: String, defaultValue: ByteArray): ByteArray {
        return mmkv.decodeBytes(key, defaultValue)
    }

    /**
     * 获取字符串Set
     */
    @JvmStatic
    fun getStringSet(key: String): Set<String>? {
        return getStringSet(key, null)
    }

    @JvmStatic
    fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? {
        return mmkv.getStringSet(key, defaultValue)
    }

    @JvmStatic
    fun clearAll() {
        mmkv.clearAll()
    }

    /**
     * 删除指定key的值
     */
    @JvmStatic
    fun removeValueForKey(key: String) {
        mmkv.removeValueForKey(key)
    }

    /**
     * 查询Here是否包含指定key的值
     */
    @JvmStatic
    fun containsKey(key: String): Boolean {
        return mmkv.containsKey(key)
    }

    /**
     * 将list列表转换为字节数组
     */
    private fun <T : Parcelable> marshall(list: List<T>?): ByteArray {
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
    private fun <T : Parcelable> unmarshall(
        data: ByteArray?,
        creator: Parcelable.Creator<T>
    ): List<T> {
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
