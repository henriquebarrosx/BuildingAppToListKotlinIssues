package com.example.github_issues.interfaces

import android.os.Build
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

interface DateFormat {
  @RequiresApi(Build.VERSION_CODES.O)
  fun formatDate(date: String): String {
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
}