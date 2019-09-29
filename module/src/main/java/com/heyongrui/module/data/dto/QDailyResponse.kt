package com.heyongrui.module.data.dto

data class QDailyResponse<T>(
        var meta: Meta,
        var response: T) {

    data class Meta(
            var msg: String,
            var status: Int
    )
}