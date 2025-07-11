package com.example.todowebview

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen() {
    var webView: WebView? by remember { mutableStateOf(null) }

    // This is the core of the composable.
    // It uses AndroidView to host a classic Android View (our WebView)
    // inside the Compose UI tree.
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // Apply settings to the WebView
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // This is crucial to prevent links from opening in an external browser
                webViewClient = WebViewClient()

                // Enable JavaScript. THIS IS ESSENTIAL for our to-do list to work.
                settings.javaScriptEnabled = true

                // Load our local HTML file from the assets folder
                // loadUrl("file:///android_asset/index.html")
                loadUrl("https://bddairy.com/admin/login")

                // Assign the created webview to our state variable
                webView = this
            }
        },
        update = {
            // This block is called when the composable is recomposed.
            // We can update the webView here if needed.
            // For this example, we don't need to do anything here.
        }
    )

    // Handle the back button press. If the WebView can go back (e.g., has a history),
    // we want the back button to navigate within the WebView, not exit the app.
    BackHandler(enabled = true) {
        webView?.let {
            if (it.canGoBack()) {
                it.goBack()
            }
        }
        // If you want the app to close when the WebView can't go back,
        // you would need to call `(context as Activity).finish()` or similar logic.
        // For simplicity, this implementation will just consume the back press if it can't go back.
    }
}