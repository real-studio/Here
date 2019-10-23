package com.realstudio.here

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.*
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HereTest {
    val KeyNotExist = "Key_Not_Exist"
    val Delta = 0.000001f

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().context
        Here.init(appContext)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        Here.clearAll()
    }

    @Test
    fun testBool() {
        Here.put("bool", true)

        var value = Here.getBool("bool")
        assertEquals(value, true)

        value = Here.getBool(KeyNotExist)
        assertEquals(value, false)

        value = Here.getBool(KeyNotExist, true)
        assertEquals(value, true)
    }

    @Test
    fun testInt() {
        Here.put("int", Integer.MAX_VALUE)

        var value = Here.getInt("int")
        assertEquals(value, Integer.MAX_VALUE)

        value = Here.getInt(KeyNotExist)
        assertEquals(value, 0)

        value = Here.getInt(KeyNotExist, -1)
        assertEquals(value, -1)
    }

    @Test
    fun testLong() {
        Here.put("long", java.lang.Long.MAX_VALUE)

        var value = Here.getLong("long")
        assertEquals(value, java.lang.Long.MAX_VALUE)

        value = Here.getLong(KeyNotExist)
        assertEquals(value, 0)

        value = Here.getLong(KeyNotExist, -1)
        assertEquals(value, -1)
    }

    @Test
    fun testFloat() {
        Here.put("float", java.lang.Float.MAX_VALUE)

        var value = Here.getFloat("float")
        assertEquals(value, java.lang.Float.MAX_VALUE, Delta)

        value = Here.getFloat(KeyNotExist)
        assertEquals(value, 0.0f, Delta)

        value = Here.getFloat(KeyNotExist, -1.0f)
        assertEquals(value, -1.0f, Delta)
    }

    @Test
    fun testDouble() {
        Here.put("double", Double.MAX_VALUE)

        var value = Here.getDouble("double")
        assertEquals(value, Double.MAX_VALUE, Delta.toDouble())

        value = Here.getDouble(KeyNotExist)
        assertEquals(value, 0.toDouble(), Delta.toDouble())

        value = Here.getDouble(KeyNotExist, -1.0)
        assertEquals(value, (-1).toDouble(), Delta.toDouble())
    }

    @Test
    fun testString() {
        val str = "Hello 2018 world cup 世界杯"
        Here.put("string", str)

        var value = Here.getString("string")
        assertEquals(value, str)

        value = Here.getString(KeyNotExist)
        assertEquals(value, "")

        value = Here.getString(KeyNotExist, "Empty")
        assertEquals(value, "Empty")
    }

    @Test
    fun testStringSet() {
        var set = HashSet<String>()
        set.add("W")
        set.add("e")
        set.add("C")
        set.add("h")
        set.add("a")
        set.add("t")
        Here.put("string_set", set)

        var value: Set<String>? = Here.getStringSet("string_set")
        assertEquals(value, set)

        value = Here.getStringSet(KeyNotExist)
        assertEquals(value, null)

        set = HashSet()
        set.add("W")
        value = Here.getStringSet(KeyNotExist, set) as HashSet<String>
        assertEquals(value, set)
    }

    @Test
    fun testBytes() {
        val bytes = byteArrayOf('m'.toByte(), 'm'.toByte(), 'k'.toByte(), 'v'.toByte())
        Here.put("bytes", bytes)

        val value = Here.getByteArray("bytes")
        assertArrayEquals(value, bytes)
    }

//    @Test
//    fun testRemove() {
//        var ret = mmkv.encode("bool_1", true)
//        ret = ret and mmkv.encode("int_1", Integer.MIN_VALUE)
//        ret = ret and mmkv.encode("long_1", java.lang.Long.MIN_VALUE)
//        ret = ret and mmkv.encode("float_1", java.lang.Float.MIN_VALUE)
//        ret = ret and mmkv.encode("double_1", java.lang.Double.MIN_VALUE)
//        ret = ret and mmkv.encode("string_1", "hello")
//
//        val set = HashSet<String>()
//        set.add("W")
//        set.add("e")
//        set.add("C")
//        set.add("h")
//        set.add("a")
//        set.add("t")
//        ret = ret and mmkv.encode("string_set_1", set)
//
//        val bytes = byteArrayOf('m'.toByte(), 'm'.toByte(), 'k'.toByte(), 'v'.toByte())
//        ret = ret and mmkv.encode("bytes_1", bytes)
//        assertEquals(ret, true)
//
//        run {
//            val count = mmkv.count()
//
//            mmkv.removeValueForKey("bool_1")
//            mmkv.removeValuesForKeys(arrayOf("int_1", "long_1"))
//
//            val newCount = mmkv.count()
//            assertEquals(count, newCount + 3)
//        }
//
//        val bValue = mmkv.decodeBool("bool_1")
//        assertEquals(bValue, false)
//
//        val iValue = mmkv.decodeInt("int_1")
//        assertEquals(iValue, 0)
//
//        val lValue = mmkv.decodeLong("long_1")
//        assertEquals(lValue, 0)
//
//        val fValue = mmkv.decodeFloat("float_1")
//        assertEquals(fValue, java.lang.Float.MIN_VALUE, Delta)
//
//        val dValue = mmkv.decodeDouble("double_1")
//        assertEquals(dValue, java.lang.Double.MIN_VALUE, Delta)
//
//        val sValue = mmkv.decodeString("string_1")
//        assertEquals(sValue, "hello")
//
//        val hashSet = mmkv.decodeStringSet("string_set_1") as HashSet<String>
//        assertEquals(hashSet, set)
//
//        val byteValue = mmkv.decodeBytes("bytes_1")
//        assertArrayEquals(bytes, byteValue)
//    }

//    @Test
//    fun testIPCUpdateInt() {
//        val mmkv = MMKV.mmkvWithID(MMKVTestService.SharedMMKVID, MMKV.MULTI_PROCESS_MODE)
//        mmkv.encode(MMKVTestService.SharedMMKVKey, 1024)
//
//        val appContext = InstrumentationRegistry.getTargetContext()
//        val intent = Intent(appContext, MMKVTestService::class.java)
//        intent.putExtra(MMKVTestService.CMD_Key, MMKVTestService.CMD_Update)
//        appContext.startService(intent)
//
//        SystemClock.sleep((1000 * 3).toLong())
//        val value = mmkv.decodeInt(MMKVTestService.SharedMMKVKey)
//        assertEquals(value, 1024 + 1)
//    }

//    @Test
//    fun testIPCLock() {
//        val appContext = InstrumentationRegistry.getTargetContext()
//
//        val intent = Intent(appContext, MMKVTestService::class.java)
//        intent.putExtra(MMKVTestService.CMD_Key, MMKVTestService.CMD_Lock)
//        appContext.startService(intent)
//
//        SystemClock.sleep((1000 * 3).toLong())
//        val mmkv = MMKV.mmkvWithID(MMKVTestService.SharedMMKVID, MMKV.MULTI_PROCESS_MODE)
//        var ret = mmkv.tryLock()
//        assertEquals(ret, false)
//
//        intent.putExtra(MMKVTestService.CMD_Key, MMKVTestService.CMD_Kill)
//        appContext.startService(intent)
//
//        SystemClock.sleep((1000 * 3).toLong())
//        ret = mmkv.tryLock()
//        assertEquals(ret, true)
//    }
}
