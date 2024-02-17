package com.pwc.azure_ad_poc

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val CHANNEL = "GET_ENTRA_TOKEN"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (call.method == "getEntraToken") {
                val token = getEntraToken()
                result.success(token)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun getEntraToken(): String {
        val token: String
        PublicClientApplication.createMultipleAccountPublicClientApplication(
            context as Context,
            R.raw.msal_config,
            object : IPublicClientApplication.IMultipleAccountApplicationCreatedListener {
                override fun onCreated(application: IMultipleAccountPublicClientApplication) {
                    mMultipleAccountApp = application
                }

                override fun onError(error: MsalException){
                }
            });
        return token
    }
}
