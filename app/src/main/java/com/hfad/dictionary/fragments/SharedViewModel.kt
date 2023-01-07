package com.hfad.dictionary.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hfad.dictionary.R
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.models.card.Status

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

    }

    fun checkIfDatabaseEmpty(cardList: List<Card>) {
        emptyDatabase.value = cardList.isEmpty()
    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parseStatus(priority: String): Status {
        return when (priority) {
            "New Word" -> {
                Status.New
            }
            "Learning" -> {
                Status.Repeat
            }
            "Learned" -> {
                Status.Learned
            }
            else -> {
                Status.New
            }
        }

    }

    fun parseStatusToInt(status: Status): Int {
        return when (status) {
            Status.New -> 0
            Status.Repeat -> 1
            Status.Learned -> 2
        }
    }
}