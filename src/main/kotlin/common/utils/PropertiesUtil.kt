package common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.Files
import java.nio.file.Paths

fun parse(): Configuration {
    val path = Paths.get("src/main/resources/", "application.yaml")
    val mapper = ObjectMapper(YAMLFactory())
    mapper.registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )

    return try {
        Files.newBufferedReader(path).use {
            mapper.readValue(it, Configuration::class.java)
        }
    } catch (exception: MismatchedInputException) {
        println("Could not read YAML file!")
        println(exception.message)
        throw exception
    }
}
