package br.com.devlhse.kotlinauthservice.domain.model.dto

data class Pageable(val pageNumber: Long, val pageSize: Int, val totalPages: Long? = null)