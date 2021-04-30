package com.example.github_issues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var issueAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapterModules()
    }

    private fun initAdapterModules() {
        issueAdapter = IssueAdapter(mutableListOf())

        rvIssueItems.adapter = issueAdapter
        rvIssueItems.layoutManager = LinearLayoutManager(this)
    }
}