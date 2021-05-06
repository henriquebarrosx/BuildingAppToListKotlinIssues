package com.example.github_issues.fragments

import java.util.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import android.view.ViewGroup
import android.content.Intent
import android.view.LayoutInflater
import com.example.github_issues.R
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.navigation.ui.NavigationUI
import androidx.navigation.fragment.navArgs
import com.example.github_issues.entity.Issue
import androidx.appcompat.app.AppCompatActivity
import com.example.github_issues.interfaces.State
import androidx.navigation.fragment.findNavController
import com.example.github_issues.interfaces.DateFormat
import kotlinx.android.synthetic.main.fragment_issue_details.view.*

class IssueDetails : Fragment() {
    class ShortDate: DateFormat
    class HandleState: State

    private val args: IssueDetailsArgs by navArgs()

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_issue_details,
            container,
            false
        )

        val issue: String = args.issueArgs
        val issueDataClass = Gson().fromJson(issue, Issue::class.java)

        println("issueJSON: $issueDataClass")
        renderContext(view, issueDataClass)

        val navController = findNavController()
        NavigationUI.setupActionBarWithNavController(this.activity as AppCompatActivity, navController)

        return view
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderContext(view: View, issue: Issue) {
        val description = if(issue.body?.isNotEmpty() == true) issue.body else "Nenhuma descrição fornecida."

        view.issue_item_title.text = issue.title
        view.issue_item_description.text = description
        view.issue_item_owner_name.text = issue.user.login.capitalize(Locale.ROOT)
        view.issue_item_created_at.text = ShortDate().formatDate(issue.created_at)

        view.show_on_web_btn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(issue.html_url))
            startActivity(browserIntent)
        }

        HandleState().getState(view.issue_item_details_status, issue.state)
    }


}
