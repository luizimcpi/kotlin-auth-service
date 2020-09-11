package br.com.devlhse.kotlinauthservice.domain.model.response

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.Contact

data class ContactPageable (
    val contacts: List<Contact>,
    val pageable: Pageable
)