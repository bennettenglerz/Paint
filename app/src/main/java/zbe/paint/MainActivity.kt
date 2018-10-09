package zbe.paint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.list_view.*
import zbe.paint.adapter.ImageButtonAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set view
        setContentView(R.layout.main_activity)

        // Set adapter for list of buttons
        listView.adapter = ImageButtonAdapter(this)
    }
}
