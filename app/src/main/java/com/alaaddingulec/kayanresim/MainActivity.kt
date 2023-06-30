package com.alaaddingulec.kayanresim
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var horizontalScrollView: HorizontalScrollView
    private lateinit var containerImages: LinearLayout
    private var scrollDistance = 0
    private var currentPosition = 0
    private var isAutoScrollEnabled = true
    private var scrollHandler: Handler? = null
    private val scrollSpeed = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        horizontalScrollView = findViewById(R.id.horizontal_scroll_view)
        containerImages = findViewById(R.id.container_images)

        val imageMain = findViewById<ImageView>(R.id.image_main)
        val imageA = findViewById<ImageView>(R.id.image_a)
        val imageB = findViewById<ImageView>(R.id.image_b)
        val imageC = findViewById<ImageView>(R.id.image_c)
        val imageD = findViewById<ImageView>(R.id.image_d)
        val imageE = findViewById<ImageView>(R.id.image_e)

        containerImages.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                containerImages.viewTreeObserver.removeOnGlobalLayoutListener(this)
                scrollDistance = containerImages.width - horizontalScrollView.width
                startAutoScroll()
            }
        })

        containerImages.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isAutoScrollEnabled = false
                    scrollHandler?.removeCallbacks(scrollRunnable)
                }
                MotionEvent.ACTION_UP -> {
                    isAutoScrollEnabled = true
                    scrollHandler?.post(scrollRunnable)
                }
            }
            true
        }
    }

    private fun startAutoScroll() {
        scrollHandler = Handler()
        scrollHandler?.postDelayed(scrollRunnable, scrollSpeed.toLong())
    }

    private val scrollRunnable: Runnable = object : Runnable {
        override fun run() {
            if (isAutoScrollEnabled) {
                currentPosition += scrollSpeed
                if (currentPosition > scrollDistance) {
                    currentPosition = 0
                }
                horizontalScrollView.scrollTo(currentPosition, 0)
            }
            scrollHandler?.postDelayed(this, scrollSpeed.toLong())
        }
    }
}
