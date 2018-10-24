package zbe.paint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import zbe.paint.view.ImageButtonAdapter
import kotlinx.android.synthetic.main.canvas_view.*
import kotlinx.android.synthetic.main.list_view.*
import kotlinx.android.synthetic.main.size_dropdown.*
import zbe.paint.model.OnAppStateChangedListener
import zbe.paint.model.Shape


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set view
        setContentView(R.layout.main_activity)

        // Set adapter for list of buttons
        val adapter = ImageButtonAdapter(this)
        adapter.onAppStateChangedListener = object : OnAppStateChangedListener {
            override fun onAppStateChanged() {
                canvasView.drawState = adapter.drawState
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
                canvasView.drawState.size = size_dropdown.adapter.getItem(position).toString().toInt()
            }
        }
        size_dropdown.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                v.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArray(Shape.LINE.toString(), canvasView.shapes.filter { it.value == Shape.LINE }.keys.toTypedArray())
        outState?.putParcelableArray(Shape.RECT.toString(), canvasView.shapes.filter { it.value == Shape.RECT }.keys.toTypedArray())
        outState?.putParcelableArray(Shape.OVAL.toString(), canvasView.shapes.filter { it.value == Shape.OVAL }.keys.toTypedArray())

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.getParcelableArray(Shape.LINE.toString())?.forEach {
            canvasView.shapes.put(it, Shape.LINE)
        }
        savedInstanceState?.getParcelableArray(Shape.RECT.toString())?.forEach {
            canvasView.shapes.put(it, Shape.RECT)
        }
        savedInstanceState?.getParcelableArray(Shape.OVAL.toString())?.forEach {
            canvasView.shapes.put(it, Shape.OVAL)
        }
    }
}
