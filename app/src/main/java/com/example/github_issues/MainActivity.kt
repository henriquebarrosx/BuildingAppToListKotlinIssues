package com.example.github_issues

import android.os.Bundle
import java.io.IOException
import com.squareup.okhttp.*
import com.google.gson.GsonBuilder
import java.lang.IllegalArgumentException
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    private var issues = mutableListOf<Issue>()
    private lateinit var issueAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapterModules()
    }

    private fun initAdapterModules() {
        requestIssues()
        issueAdapter = IssueAdapter(issues)

        rvIssueItems.adapter = issueAdapter
        rvIssueItems.layoutManager = LinearLayoutManager(this)
    }

    private fun requestIssues() {
        val promiseURL = "https://api.github.com/repos/JetBrains/kotlin/issues"
        val promiseBuilder = Request.Builder().url(promiseURL).build()
        val promise = OkHttpClient()

        val data = promise.newCall(promiseBuilder).enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
                throw IllegalArgumentException()
            }

            override fun onResponse(response: Response) {
                if (response.isSuccessful) {
                    val convert = GsonBuilder().create()
                    val resStr = response.body().string()

                    val resJSON = convert
                        .fromJson(resStr, Array<Issue>::class.java)
                        .toMutableList()

                }
            }
        })

        println("-> $data")

    }
}




