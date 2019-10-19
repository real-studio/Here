package com.realstudio.here.impl

import android.os.Parcelable
import com.tencent.mmkv.MMKV

class HereImpl(var mmkv: MMKV) {

    companion object {

        private val MAP: MutableMap<String, HereImpl> = mutableMapOf()

        @JvmStatic
        fun bucket(name: String): HereImpl {
            synchronized(HereImpl::class) {
                var here = MAP[name]
                if (here == null) {
                    here = HereImpl(createMMKV(name))
                    MAP[name] = here
                }
                return here
            }
        }

        @JvmStatic
        fun global(): HereImpl {
            return bucket(BucketFactory.Global.name)
        }

        private fun createMMKV(bucketName: String): MMKV {
            return when (bucketName) {
                BucketFactory.Global.name -> MMKV.defaultMMKV()
                else -> {
                    MMKV.mmkvWithID(bucketName) ?: MMKV.defaultMMKV()
                }
            }
        }
    }

    /**
     * 存储列表对象
     */
    fun <E : Parcelable> put(key: String, elementList: List<E>): HereImpl {
        mmkv.encode(key, ParcelUtil.marshall(elementList))
        return this
    }

    /**
     * 存储Parcelable对象
     */
    fun <E : Parcelable> put(key: String, element: E): HereImpl {
        mmkv.encode(key, element)
        return this
    }

    /**
     * 存储Boolean值
     */
    fun put(key: String, boolean: Boolean): HereImpl {
        mmkv.encode(key, boolean)
        return this
    }

    /**
     * 存储Int值
     */
    fun put(key: String, int: Int): HereImpl {
        mmkv.encode(key, int)
        return this
    }

    /**
     * 存储Long值
     */
    fun put(key: String, long: Long): HereImpl {
        mmkv.encode(key, long)
        return this
    }

    /**
     * 存储Float值
     */
    fun put(key: String, float: Float): HereImpl {
        mmkv.encode(key, float)
        return this
    }

    /**
     * 存储Double值
     */
    fun put(key: String, double: Double): HereImpl {
        mmkv.encode(key, double)
        return this
    }

    /**
     * 存储String值
     */
    fun put(key: String, string: String): HereImpl {
        mmkv.encode(key, string)
        return this
    }

    /**
     * 存储字节数组
     */
    fun put(key: String, array: ByteArray): HereImpl {
        mmkv.encode(key, array)
        return this
    }

    /**
     * 存储字符串集合
     */
    fun put(key: String, set: Set<String>): HereImpl {
        mmkv.encode(key, set)
        return this
    }

    /**
     * 获取列表对象
     */
    fun <E : Parcelable> getList(key: String, creator: Parcelable.Creator<E>): List<E>? {
        val bytes: ByteArray? = mmkv.decodeBytes(key)
        return ParcelUtil.unmarshall(bytes, creator)
    }

    /**
     * 获取Parcelable对象
     */
    fun <E : Parcelable> getParcelable(key: String, clazz: Class<E>): E? {
        return mmkv.decodeParcelable(key, clazz)
    }

    /**
     * 获取Boolean值
     */
    fun getBool(key: String): Boolean {
        return getBool(key, false)
    }

    fun getBool(key: String, defaultValue: Boolean): Boolean {
        return mmkv.decodeBool(key, defaultValue)
    }

    /**
     * 获取Int值
     */
    fun getInt(key: String): Int {
        return getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return mmkv.decodeInt(key, defaultValue)
    }

    /**
     * 获取Long值
     */
    fun getLong(key: String): Long {
        return getLong(key, 0L)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return mmkv.decodeLong(key, defaultValue)
    }

    /**
     * 获取String值
     */
    fun getString(key: String): String {
        return getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String {
        return mmkv.decodeString(key, defaultValue)
    }

    /**
     * 获取Double值
     */
    fun getDouble(key: String): Double {
        return getDouble(key, 0.0)
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        return mmkv.decodeDouble(key, defaultValue)
    }

    /**
     * 获取Float值
     */
    fun getFloat(key: String): Float {
        return getFloat(key, 0.0F)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return mmkv.decodeFloat(key, defaultValue)
    }

    /**
     * 获取字节数组
     */
    fun getByteArray(key: String): ByteArray? {
        return mmkv.decodeBytes(key, null)
    }

    fun getByteArray(key: String, defaultValue: ByteArray): ByteArray {
        return mmkv.decodeBytes(key, defaultValue)
    }

    /**
     * 获取字符串Set
     */
    fun getStringSet(key: String): Set<String>? {
        return getStringSet(key, null)
    }

    fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? {
        return mmkv.getStringSet(key, defaultValue)
    }

    fun clearAll() {
        mmkv.clearAll()
    }

    /**
     * 删除指定key的值
     */
    fun removeValueForKey(key: String) {
        mmkv.removeValueForKey(key)
    }

    /**
     * 查询Here是否包含指定key的值
     */
    fun containsKey(key: String): Boolean {
        return mmkv.containsKey(key)
    }



}
