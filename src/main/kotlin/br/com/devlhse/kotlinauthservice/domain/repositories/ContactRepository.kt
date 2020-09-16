package br.com.devlhse.kotlinauthservice.domain.repositories

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.Contact
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactPageable
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactResponse


interface ContactRepository {

   fun findByUser(userId: Int, pageable: Pageable): ContactPageable
   fun save(contact: Contact)
   fun findById(userId: Int, id: Int): ContactResponse?
   fun update(id: Int, userId: Int, contact: Contact)
   fun delete(id: Int, userId: Int)
}
