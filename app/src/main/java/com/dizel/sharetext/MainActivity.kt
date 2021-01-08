package com.dizel.sharetext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var editText_text: EditText
    private lateinit var editText_quote: EditText
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        editText_text = findViewById(R.id.editText_text)
        editText_quote = findViewById(R.id.editText_quote)
        initExtra(intent)
    }

    fun onClickSend(view: View?) {
        if (editText_quote.text.isNotEmpty())
            sendText(editText_text.text.toString(),
                    editText_quote.text.toString())
    }

    private val TOKEN: String = "" // TOKEN HERE
    private val BASE_URL: String = "" // URL HERE
    private val VERSION: Int = 1

    private fun sendText(text: String, quote: String) {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()
                .create(ShareAPI::class.java)
                .notesAdd("notes.add", VERSION, TOKEN, quote, text)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("RestAPI", response.body()!!.string())
                            editText_quote.setText("")
                            editText_text.setText("")
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }

    private fun initExtra(intent: Intent?) {
        if (intent?.action == Intent.ACTION_SEND && "text/plain" == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                editText_quote.setText(it)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initExtra(intent)
    }
}