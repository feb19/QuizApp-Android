package feb19.jp.quizapp_android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        val EXTRA_SCORE = "feb19.jp.quizapp_android.SCORE"
    }

    val quizSet = ArrayList<List<String>>()

    var currentQuiz = 0
    var score = 0
    var totalScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadQuizSet()
    }

    override fun onResume() {
        super.onResume()
        currentQuiz = 0
        score = 0
        goNextButton.text = "Go Next"
        setQuestion()
    }

    fun loadQuizSet() {
        val textFile = getAssets().open("quiz.txt")
        textFile.reader(charset = Charsets.UTF_8).forEachLine {
            if (it.isNotBlank()) {
                quizSet.add(it.split("\t"))
            }
        }
        score = 0
        totalScore = quizSet.count()

        setQuestion()
    }

    fun setQuestion() {
        scoreTextView.text = "score: ${score} / ${totalScore}"

        val list = quizSet.get(currentQuiz)
        var answers:ArrayList<String> = ArrayList<String>()
        for (i in 1 .. 3) {
            answers.add(list.get(i))
        }
        questionText?.text = list.get(0)
        Collections.shuffle(answers)

        a0button.text = answers.get(0)
        a1button.text = answers.get(1)
        a2button.text = answers.get(2)


        a0button.isEnabled = true
        a1button.isEnabled = true
        a2button.isEnabled = true
        goNextButton.isEnabled = false
    }

    fun checkAnswer(t: View) {
        Log.v("MainActivity", t.toString())

        val b:Button = t as Button
        val clicked = t.text.toString()

        if (clicked.equals(quizSet.get(currentQuiz)[1])) {
            t.text = "○ ${clicked}"
            score++
        } else {
            t.text = "× ${clicked}"
        }
        scoreTextView.text = "score: ${score} / ${totalScore}"

        a0button.isEnabled = false
        a1button.isEnabled = false
        a2button.isEnabled = false

        currentQuiz++

        goNextButton.isEnabled = true

        if (currentQuiz == quizSet.size) {
            goNextButton.text = "Check Result"
        }
    }

    fun goNext(t: View) {
        if (currentQuiz == quizSet.size) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_SCORE, "${score} / ${totalScore}")
            startActivity(intent)
        } else {
            setQuestion()
        }
    }
}
