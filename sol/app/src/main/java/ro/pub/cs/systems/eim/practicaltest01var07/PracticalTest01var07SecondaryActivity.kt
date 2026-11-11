package ro.pub.cs.systems.eim.practicaltest01var07

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01var07SecondaryActivity : AppCompatActivity() {
    private lateinit var textView1 : TextView
    private lateinit var textView2 : TextView
    private lateinit var textView3 : TextView
    private lateinit var textView4 : TextView
    private lateinit var sumButton : Button
    private lateinit var productButton : Button
    private var a1 : Float = 0.0F
    private var a2 : Float = 0.0F
    private var a3 : Float = 0.0F
    private var a4 : Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01var07_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView4 = findViewById(R.id.textView4)
        sumButton = findViewById(R.id.sumButton)
        productButton = findViewById(R.id.productButton)

        if (intent != null) {
            if (intent.hasExtra("a1")) {
                a1 = intent.getFloatExtra("a1", 0.0F)
                textView1.text = a1.toString()
            }
            if (intent.hasExtra("a2")) {
                a2 = intent.getFloatExtra("a2", 0.0F)
                textView2.text = a2.toString()
            }
            if (intent.hasExtra("a3")) {
                a3 = intent.getFloatExtra("a3", 0.0F)
                textView3.text = a3.toString()
            }
            if (intent.hasExtra("a4")) {
                a4 = intent.getFloatExtra("a4", 0.0F)
                textView4.text = a4.toString()
            }
        }

        sumButton.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("sum", a1 + a2 + a3 + a4))
            finish()
        }

        productButton.setOnClickListener {
            setResult(RESULT_CANCELED, Intent().putExtra("prod", a1 * a2 * a3 * a4))
            finish()
        }
    }
}