package com.example.github_issues

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var issueAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapterModules()
    }

    private fun initAdapterModules() {
        requestIssues()

        issueAdapter = IssueAdapter(
            mutableListOf(
                Issue(
                    "This is a opened issue",
                    "open",
                    "13d"
                ),
                Issue(
                    "This is a closed issue",
                    "closed",
                    "1d"
                )
            )
        )

        rvIssueItems.adapter = issueAdapter
        rvIssueItems.layoutManager = LinearLayoutManager(this)
    }

    private fun requestIssues() {
        val promiseURL = "https://api.github.com/repos/JetBrains/kotlin/issues"
        val promiseBuilder = Request.Builder().url(promiseURL).build()
        val promise = OkHttpClient()

        val data = mutableListOf<Issue>()

        promise.newCall(promiseBuilder).enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
            }

            override fun onResponse(response: Response) {
            }
        })

    }
}




