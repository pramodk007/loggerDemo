package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Logger.addLogAdapter(AndroidLogAdapter())
            Logger.d("message")
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    Logger.clearLogAdapters();

    var formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
        .methodCount(0) // (Optional) How many method line to show. Default 2
        .methodOffset(3) // (Optional) Skips some method invokes in stack trace. Default 5
        //        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
        .tag("My custom tag") // (Optional) Custom tag for each log. Default PRETTY_LOGGER
        .build()

    formatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(false)
        .methodCount(0)
        .build();

    Logger.addLogAdapter(AndroidLogAdapter(formatStrategy));

    Logger.addLogAdapter(object : AndroidLogAdapter() {
        override fun isLoggable(priority: Int, tag: String?): Boolean {
            return BuildConfig.DEBUG
        }
    })

    Logger.w("no thread info and only 1 method");
    Logger.i("no thread info and method info");
    Logger.t("tag").e("Custom tag for only one use");
    Logger.json("{ \"key\": 3, \"value\": something}");
    Logger.d(Arrays.asList("foo", "bar"));

    Logger.clearLogAdapters();
    formatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(false)
        .methodCount(0)
        .tag("MyTag")
        .build();
    Logger.addLogAdapter(AndroidLogAdapter(formatStrategy));

    Logger.w("my log message with my tag");
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}