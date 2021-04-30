package com.example.github_issues

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.issue_item.view.*


class IssueAdapter(
    private val issues: MutableList<Issue>
): RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.issue_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    private fun handleStatusColor(tvIssueStatus: TextView, status: String) {
        val isOpened: Boolean = status == "open"
        val resource = getResourceByStatus(isOpened)
        tvIssueStatus.setBackgroundResource(resource)
    }

    private fun getResourceByStatus(isOpened: Boolean): Int {
        return if(isOpened) R.drawable.opened_issue else R.drawable.closed_issue
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val currentIssue = issues[position]

        holder.itemView.apply {
            issue_item_title.text = currentIssue.title
            issue_item_status.text = currentIssue.status
            issue_item_created_at.text = currentIssue.createdAt
            handleStatusColor(issue_item_status, currentIssue.status)
        }
    }
}