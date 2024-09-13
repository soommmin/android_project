package android.part1.android_pro_04_picture

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.part1.android_pro_04_picture.databinding.ActivityPhotoFrameBinding
import android.util.Log
import java.util.Timer
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()
    private var currentPosition = 0
    private var timer: Timer? = null


    private lateinit var binding: ActivityPhotoFrameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPhotoFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("PhotoFrame", "onCreate!!!")
        getPhotoUriFromIntent()

    }
    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }
        }
    }
    private fun startTimer() = with(binding){
        timer = timer(period = 5 * 1000) {
            runOnUiThread {
                Log.d("PhotoFrame", "5초가 지나감 !!")
                val current = currentPosition
                val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

                backgroundPhotoImageView.setImageURI(photoList[current])

                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            }
        }
    }
    override fun onStop() {
        super.onStop()

        Log.d("PhotoFrame", "onStop!!! timer cancel")
        timer?.cancel()
    }


    override fun onStart() {
        super.onStart()

        Log.d("PhotoFrame", "onStart!!! timer start")
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("PhotoFrame", "onDestroy!!! timer cancel")
        timer?.cancel()
    }


}