package com.example.github_issues

import java.util.*
import android.os.Bundle
import android.view.View
import java.io.IOException
import com.squareup.okhttp.*
import android.view.ViewGroup
import android.content.Context
import io.reactivex.Completable
import android.view.LayoutInflater
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import android.annotation.SuppressLint
import io.reactivex.schedulers.Schedulers
import com.example.github_issues.entity.Issue
import com.example.github_issues.adapter.IssueAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.issue_item.view.*
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.android.synthetic.main.fragment_issue_list.*


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

        // This approach doesn't work when use try navigate to previous screen and click in issue again
        Completable.complete()
            .delay(2, TimeUnit.SECONDS, Schedulers.io())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onStart() {
                    requestIssues(context)
                }

                override fun onError(error: Throwable) {
                    error.printStackTrace()
                }

                override fun onComplete() {
                    setListenerToEachItemOnTheList(view)
                }
            })

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



