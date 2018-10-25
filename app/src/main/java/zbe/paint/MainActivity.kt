package zbe.paint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import zbe.paint.view.ImageButtonAdapter
import kotlinx.android.synthetic.main.canvas_view.*
import kotlinx.android.synthetic.main.list_view.*
import kotlinx.android.synthetic.main.size_dropdown.*
import zbe.paint.model.AppState
import zbe.paint.model.OnAppStateChangedListener


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

        // Dropdown adapter
        val sizeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                arrayOf("1", "2", "3"))
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        size_dropdown.adapter = sizeAdapter
        size_dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                size_dropdown.visibility = View.GONE
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                size_dropdown.visibility = View.GONE
                canvasView.appState.size = size_dropdown.adapter.getItem(position).toString().toInt()
            }
        }
        size_dropdown.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                v.visibility = View.GONE
        }
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
}
