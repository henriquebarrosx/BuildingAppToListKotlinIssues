package com.example.github_issues

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_issue_details.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [IssueDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        return view
    }
}