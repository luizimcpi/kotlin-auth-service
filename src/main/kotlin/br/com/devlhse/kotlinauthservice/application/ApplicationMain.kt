package br.com.devlhse.kotlinauthservice.application

import br.com.devlhse.kotlinauthservice.application.web.AuthServiceEntryPoint

object ApplicationMain {

    @JvmStatic
    fun main(args: Array<String>){
        AuthServiceEntryPoint.init()
    }
}
