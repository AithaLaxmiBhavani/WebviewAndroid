
package com.syf.unifidemoandroid

import TipFormModal
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SyfPageActivity : AppCompatActivity() {
    private val TAG = "SyfPageActivity"
    private lateinit var syfWebView : WebView
    private lateinit var jsonPayload : String
    private lateinit var closeButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syf_page)

        // Custom code..
        supportActionBar?.hide()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        closeButton = findViewById(R.id.back_button)
        syfWebView = findViewById(R.id.syfWebView)
        val webSettings = syfWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.supportMultipleWindows()
        //syfWebView.loadUrl("file:///android_asset/androidNative.html")
        syfWebView.loadUrl("https://qpdpone.syfpos.com/mpp/syf-unifi-webview.html")
        syfWebView.webViewClient = UnifiWebViewClient()
        syfWebView.addJavascriptInterface(UnifiAndroidJavascriptIntf(this), "UnifiAndroidJSIntf")
        jsonPayload = intent.getStringExtra("jsonData").toString()
        closeButton.setOnClickListener{
            goBack()
        }
    }

    private inner class UnifiWebViewClient : WebViewClient() {
        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return if (url != null && (url.startsWith("https://dpdpone.syfpos.com/cs/")
                        || url.startsWith("https://www.synchrony.com/")
                        || url.startsWith("https://qbuy.syf.com/")) || url.startsWith("https://pdppone.syfpos.com/cs/")) {
                view.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                )
                true
            }else if(url.contains("tel:")){
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel://1-866-396-8254")
                startActivity(intent)
                true
            } else {
                false // open in-app
            }
        }
        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
            // showProgress(true)
            view.clearCache(true)
            super.onPageStarted(view, url, favicon)
        }
        override fun onPageFinished(view: WebView, url: String) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url)
            var jsonPayloadScript = "{syfPartnerId:\"PI53421676\"," +
                    "tokenId:\"PI5342167615725186eeeaa346\"," +
                    "encryptKey:\"\",modalType:\"\",childMid:\"\",childPcgc:\"\",childTransType:\"\",pcgc:\"\",partnerCode:\"\",clientToken:\"\",postbackid:\"d979e5b7-6382-4e4e-b269-aab027bbed58\",clientTransId:\"\",cardNumber:\"\",custFirstName:\"\",custLastName:\"\",expMonth:\"\",expYear:\"\",custZipCode:\"\",custAddress1:\"\",phoneNumb:\"\",appartment:\"\",emailAddr:\"\",custCity:\"\",upeProgramName:\"\",custState:\"\",transPromo1:\"\",iniPurAmt:\"\",mid:\"\",productCategoryNames:\"\",transAmount1:\"700\",transAmounts:\"\",initialAmount:\"\",envUrl:\"https://dpdpone.syfpos.com/mitservice/\",productAttributes:\"\",processInd:\"3\"}"
            //HdA/CECiAxRB8UjOp4cPDA==
            view.evaluateJavascript("javascript:loadJsonObjectAndroid($jsonPayload)",null)
            val cookies = CookieManager.getInstance().getCookie(url)
        }
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            val errorMessage = "Got Error! $error"
            super.onReceivedError(view, request, error)
        }
    }


    private inner class UnifiAndroidJavascriptIntf(private val context: Context) {
        private val TAG = "UnifiAndroidJavascriptI"
        @JavascriptInterface
        fun setStatusMessageFromJS(message: String) {
            if(message == "syf-close-modal") {
                goBack()
            }
        }
        @JavascriptInterface
        fun printMsg(message: String){
            println(message)
        }
    }

    override fun onBackPressed() {
        if(syfWebView.canGoBack()){
            syfWebView.goBack()
        }else{
            super.onBackPressed()
        }
    }
    private fun goBack(){
        val intent = Intent(this,MainActivity::class.java)
        this.startActivity(intent)
    }
}