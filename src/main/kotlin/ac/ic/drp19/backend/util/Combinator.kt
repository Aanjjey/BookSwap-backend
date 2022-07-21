package ac.ic.drp19.backend.util

private typealias Func<T, R> = (T) -> R
private typealias BiFunc<T, S, R> = (T, S) -> R

/**
 * Performs operations depending on whether the two given values are null or not.
 */
class Combinator<T, R> private constructor(
    private val firstArg: T?,
    private val secondArg: T?,
    private val none: () -> R
) {
    private var first: Func<T, R>? = null
    private var second: Func<T, R>? = null
    private var both: BiFunc<T, T, R>? = null

    companion object {
        fun <T, R> of(
            firstArg: T?,
            secondArg: T?,
            none: () -> R
        ): Combinator<T, R> {
            return Combinator(firstArg, secondArg, none)
        }
    }

    /**
     * Run this function if first is not null.
     */
    fun first(func: Func<T, R>): Combinator<T, R> {
        first = func
        return this
    }

    /**
     * Run this function if second is not null.
     */
    fun second(func: Func<T, R>): Combinator<T, R> {
        second = func
        return this
    }

    /**
     * Run this function if both first and second are not null.
     */
    fun both(func: BiFunc<T, T, R>): Combinator<T, R> {
        both = func
        return this
    }

    /**
     * Get the final value.
     * First check if both are not null. Return result of both().
     * Then check if first is not null. Return result of first().
     * Then check if second is not null. Return result of second().
     * Finally return none().
     */
    fun get(): R {
        if (both != null) {
            if (firstArg != null && secondArg != null) {
                return both!!.invoke(firstArg, secondArg)
            }
        }
        if (first != null) {
            if (firstArg != null) {
                return first!!.invoke(firstArg)
            }
        }
        if (second != null) {
            if (secondArg != null) {
                return second!!.invoke(secondArg)
            }
        }
        return none.invoke()
    }
}
