package common.implementation

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RepositoryImplTest {

    private val repository = RepositoryImpl<String>()

    @Test
    fun save() {
        assertDoesNotThrow{ repository.save("string")}
    }

    @Test
    fun findById() {
        repository.save("s")

        assertNotNull(repository.findById(1))
    }

    @Test
    fun deleteById() {
        assertDoesNotThrow{ repository.deleteById(1)}
    }

    @Test
    fun findAll() {
        assertDoesNotThrow{ repository.findAll()}
    }
}