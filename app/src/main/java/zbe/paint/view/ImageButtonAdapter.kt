package zbe.paint.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.Spinner
import zbe.paint.model.OnAppStateChangedListener
import zbe.paint.R
import zbe.paint.model.AppState

class ImageButtonAdapter(private val context: Context) : BaseAdapter() {

    private val buttons = arrayListOf<ImageButton>()
    var appState = AppState(HashMap(), -1, 1, Color.BLACK, false)
        set(value) {
            field = value
            if (value.buttonPressed != -1) {
                buttons[value.buttonPressed].setBackgroundColor(
                        ResourcesCompat.getColor(context.resources, R.color.lt_gray, null))
            }
        }
    var onAppStateChangedListener: OnAppStateChangedListener? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Get buttons
        val sizeButton = inflater.inflate(R.layout.size_button, null) as ImageButton
        val lineButton = inflater.inflate(R.layout.line_button, null) as ImageButton
        val rectButton = inflater.inflate(R.layout.rect_button, null) as ImageButton
        val ovalButton = inflater.inflate(R.layout.oval_button, null) as ImageButton
        val fillButton = inflater.inflate(R.layout.fill_button, null) as ImageButton
        val colorButton = inflater.inflate(R.layout.color_button, null) as ImageButton
        val clearButton = inflater.inflate(R.layout.clear_button, null) as ImageButton

        // Add buttons
        buttons.add(sizeButton)
        buttons.add(lineButton)
        buttons.add(rectButton)
        buttons.add(ovalButton)
        buttons.add(fillButton)
        buttons.add(colorButton)
        buttons.add(clearButton)

        // Add click listeners
        sizeButton.setOnClickListener {
            // Open size dropdown
            val dropdown = (context as Activity).findViewById<Spinner>(R.id.size_dropdown)
            dropdown.visibility = View.VISIBLE
            dropdown.performClick()

            updateButton(buttons.indexOf(sizeButton))
        }

        lineButton.setOnClickListener {
            updateButton(buttons.indexOf(lineButton))
        }

        rectButton.setOnClickListener {
            updateButton(buttons.indexOf(rectButton))
        }

        ovalButton.setOnClickListener {
            updateButton(buttons.indexOf(ovalButton))
        }

        fillButton.setOnClickListener {
            appState.fill = !appState.fill

            updateButton(buttons.indexOf(fillButton))
        }

        colorButton.setOnClickListener {
            updateButton(buttons.indexOf(colorButton))
        }

        clearButton.setOnClickListener {
            updateButton(buttons.indexOf(clearButton))
        }
    }

    override fun getItem(position: Int): Any = buttons[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = buttons.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            getItem(position) as ImageButton

    private fun updateButton(buttonPosition: Int) {
        appState.buttonPressed = if (appState.buttonPressed == buttonPosition) {
            // If buttonPosition was already selected
            buttons[buttonPosition].setBackgroundColor(
                    ResourcesCompat.getColor(context.resources, R.color.gray, null))
            -1
        } else { // If buttonPosition was not already selected
            if (appState.buttonPressed != -1)
                buttons[appState.buttonPressed].setBackgroundColor(
                        ResourcesCompat.getColor(context.resources, R.color.gray, null))

            buttons[buttonPosition].setBackgroundColor(
                    ResourcesCompat.getColor(context.resources, R.color.lt_gray, null))
            buttonPosition
        }

        onAppStateChangedListener?.onAppStateChanged()
    }
}