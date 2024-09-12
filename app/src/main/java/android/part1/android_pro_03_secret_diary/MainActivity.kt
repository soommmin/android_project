package android.part1.android_pro_03_secret_diary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.part1.android_pro_03_secret_diary.databinding.ActivityMainBinding
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var changePasswordMode = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNumberPicker()
        initOpenButton()
        initChangePasswordButton()
    }

    private fun initNumberPicker(){
        fun setupNumberPicker(numberPicker: NumberPicker) {
            numberPicker.apply {
                minValue = 0
                maxValue = 9
            }
        }

        setupNumberPicker(binding.firstNumberPicker)
        setupNumberPicker(binding.secondNumberPicker)
        setupNumberPicker(binding.thirdNumberPicker)
    }

    private fun initOpenButton(){
        binding.openButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 모드입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val sharedPreferences = getSharedPreferences("password",Context.MODE_PRIVATE)
            val password = "${binding.firstNumberPicker.value}${binding.secondNumberPicker.value}${binding.thirdNumberPicker.value}"

            if (password == sharedPreferences.getString("password", "000")) {
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showErrorPopup()
            }
        }
    }

    private fun initChangePasswordButton() = with(binding){
        binding.changePasswordButton.setOnClickListener{
            val sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            if (changePasswordMode) {
                sharedPreferences.edit {
                    this.putString("password", "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}")
                    commit()
                }
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)
            }else{
                val password = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

                if (password != sharedPreferences.getString("password", "000")) {
                    showErrorPopup()
                    return@setOnClickListener
                }
                changePasswordButton.setBackgroundColor(Color.RED)
                Toast.makeText(this@MainActivity, "변경할 비밀번호를 입력하고 다시 눌러주세요.", Toast.LENGTH_SHORT).show()
                changePasswordMode = true
            }

        }
    }

    private fun showErrorPopup(){
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}