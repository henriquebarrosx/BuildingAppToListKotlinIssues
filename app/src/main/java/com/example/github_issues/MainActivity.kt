package com.example.github_issues

import android.content.Context
import android.os.Bundle
import java.io.IOException
import com.squareup.okhttp.*
import com.google.gson.GsonBuilder
import java.lang.IllegalArgumentException
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var issueAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestIssues(this)
    }

    private fun requestIssues(context: Context) {
        val promiseURL = "https://api.github.com/repos/JetBrains/kotlin/issues"
        val promiseBuilder = Request.Builder().url(promiseURL).build()
        val promise = OkHttpClient()

        promise.newCall(promiseBuilder).enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
                throw IllegalArgumentException()
            }

            override fun onResponse(response: Response) {
                val convert = GsonBuilder().create()
                val resStr = response.body().string()

                val resJSON = convert
                        .fromJson(resStr, Array<Issue>::class.java)
                        .toMutableList()

                runOnUiThread {
                    issueAdapter = IssueAdapter(resJSON)
                    rvIssueItems.adapter = issueAdapter
                    rvIssueItems.layoutManager = LinearLayoutManager(context)
                }
            }
        })


    }
}




