package api.epilogue.wehere.auth.infra

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "appleClient",
    url = "https://appleid.apple.com/auth",
)
interface AppleClient {
    @get:GetMapping(value = ["/keys"])
    val getPublicKeys: ApplePublicKeyResponse?
}

data class ApplePublicKeyResponse(
    val keys: List<ApplePublicKey>
) {
    fun getKeyMatched(kid: String, alg: String) =
        keys.first {
            it.kid == kid && it.alg == alg
        }
}

data class ApplePublicKey(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String
)