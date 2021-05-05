package com.example.github_issues.adapter

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.time.LocalDateTime
import android.view.LayoutInflater
import com.example.github_issues.R
import java.time.temporal.ChronoUnit
import androidx.navigation.Navigation
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter
import com.example.github_issues.entity.Issue
import androidx.recyclerview.widget.RecyclerView
import com.example.github_issues.IssueListDirections
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

    private fun compressText(title: String): String {
        val limitSize = 30
        val tooLong = title.length >= limitSize
        return if(tooLong) "${title.substring(0, limitSize)}..." else title
    }

    private fun getResourceByStatus(isOpened: Boolean): Int {
        return if(isOpened) R.drawable.opened_issue else R.drawable.closed_issue
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(date: String): String {
        val today = LocalDateTime.now()

        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val parsedDate = LocalDateTime.parse(date, dateFormat)

        val formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val nowStr = LocalDateTime.parse(formatter.format(today), formatter)

        return manageTime(parsedDate, nowStr)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageTime(createdAt: LocalDateTime, now: LocalDateTime): String {
        val minutes: Int = getMinutes(createdAt, now)
        val hours: Int = getHours(createdAt, now)
        val days: Int = getDays(createdAt, now)
        val months: Int = getMonths(createdAt, now)
        val years: Int = getYears(createdAt, now)

        return when {
            days <= 0 && hours < 1 -> "${minutes}m"
            days <= 0 && hours >= 1 -> "${hours}h"
            days <= 30 -> "${days}d"
            days > 30 -> "${months}mo"
            days > 365 -> "${years}y"
            else -> "${days}d"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMinutes(startDate: LocalDateTime, endDate: LocalDateTime): Int {
        return ChronoUnit.MINUTES.between(startDate, endDate).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getHours(startDate: LocalDateTime, endDate: LocalDateTime): Int {
        return ChronoUnit.HOURS.between(startDate, endDate).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDays(startDate: LocalDateTime, endDate: LocalDateTime): Int {
        return ChronoUnit.DAYS.between(startDate, endDate).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMonths(startDate: LocalDateTime, endDate: LocalDateTime): Int {
        return ChronoUnit.MONTHS.between(startDate, endDate).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getYears(startDate: LocalDateTime, endDate: LocalDateTime): Int {
        return ChronoUnit.YEARS.between(startDate, endDate).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val currentIssue = issues[position]

        holder.itemView.apply {
            issue_item_status.text = currentIssue.state
            issue_item_title.text = compressText(currentIssue.title)
            issue_item_created_at.text = formatDate(currentIssue.created_at)
            handleStatusColor(issue_item_status, currentIssue.state)

            issue_item.setOnClickListener {
                val action = IssueListDirections.navigateToDetails(currentIssue.toString())
                Navigation.findNavController(this).navigate(action)
            }
        }
    }
}
