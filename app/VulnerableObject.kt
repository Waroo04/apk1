// VulnerableObject.kt
package com.example.apk1

import java.io.IOException
import java.io.ObjectInputStream
import java.io.Serializable

class VulnerableObject(private val data: String) : Serializable {
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(inputStream: ObjectInputStream) {
        inputStream.defaultReadObject()
        // Object Injection: Executing arbitrary code during deserialization
        Runtime.getRuntime().exec(data)
    }
}