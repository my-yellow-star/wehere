package api.epilogue.wehere.auth.infra

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory

class GoogleIdTokenPayloadVerifier(builder: Builder) :
    GoogleIdTokenVerifier(builder) {
    override fun verify(googleIdToken: GoogleIdToken?): Boolean {
        return super.verifyPayload(googleIdToken)
    }

    companion object {
        class Builder(transport: HttpTransport, jsonFactory: JsonFactory) :
            GoogleIdTokenVerifier.Builder(transport, jsonFactory) {
            override fun build(): GoogleIdTokenPayloadVerifier =
                GoogleIdTokenPayloadVerifier(this)
        }
    }
}