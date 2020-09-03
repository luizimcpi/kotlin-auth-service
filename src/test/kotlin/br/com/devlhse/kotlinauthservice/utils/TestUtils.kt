package br.com.devlhse.kotlinauthservice.utils

import java.io.File

object TestUtils {

    fun viaCepResponseSample(sampleName: String) = readSample("$sampleName")

    private fun readSample(path: String): String {
        val fullPath = "/samples/$path.json"
        val uri = TestUtils::class.java.getResource(fullPath).toURI()
        return File(uri).readText(Charsets.UTF_8)
    }
}