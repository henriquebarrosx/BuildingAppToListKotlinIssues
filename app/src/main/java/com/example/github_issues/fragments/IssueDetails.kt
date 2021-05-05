package com.example.github_issues.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.github_issues.R
import androidx.fragment.app.Fragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.fragment.navArgs
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_issue_details.view.*

class IssueDetails : Fragment() {
    private val args: IssueDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_issue_details,
            container,
            false
        )

        val issue = args.issueArgs
        view.details_text.text = issue

        val navController = findNavController()
        NavigationUI.setupActionBarWithNavController(this.activity as AppCompatActivity, navController)

        return view
    }


}