package me.gilo.cocart.callback


class Resource<T> private constructor(private val data: T?, private val error: Exception?) {
    internal var status = Status.LOADING

    val isSuccessful: Boolean
        get() = data != null && error == null

    constructor(data: T) : this(data, null) {}

    constructor(status: Status) : this(null, null) {
        this.status = status
    }

    constructor(exception: Exception) : this(null, exception) {
        this.status = Status.ERROR
    }

    init {

        if (error != null) {
            status = Status.ERROR
        } else if (data != null) {
            if (data is List<*>) {
                if ((data as List<*>).size == 0) {
                    status = Status.EMPTY
                } else {
                    status = Status.SUCCESS
                }
            } else {
                status = Status.SUCCESS
            }
        } else {
            status = Status.LOADING
        }
    }

    fun data(): T? {
        if (error != null) {
            throw IllegalStateException("error is not null. Call isSuccessful() first.")
        }
        return data
    }

    fun error(): Exception? {
        if (data != null) {
            throw IllegalStateException("data is not null. Call isSuccessful() first.")
        }
        return error
    }

    fun status(): Status {
        return status
    }


}
