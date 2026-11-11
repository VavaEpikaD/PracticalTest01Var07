package ro.pub.cs.systems.eim.practicaltest01var07

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var07MainActivity : AppCompatActivity() {
    private lateinit var editText1 : EditText
    private lateinit var editText2 : EditText
    private lateinit var editText3 : EditText
    private lateinit var editText4 : EditText
    private lateinit var setButton : Button
    private lateinit var randomButton : Button

    private var a1 : Float = 0.0F
    private var a2 : Float = 0.0F
    private var a3 : Float = 0.0F
    private var a4 : Float = 0.0F

    private var sum : Float = 0.0F
    private var prod : Float = 0.0F

    fun getRandom() : Float {
        var a : Int = (1..100).random()
        var b : Int = (1..100).random()
        return a.toFloat() / b.toFloat()
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            sum = result.data?.getFloatExtra("sum", 0.0F) ?: 0.0F
            Toast.makeText(this, "sum: $sum", Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            prod = result.data?.getFloatExtra("prod", 0.0F) ?: 0.0F
            Toast.makeText(this, "prod: $prod", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        editText3 = findViewById(R.id.editText3)
        editText4 = findViewById(R.id.editText4)
        setButton = findViewById(R.id.setButton)
        randomButton = findViewById(R.id.randomButton)

        randomButton.setOnClickListener {
            if (editText1.text.toString() == "" || editText1.text.toString().toFloatOrNull() == null) {
                a1 = getRandom()
                editText1.setText(a1.toString())
            }
            if (editText2.text.toString() == "" || editText2.text.toString().toFloatOrNull() == null) {
                a2 = getRandom()
                editText2.setText(a2.toString())
            }
            if (editText3.text.toString() == "" || editText3.text.toString().toFloatOrNull() == null) {
                a3 = getRandom()
                editText3.setText(a3.toString())
            }
            if (editText4.text.toString() == "" || editText4.text.toString().toFloatOrNull() == null) {
                a4 = getRandom()
                editText4.setText(a4.toString())
            }
        }

        setButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01var07SecondaryActivity::class.java)
            intent.putExtra("a1", a1)
            intent.putExtra("a2", a2)
            intent.putExtra("a3", a3)
            intent.putExtra("a4", a4)
            activityResultLauncher.launch(intent)
        }

        val intent = Intent(this, PracticalTest01Var07Service::class.java)
        startService(intent)
    }


    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var s = intent?.getStringExtra("message") ?: "Empty"
            Log.d("string", s)
            var arr = s.split(" ")
            a1 = arr[0].toFloat()
            a2 = arr[1].toFloat()
            a3 = arr[2].toFloat()
            a4 = arr[3].toFloat()
            Log.d("nums", a1.toString())
            editText1.setText(a1.toString())
            editText2.setText(a2.toString())
            editText3.setText(a3.toString())
            editText4.setText(a4.toString())
        }
    }

    private val intentFilter = IntentFilter().apply {
        addAction("action1")
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putFloat("sum", sum)
        outState.putFloat("prod", prod)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState.containsKey("sum"))
            sum = savedInstanceState.getFloat("sum")
        if (savedInstanceState.containsKey("prod"))
            prod = savedInstanceState.getFloat("prod")
    }
}
