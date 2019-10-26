package org.realstudio.here.impl

class Bucket(val name: String, val relativePath: String? = null)

object BucketFactory {
    val Global = Bucket("global")
}
