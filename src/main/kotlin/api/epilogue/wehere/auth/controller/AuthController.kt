package api.epilogue.wehere.auth.controller

import api.epilogue.wehere.auth.application.PasswordAuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val passwordAuthService: PasswordAuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody input: EmailPasswordInput) {
        passwordAuthService.register(input.email, input.password)
    }

    @PostMapping("/login")
    fun login(@RequestBody input: EmailPasswordInput) =
        passwordAuthService.login(input.email, input.password)
}