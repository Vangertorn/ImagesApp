package com.vangertorn.imagesapp.util.extension

import com.vangertorn.imagesapp.R
import retrofit2.HttpException
import java.net.UnknownHostException

object ExceptionParser {

    fun getMessage(exception: Exception): Int {
        return when (exception) {
            is HttpException -> getHttpErrorMessage(exception)
            is UnknownHostException -> internetConnectionError()
            else -> generalError()
        }
    }

    private fun getHttpErrorMessage(exception: HttpException): Int {
        return when (exception.code()) {
            404 -> R.string.error_not_found
            else -> generalError()
        }
    }

    private fun generalError() = R.string.error_something_went_wrong

    private fun internetConnectionError() = R.string.error_internet_connection
}
