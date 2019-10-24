package com.realstudio.here.impl

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.lang.ref.WeakReference

class HereImpl(val mmkv: MMKV) {

    companion object {

        private val HERE_IMPL_MAP: MutableMap<String, WeakReference<HereImpl>> = mutableMapOf()

        @JvmStatic
        fun bucket(name: String, config: Config): HereImpl {
            synchronized(HereImpl::class) {
                var hereRef = HERE_IMPL_MAP[name]
                var here = hereRef?.get()
                if (here == null) {
                    here = HereImpl(createMMKV(name, config))
                    hereRef = WeakReference(here)
                    HERE_IMPL_MAP[name] = hereRef
                }
                return here
            }
        }

        private fun createMMKV(name: String, config: Config): MMKV {
            val mode =
                if (config.multiProcess) MMKV.MULTI_PROCESS_MODE else MMKV.SINGLE_PROCESS_MODE

            return when (name) {
                BucketFactory.Global.name -> MMKV.defaultMMKV(mode, config.encryptKey)
                else -> {
                    MMKV.mmkvWithID(name, mode, config.encryptKey)
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

    fun <E : Parcelable> put(key: String, map: Map<String, E>): HereImpl {
        mmkv.encode(key, ParcelUtil.marshall(map))
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
     * 存储数据
     */
    fun <E> put(key: String, value: E): HereImpl {
        mmkv.apply {
            when (value) {
                is Parcelable -> encode(key, value)
                is Boolean -> encode(key, value)
                is ByteArray -> encode(key, value)
                is Double -> encode(key, value)
                is Float -> encode(key, value)
                is Int -> encode(key, value)
                is Long -> encode(key, value)
                is String -> encode(key, value)
            }
        }
        return this
    }

    /**
     * 获取列表对象
     */
    fun <E : Parcelable> getList(key: String, creator: Parcelable.Creator<E>): List<E>? {
        val bytes: ByteArray? = mmkv.decodeBytes(key)
        return ParcelUtil.unmarshall(bytes, creator)
    }

    fun <E : Parcelable> getMap(key: String): Map<String, E?> {
        val bytes: ByteArray? = mmkv.decodeBytes(key)
        return ParcelUtil.unmarshall(bytes)
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
        return get(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return get(key, defaultValue) ?: defaultValue
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
    fun getByteArray(key: String): ByteArray {
        return getByteArray(key, ByteArray(0))
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

    /**
     * 获取值
     */
    inline fun <reified E> get(key: String, defaultValue: E): E = with(mmkv) {
        val result: Any? = when (defaultValue) {
            is Boolean -> decodeBool(key, defaultValue)
            is ByteArray -> decodeBytes(key, defaultValue)
            is Double -> decodeDouble(key, defaultValue)
            is Float -> decodeFloat(key, defaultValue)
            is Int -> decodeInt(key, defaultValue)
            is Long -> decodeLong(key, defaultValue)
            is String -> decodeString(key, defaultValue)
            else -> null
        }
        return if (result is E) result else defaultValue
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
