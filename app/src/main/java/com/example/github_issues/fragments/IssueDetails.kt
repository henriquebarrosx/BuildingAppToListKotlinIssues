package com.example.github_issues.fragments

import CircleTransform
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.github_issues.R
import com.example.github_issues.entity.Issue
import com.example.github_issues.interfaces.DateFormat
import com.example.github_issues.interfaces.State
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_issue_details.view.*
import java.util.*


class IssueDetails : Fragment() {
    class ShortDate: DateFormat
    class HandleIssueState: State

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

        renderContext(view, issueDataClass)
        addBtnUpOnActionBar(this.activity as AppCompatActivity)

        return view
    }

    private fun addBtnUpOnActionBar(activity: AppCompatActivity) {
        val navController = findNavController()
        NavigationUI.setupActionBarWithNavController(activity, navController)
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderContext(view: View, issue: Issue) {
        view.issue_item_title.text = issue.title
        view.issue_item_description.text = getDescription(issue)
        view.issue_item_owner_name.text = issue.user.login.capitalize(Locale.ROOT)
        view.issue_item_created_at.text = ShortDate().formatDate(issue.created_at)

        onPressBtnEvent(issue.html_url, view)
        loadThumbnail(issue.user.avatar_url, view)
        HandleIssueState().getState(view.issue_item_details_status, issue.state)
    }

    private fun getDescription(issue: Issue): String {
        return if(issue.body?.isNotEmpty() == true) issue.body else "Nenhuma descrição fornecida."
    }

    private fun loadThumbnail(thumbnail: String, view: View) {
        val ivBasicImage: ImageView = view.findViewById(R.id.issue_item_avatar);

        Picasso.with(context).load(thumbnail)
            .transform(CircleTransform())
            .into(ivBasicImage);
    }

    private fun onPressBtnEvent(url: String, view: View) {
        view.show_on_web_btn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }
}
