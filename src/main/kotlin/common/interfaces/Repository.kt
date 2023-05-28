package common.interfaces

import java.util.Optional

/**
 * Repository
 *
 * @param T
 * @constructor Create empty Repository
 */
interface Repository<T> {


    /**
     * Save
     *
     * @param t
     * @return
     */
    fun save(t: T): T

    /**
     * Find by id
     *
     * @param id
     * @return
     */
    fun findById(id: Any): Optional<T>

    /**
     * Delete by id
     *
     * @param id
     */
    fun deleteById(id: Any)

    /**
     * Find all
     *
     * @return
     */
    fun findAll(): List<T>


}