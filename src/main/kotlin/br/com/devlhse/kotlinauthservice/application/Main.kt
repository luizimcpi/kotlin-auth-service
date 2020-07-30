package br.com.devlhse.kotlinauthservice.application

import br.com.devlhse.kotlinauthservice.application.web.AuthServiceEntryPoint

@SuppressWarnings("MemberNameEqualsClassName")
object Main {

    @JvmStatic
    fun main(args: Array<String>){
        AuthServiceEntryPoint.init()
    }
}
