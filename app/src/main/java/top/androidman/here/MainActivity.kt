package top.androidman.here

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import org.realstudio.here.Here

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Here.init(this)

        Here.config {
            multiProcess  = true
            encryptKey = "encryptKey"
        }

//        Here.put("text", 100)
//        Toast.makeText(this, Here.getInt("text").toString(), Toast.LENGTH_SHORT).show()
//
//        Here.bucket("user") {
//            put("text", 300)
//        }
//        Toast.makeText(this, Here.bucket("user").getInt("text").toString(), Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, Here.getInt("text").toString(), Toast.LENGTH_SHORT).show()

        val user1 = SimpleBean("derlio", 10)
        val user2 = SimpleBean("yanjie", 12)
        val map = mapOf(user1.name to user1, user2.name to user2)




        var start = System.currentTimeMillis()
        Here.put("map5", map)
        var end = System.currentTimeMillis()
        Toast.makeText(this, "put cost:${end - start}", Toast.LENGTH_SHORT).show()

        start = System.currentTimeMillis()
        val str = JSON.toJSONString(map)
        Here.put("map6", str)
        end = System.currentTimeMillis()
        Toast.makeText(this, "fastjson put cost:${end - start}", Toast.LENGTH_SHORT).show()

        start = System.nanoTime()
        val result: Map<String, SimpleBean?> = Here.getMap("map")
        end = System.nanoTime()
        Toast.makeText(this, "get cost:${end - start}", Toast.LENGTH_SHORT).show()

//        Toast.makeText(this, "${result.size}", Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, result["derlio"].toString(), Toast.LENGTH_SHORT).show()


    }
}
