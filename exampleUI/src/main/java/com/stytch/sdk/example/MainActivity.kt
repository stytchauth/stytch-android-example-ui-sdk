package com.stytch.sdk.example

import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stytch.sdk.*
import com.stytch.sdk.api.StytchResult
import com.stytch.sdk.ui.StytchMainActivity

private const val PROJECT_ID = "PROJECT_ID"//"Your Id"
private const val SECRET = "SECRET"//"Your secret"
private const val SCHEME = "https"
private const val HOST = "test.stytch.com"

class MainActivity : AppCompatActivity(), StytchUI.StytchUIListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureStytch()
        findViewById<View>(R.id.textView).setOnClickListener {
            launchStytch()
        }
    }

    private fun launchStytch() {
        StytchUI.instance.uiListener = this
        StytchUI.instance.uiCustomization = createCustomization()
        startStytchActivity()
    }

    private fun startStytchActivity(){
        val intent = Intent(this, StytchMainActivity::class.java)
        startActivity(intent)
    }

    private fun createCustomization(): StytchUICustomization {

        val textColor = R.color.textColor
        val placeholderTextColor = R.color.placeholderTextColor
        val inputBackgroundColor = R.color.inputBackgroundColor
        val backgroundColor = R.color.backgroundColor

        return StytchUICustomization().apply {

            titleStyle.colorId = textColor
            titleStyle.size = 25.dp
            titleStyle.font = Typeface.create(null as Typeface?, Typeface.BOLD)

            subtitleStyle.colorId = textColor
            subtitleStyle.size = 18.dp
            subtitleStyle.font = Typeface.create(null as Typeface?, Typeface.NORMAL)

            inputTextStyle.colorId = textColor
            inputTextStyle.size = 16.dp
            inputTextStyle.font = Typeface.create(null as Typeface?, Typeface.NORMAL)
            inputHintStyle.colorId = placeholderTextColor
            inputHintStyle.size = 15.dp
            inputHintStyle.font = Typeface.create(null as Typeface?, Typeface.NORMAL)
            inputBackgroundColorId = inputBackgroundColor
            inputBackgroundBorderColorId = textColor
            inputCornerRadius = 8.dp

            buttonTextStyle.colorId = R.color.white
            buttonTextStyle.size = 20.dp
            buttonTextStyle.font = Typeface.create(null as Typeface?, Typeface.BOLD)
            buttonBackgroundColorId = textColor
            buttonCornerRadius = 8.dp

            backgroundId = backgroundColor
            showTitle = true
            showSubtitle = true
            showBrandLogo = true
        }
    }

    private fun configureStytch() {
//        Initialize sdk with required parameters
        Stytch.instance.configure(PROJECT_ID, SECRET, SCHEME, HOST)
//        Set environment
        Stytch.instance.environment = StytchEnvironment.TEST
//        Setting login method
        Stytch.instance.loginMethod = StytchLoginMethod.LoginOrSignUp
    }


//        StytchUI.StytchUIListener implementation

    override fun onSuccess(result: StytchResult) {
//        handle success
        Log.d("TAG", "onSuccess: $result")
    }

    override fun onFailure() {
//        handle error
        Log.d("TAG", "onFailure()")
    }

    override fun onEvent(event: StytchEvent) {
        Log.d("TAG", "onEvent($event)")
    }
}

//        Extension to convert dp to pixels
val Number.dp: Float
    get() = this.toFloat() * Resources.getSystem().displayMetrics.density
