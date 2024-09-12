package android.part1.android_pro_01_lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.part1.android_pro_01_lotto.databinding.ActivityMainBinding
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()
    private val numberTextViewList: List<TextView> by lazy{
        listOf<TextView>(
            binding.textView1,
            binding.textView2,
            binding.textView3,
            binding.textView4,
            binding.textView5,
            binding.textView6
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNumberPicker()
        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initNumberPicker() {
        binding.numberPicker.apply {
            minValue = 1
            maxValue = 45
        }
    }
    private fun initRunButton(){


        binding.runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(number, textView)
            }
        }
    }
    private fun initAddButton(){
        binding.addButton.setOnClickListener {
            if(didRun){
                Toast.makeText(this, "초기화 후에 시도해주세요. ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.size >= 5){
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.contains(binding.numberPicker.value)){
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = binding.numberPicker.value.toString()

            setNumberBackground(binding.numberPicker.value, textView)
            pickNumberSet.add(binding.numberPicker.value)

        }
    }

    private fun initClearButton(){
        binding.clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }

    private fun setNumberBackground(number:Int, textView: TextView){
        when(number){
            in 1..10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yello)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)


        }
    }
    private fun getRandomNumber():List<Int>{
        val numberList = mutableListOf<Int>()
            .apply {
                for(i in 1..45){
                    if (pickNumberSet.contains(i)){
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()
        val newList = pickNumberSet.toList() + numberList.subList(0,6-pickNumberSet.size)
        return newList.sorted()
    }
}