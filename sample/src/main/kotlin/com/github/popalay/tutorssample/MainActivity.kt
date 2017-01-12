package com.github.popalay.tutorssample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.popalay.tutors.TutorialDialog
import com.github.popalay.tutors.TutorialListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val tutorials: MutableMap<String, View> = HashMap()
    private lateinit var dialog: TutorialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTutorials()
        initViews()
    }

    private fun initTutorials() {
        tutorials.put("It's a toolbar", toolbar)
        tutorials.put("It's a button", button_hello)
        tutorials.put("It's a borderless button", button_ok)
        tutorials.put("It's a text", text_description)
    }

    private fun initViews() {

        setSupportActionBar(toolbar)

        dialog = TutorialDialog.create {
            textColorRes = android.R.color.white
            shadowColorRes = R.color.shadow
            textSizeRes = R.dimen.textNormal
            completeIconRes = R.drawable.ic_cross_24_white
            nextButtonTextRes = R.string.action_next
            completeButtonTextRes = R.string.action_got_it
            spacingRes = R.dimen.spacingNormal
            lineWidthRes = R.dimen.lineWidth
            cancelable = false
        }

        var iterator: MutableIterator<MutableMap.MutableEntry<String, View>>? = null

        button_show.setOnClickListener {
            iterator = tutorials.iterator()
            showTutorial(iterator)

        }

        dialog.setTutorialListener(object : TutorialListener {

            override fun onNext() {
                showTutorial(iterator)
            }

            override fun onComplete() {
                dialog.closeTutorial()
            }

            override fun onCompleteAll() {
                dialog.closeTutorial()
            }

        })
    }

    private fun showTutorial(iterator: MutableIterator<MutableMap.MutableEntry<String, View>>?) {
        iterator?.let {
            if (iterator.hasNext()) {
                val next = iterator.next()
                dialog.showTutorial(supportFragmentManager, next.value, next.key, !iterator.hasNext())
            }
        }
    }
}
