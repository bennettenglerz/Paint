package zbe.paint

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import zbe.paint.view.ImageButtonAdapter
import kotlinx.android.synthetic.main.canvas_view.*
import kotlinx.android.synthetic.main.list_view.*
import zbe.paint.model.AppState
import zbe.paint.model.OnAppStateChangedListener
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set view
        setContentView(R.layout.main_activity)

        // Set adapter for list of buttons
        val adapter = ImageButtonAdapter(this)
        adapter.onAppStateChangedListener = object : OnAppStateChangedListener {
            override fun onAppStateChanged() {
                canvasView.appState = adapter.appState
                canvasView.invalidate()
            }
        }
        listView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable("state", canvasView.appState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val appState = savedInstanceState?.getSerializable("state") as AppState
        (listView.adapter as ImageButtonAdapter).appState = appState
        canvasView.appState = appState
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        newConfig ?: return

        val orientation = newConfig.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            Toast.makeText(this, "Portrait Mode", Toast.LENGTH_SHORT).show()
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            Toast.makeText(this, "Landscape Mode", Toast.LENGTH_SHORT).show()
    }
}
