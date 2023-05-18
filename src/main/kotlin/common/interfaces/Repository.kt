package common.interfaces

import java.util.Optional

interface Repository<T> {

    fun save(t: T): T

    fun findById(id: Any): Optional<T>

    fun deleteById(id: Any)

    fun findAll(): List<T>


}