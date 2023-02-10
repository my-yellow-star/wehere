package api.epilogue.wehere.client.controller

import api.epilogue.wehere.client.resource.PrivacyPolicy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ViewController {
    @GetMapping("/health-check")
    fun healthCheck() = ""

    @GetMapping("/privacy-policy")
    fun privacyPolicy() = PrivacyPolicy.html
}