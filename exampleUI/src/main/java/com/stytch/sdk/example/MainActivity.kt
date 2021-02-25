package com.stytch.sdk.example

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
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

        val textColor = StytchColor.fromColor(Color.parseColor("#6E471F"))
        val placeholderTextColor = StytchColor.fromColorId( R.color.placeholderTextColor)
        val inputBackgroundColor =  StytchColor.fromColorId(R.color.inputBackgroundColor)
        val backgroundColor =  StytchColor.fromColorId(R.color.backgroundColor)

        return StytchUICustomization().apply {

            titleStyle.color = textColor
            titleStyle.size = 25.dp
            titleStyle.font = Typeface.create(null as Typeface?, Typeface.BOLD)

            subtitleStyle.color = textColor
            subtitleStyle.size = 18.dp
            subtitleStyle.font = Typeface.create(null as Typeface?, Typeface.NORMAL)

            inputTextStyle.color = textColor
            inputTextStyle.size = 16.dp
            inputTextStyle.font = Typeface.create(null as Typeface?, Typeface.NORMAL)
            inputHintStyle.color = placeholderTextColor
            inputHintStyle.size = 15.dp
            inputHintStyle.font = Typeface.create(null as Typeface?, Typeface.NORMAL)
            this.inputBackgroundColor = inputBackgroundColor
            inputBackgroundBorderColor = textColor
            inputCornerRadius = 8.dp

            buttonTextStyle.color = StytchColor.fromColorId(R.color.white)
            buttonTextStyle.size = 20.dp
            buttonTextStyle.font = Typeface.create(null as Typeface?, Typeface.BOLD)
            buttonBackgroundColor = textColor
            buttonCornerRadius = 8.dp

            this.backgroundColor = backgroundColor
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
