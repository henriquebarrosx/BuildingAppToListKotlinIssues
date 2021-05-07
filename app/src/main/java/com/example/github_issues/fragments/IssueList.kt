package com.example.github_issues.fragments

import java.util.*
import android.os.Bundle
import android.view.View
import java.io.IOException
import com.google.gson.Gson
import com.squareup.okhttp.*
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.example.github_issues.R
import androidx.fragment.app.Fragment
import android.annotation.SuppressLint
import com.example.github_issues.entity.Issue
import kotlinx.android.synthetic.main.issue_item.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_issues.adapter.IssueListAdapter
import kotlinx.android.synthetic.main.fragment_issue_list.*

class IssueList : Fragment() {
    private lateinit var issueListAdapter: IssueListAdapter

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_issue_list,
            container,
            false
        )

        requestIssues(context)
        return view
    }

    private fun requestIssues(context: Context?) {
        val promise = OkHttpClient()
        val promiseURL = "https://api.github.com/repos/JetBrains/kotlin/issues"

        val promiseBuilder = Request.Builder().url(promiseURL).build()

        promise.newCall(promiseBuilder).enqueue(object : Callback {
            override fun onFailure(request: Request?, expection: IOException?) {
                throw IllegalArgumentException()
            }

            override fun onResponse(response: Response) {
                val responseStr = response.body().string()
                val resJSON = Gson().fromJson(responseStr, Array<Issue>::class.java).toMutableList()

                activity?.runOnUiThread {
                    issueListAdapter = IssueListAdapter(resJSON)
                    rvIssueItems.adapter = issueListAdapter
                    rvIssueItems.layoutManager = LinearLayoutManager(context)
                }
            }
        })
    }
}



