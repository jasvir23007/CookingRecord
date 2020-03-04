package com.jasvir.cookingrecord.model

data class Response(
    val code: Int,
    val etag: String,
    val cooking_records: List<Character>
)