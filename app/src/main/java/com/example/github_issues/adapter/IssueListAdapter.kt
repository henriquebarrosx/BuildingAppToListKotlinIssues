package com.example.github_issues.adapter

import android.os.Build
import android.view.View
import com.google.gson.Gson
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.github_issues.R
import androidx.navigation.Navigation
import androidx.annotation.RequiresApi
import com.example.github_issues.entity.Issue
import androidx.recyclerview.widget.RecyclerView
import com.example.github_issues.interfaces.State
import com.example.github_issues.interfaces.DateFormat
import kotlinx.android.synthetic.main.issue_item.view.*
import com.example.github_issues.fragments.IssueListDirections

class IssueListAdapter(
  private val issues: MutableList<Issue>
): RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>(){

  class HandleState: State
  class ShortDate: DateFormat
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

  private fun compressText(title: String): String {
    val limitSize = 30
    val tooLong = title.length >= limitSize
    return if(tooLong) "${title.substring(0, limitSize)}..." else title
  }

  private fun setClickListener(element: View, currentIssue: Issue, view: View) {
    element.setOnClickListener {
      val convertDataClassToJson = Gson().toJson(currentIssue)

      val action = IssueListDirections.navigateToDetails(convertDataClassToJson.toString())
      Navigation.findNavController(view).navigate(action)
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
    val currentIssue = issues[position]

    holder.itemView.apply {
      issue_item_title.text = compressText(currentIssue.title)
      HandleState().getState(issue_item_status, currentIssue.state)
      issue_item_created_at.text = ShortDate().formatDate(currentIssue.created_at)

      setClickListener(issue_item, currentIssue, this)
    }
  }

}
