package br.com.devlhse.kotlinauthservice.domain.repositories

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.Contact
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactPageable


interface ContactRepository {

   fun findByUser(userId: Int, pageable: Pageable): ContactPageable
   fun save(contact: Contact)
}
