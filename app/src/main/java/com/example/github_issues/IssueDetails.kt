package com.example.github_issues

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [IssueDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class IssueDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_issue_details,
            container,
            false
        )
    }
}