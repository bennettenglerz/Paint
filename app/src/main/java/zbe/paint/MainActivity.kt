package zbe.paint

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.canvas_view.*
import kotlinx.android.synthetic.main.list_view.*
import zbe.paint.adapter.ImageButtonAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set view
        setContentView(R.layout.main_activity)

        // Set adapter for list of buttons
        listView.adapter = ImageButtonAdapter(this)
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            canvasView.setState(AppState.values()[position])

            view?.setBackgroundColor(Color.BLUE)
        }
    }
}
