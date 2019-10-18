package top.androidman.here

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.realstudio.here.Here

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Here.init(this)

        Here.put("text", 100)

        Toast.makeText(this,Here.getInt("text").toString(),Toast.LENGTH_LONG).show()
    }
}
