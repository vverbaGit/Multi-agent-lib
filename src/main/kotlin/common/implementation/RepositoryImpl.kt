package common.implementation

import common.interfaces.Repository
import java.util.*

class RepositoryImpl<T> : Repository<T> {

    private val list = mutableListOf<T>()

    override fun save(t: T): T {
        list.add(t)
        return t;
    }

    override fun findById(id: Any): Optional<T> {
        return list.stream().findAny();
    }

    override fun deleteById(id: Any) {
        println("Not implemented")
    }

    override fun findAll(): List<T> {
        return list
    }

}