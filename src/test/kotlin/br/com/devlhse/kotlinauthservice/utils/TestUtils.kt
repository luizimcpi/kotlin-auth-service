package br.com.devlhse.kotlinauthservice.utils

import java.io.File

object TestUtils {

    fun port(url: String) = url.split(":")[2].toInt()

    fun viaCepResponseSample(sampleName: String) = readSample("viacep/responses/$sampleName")

    private fun readSample(path: String): String {
        val fullPath = "/samples/$path"
        val uri = TestUtils::class.java.getResource(fullPath).toURI()
        return File(uri).readText(Charsets.UTF_8)
    }
}