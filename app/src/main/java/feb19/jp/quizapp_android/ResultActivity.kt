package feb19.jp.quizapp_android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent = getIntent()
        scoreTextView.text = intent.getStringExtra(MainActivity.EXTRA_SCORE)
    }

    fun again(t: View) {
        finish()
    }
}
