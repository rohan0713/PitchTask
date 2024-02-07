package com.social.pitchtask.data.models

data class task(
    val key : String,
    val title : String,
    val body : String,
    val isChecked : Boolean
) {
    constructor() : this("","", "", false)
}
