package com.william.smartnews.api

/**
 * Created by Willy on 07/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class ApiModelList<T> {
    private lateinit var status: Status
    private val data: List<T> = emptyList()

    fun getData(): List<T> {
        return data
    }

    fun getCode(): Int {
        return status.statusCode
    }

    fun getMessage(): String? {
        return status.message
    }
}