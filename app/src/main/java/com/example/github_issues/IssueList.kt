package com.example.github_issues

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_issues.adapter.IssueAdapter
import com.example.github_issues.entity.Issue
import com.google.gson.GsonBuilder
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_issue_list.*
import kotlinx.android.synthetic.main.issue_item.view.*
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 * Use the [IssueList.newInstance] factory method to
 * create an instance of this fragment.
 */
class IssueList : Fragment() {
    private lateinit var issueAdapter: IssueAdapter

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

        val context = container?.context
        requestIssues(context, view)

        Observable
            .timer(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .map {
                setListenerToEachItemOnTheList(view)
            }
            .subscribe()


        return view
    }

    private fun requestIssues(context: Context?, view: View) {
        val promise = OkHttpClient()
        val promiseURL = "https://api.github.com/repos/JetBrains/kotlin/issues"
        val promiseBuilder = Request.Builder().url(promiseURL).build()

        promise.newCall(promiseBuilder).enqueue(object : Callback {
            override fun onFailure(request: Request?, expection: IOException?) {
                throw IllegalArgumentException()
            }

            override fun onResponse(response: Response) {
                val convert = GsonBuilder().create()
                val responseStr = response.body().string()

                val resJSON = convert
                    .fromJson(responseStr, Array<Issue>::class.java)
                    .toMutableList()


                activity?.runOnUiThread {
                    issueAdapter = IssueAdapter(resJSON)
                    rvIssueItems.adapter = issueAdapter
                    rvIssueItems.layoutManager = LinearLayoutManager(context)
                }
            }
        })

    }

    private fun setListenerToEachItemOnTheList(view: View) {
        view.issue_item.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToDetails)
        }
    }
}



