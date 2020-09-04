package br.com.devlhse.kotlinauthservice.domain.gateway

import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.stubs.ViaCepStub
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest
import org.koin.test.inject

@ExtendWith(ComponentTestExtension::class)
class AddressGatewayComponentTest(
    private val viaCepStub: ViaCepStub
) : KoinTest {

    private val addressGateway: AddressGateway by inject()

    private val cepRequest = "11035120"
    private val invalidCepRequest = "abc-120"

    @Test
    fun `when request to viacep with valid cep string should return valid response`() {
        viaCepStub.returnSuccess()

        assertDoesNotThrow {
            addressGateway.getAddressByCep(cepRequest)
        }
    }

    @Test
    fun `when request to viacep with invalid cep request string should throw exception`() {
        viaCepStub.returnBadRequest()

        assertThrows<Exception> {
            addressGateway.getAddressByCep(invalidCepRequest)
        }
    }
}