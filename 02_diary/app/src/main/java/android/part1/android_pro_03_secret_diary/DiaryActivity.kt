package android.part1.android_pro_03_secret_diary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.part1.android_pro_03_secret_diary.databinding.ActivityDiaryBinding
import android.util.Log
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var binding: ActivityDiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDetailEditText()


    }

    private fun initDetailEditText() {


        val detail = getSharedPreferences("diary", Context.MODE_PRIVATE).getString("detail", "")
        binding.diaryEditText.setText(detail)

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit(true) {
                putString("detail", binding.diaryEditText.text.toString())
            }
        }

        binding.diaryEditText.addTextChangedListener {
            Log.e("ddd", "text changed :: $it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}