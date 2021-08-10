package com.stytch.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.stytch.sdk.Stytch
import com.stytch.sdk.StytchColor
import com.stytch.sdk.StytchEnvironment
import com.stytch.sdk.StytchUI
import com.stytch.sdk.StytchUICustomization
import com.stytch.sdk.dp

class MainActivity : AppCompatActivity() {
    lateinit var emailAuthButton: Button
    lateinit var smsAuthButton: Button
    lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stytch.configure(
            publicToken = "public-token-test-6906b71d-b14d-403c-becc-c4c88ac5fcfa",
            environment = StytchEnvironment.TEST
        )

        StytchUI.uiCustomization = StytchUICustomization(
            buttonCornerRadius = 7.dp,
            buttonEnabledBackgroundColor = StytchColor.fromColorId(R.color.purple_500),
            buttonDisabledBackgroundColor = StytchColor.fromColorId(R.color.purple_200),
            inputBackgroundColor = StytchColor.fromColorId(R.color.design_default_color_background),
            inputBackgroundBorderColor = StytchColor.fromColorId(R.color.design_default_color_secondary_variant),
            inputCornerRadius = 3.dp,
            backgroundColor = StytchColor.fromColorId(R.color.design_default_color_background),
        )

        emailAuthButton = findViewById(R.id.email_auth_button)
        smsAuthButton = findViewById(R.id.sms_auth_button)
        resultTextView = findViewById(R.id.result_text_view)

        emailAuthButton.setOnClickListener {
            StytchUI.EmailMagicLink.configure(
                loginMagicLinkUrl = "https://test.stytch.com/login",
                signupMagicLinkUrl = "https://test.stytch.com/signup",
                createUserAsPending = true,
                authenticator = { token ->
                    showResult("Received token '$token'")
                    // Send the token to your own backend here to validate
                    // For demonstration purposes, we will skip this step in this demo app and just set a constant boolean
                    // Set this value to 'false' for a demonstration of what happens if token authentication fails
                    val tokenAuthenticated = true
                    if (tokenAuthenticated) {
                        StytchUI.onTokenAuthenticated()
                    } else {
                        StytchUI.onTokenAuthenticationFailed()
                    }
                }
            )

            val stytchIntent = StytchUI.EmailMagicLink.createIntent(this)
            startActivity(stytchIntent)
        }

        smsAuthButton.setOnClickListener {
            StytchUI.SMSPasscode.configure(
                createUserAsPending = true,
                authenticator = { methodId, token ->
                    showResult("Received methodId '$methodId' and token '$token'")
                    // Send the methodId and token to your own backend here to validate the token
                    // For demonstration purposes, we will skip this step in this demo app and just set a constant boolean
                    // Set this value to 'false' for a demonstration of what happens if token authentication fails
                    val tokenAuthenticated = true
                    if (tokenAuthenticated) {
                        StytchUI.onTokenAuthenticated()
                    } else {
                        StytchUI.onTokenAuthenticationFailed()
                    }
                }
            )

            val stytchIntent = StytchUI.SMSPasscode.createIntent(this)
            startActivity(stytchIntent)
        }
    }

    private fun showResult(message: String) {
        resultTextView.text = message
        Log.d("StytchDemoAndroidApp", message)
    }
}
