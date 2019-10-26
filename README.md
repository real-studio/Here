## Here

Here 是基于腾讯[MMKV](https://github.com/Tencent/MMKV)实现便于Android使用的Key-Value存储库。MMKV自身非常高效，其内部使用mmap同步内存数据到文件，使用protobuf对数据进行encode/decode，读写性能远优于SharedPreference，在Android上完全可以替代SharedPreference使用。我们基于这些特性，进行了上层的封装与拓展，使其在Android上使用更加高效便利。性能请参考[MMKV android benchmark](https://github.com/Tencent/MMKV/wiki/android_benchmark)。  

![Baseline Performance](https://github.com/Tencent/MMKV/wiki/assets/profile_multiprocess.jpg)
	
### Usage:

#### 1. 基本用法

```kotlin
// 在使用前或在Application进行初始化
Here.init(this)
// 在需要存储值的地方直接调用Here.put(key, value)方法
Here.put("text", 100)
// 取值
Here.getInt("text")
// or
Here.getInt("text", -1)
```

#### 2. 基于bucket进行存储
bucket相当于命名空间，不同的bucket下同名的key并不冲突

```
// init, 全局初始化一次即可，不需要每次都初始化
Here.init(this)
	
Here.bucket("bucket_name").put("test", 10)
													.put("test2", "value2")
													
val value = Here.bucket("bucket_name").getInt("test")

// 可以使用kotlin lambda设置
Here.bucket("bucket_name"){
	put("key1", "value1")
	put("key2", "value2")
}	
```

####  3. 删除数据

```
// 该方法只会删除全局的key-value, 不会删除bucket上的数据
Here.clearAll()

// clearAll, 只会删除bucket_name上的数据
Here.bucket("bucket_name").clearAll()
	
// 删除某个值
Here.bucket("bucket_name").removeValueForKey("key")
	
```

#### 4. 支持的存储类型
* List<E: Parcelable>
* E: Parcelable
* Boolean
* Int
* Long
* Float
* Double
* String
* ByteArray
* `Set<String>`
   

