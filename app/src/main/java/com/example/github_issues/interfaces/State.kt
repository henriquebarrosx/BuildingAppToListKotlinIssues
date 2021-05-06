package com.example.github_issues.interfaces

import android.widget.TextView
import com.example.github_issues.R

interface State {
  fun getState(tvIssueStatus: TextView, status: String) {
    tvIssueStatus.text = status

    val isOpened: Boolean = status == "open"
    val resource = getResourceByStatus(isOpened)
    tvIssueStatus.setBackgroundResource(resource)
  }

  private fun getResourceByStatus(isOpened: Boolean): Int {
    return if(isOpened) R.drawable.opened_issue else R.drawable.closed_issue
  }
}