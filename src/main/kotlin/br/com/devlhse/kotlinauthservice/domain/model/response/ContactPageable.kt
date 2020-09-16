package br.com.devlhse.kotlinauthservice.domain.model.response

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable

data class ContactPageable (
    val contacts: List<ContactResponse>,
    val pageable: Pageable
)
