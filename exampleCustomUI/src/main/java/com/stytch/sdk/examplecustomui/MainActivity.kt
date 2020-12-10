package com.stytch.sdk.examplecustomui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.snackbar.Snackbar
import com.stytch.sdk.Stytch
import com.stytch.sdk.StytchEnvironment
import com.stytch.sdk.StytchError
import com.stytch.sdk.StytchLoginMethod
import com.stytch.sdk.api.StytchResult

private const val PROJECT_ID = "PROJECT_ID"//"Your Id"
private const val SECRET = "SECRET"//"Your secret"
private const val SCHEME = "https"
private const val HOST = "custom.ui.stytch.com"

class MainActivity : AppCompatActivity(), Stytch.StytchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureStytch()
    }


    private fun configureStytch() {
//        Initialize sdk with required parameters
        Stytch.instance.configure(PROJECT_ID, SECRET, SCHEME, HOST)
//        Set environment
        Stytch.instance.environment = StytchEnvironment.TEST
//        Setting login method
        Stytch.instance.loginMethod = StytchLoginMethod.LoginOrSignUp
//        Set listener to listen for events
        Stytch.instance.listener = this
    }

    fun signInClicked(view: View) {
        val email = findViewById<AppCompatEditText>(R.id.valueEditText).text?.toString() ?: return
//        show loading
        Stytch.instance.login(email)
    }

//        handle deep link
    override fun onNewIntent(intent: Intent?) {
        val action: String? = intent?.action
        val data = intent?.data ?: return
        if (action == Intent.ACTION_VIEW) {
            Stytch.instance.handleDeepLink(data)
        }
        super.onNewIntent(intent)
    }


//        Stytch.StytchListener implementation

    override fun onSuccess(result: StytchResult) {
//        handle success
        Toast.makeText(this, "Success! userId: ${result.userId}", Toast.LENGTH_LONG).show()
    }

    override fun onFailure(error: StytchError) {
//        handle failure
        Toast.makeText(this, "Oops...", Toast.LENGTH_LONG).show()
    }

    override fun onMagicLinkSent(email: String) {
//        hide loading
//        inform user about sent magic link
    }

}
