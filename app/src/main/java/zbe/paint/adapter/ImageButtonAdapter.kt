package zbe.paint.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import zbe.paint.R

class ImageButtonAdapter(context: Context) : BaseAdapter() {

    val buttons = arrayListOf<ImageButton>()

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Get buttons
        val pencilButton = inflater.inflate(R.layout.pencil_button, null) as ImageButton
        val sizeButton = inflater.inflate(R.layout.size_button, null) as ImageButton
        val lineButton = inflater.inflate(R.layout.line_button, null) as ImageButton
        val rectButton = inflater.inflate(R.layout.rect_button, null) as ImageButton
        val ovalButton = inflater.inflate(R.layout.oval_button, null) as ImageButton
        val fillButton = inflater.inflate(R.layout.fill_button, null) as ImageButton
        val colorButton = inflater.inflate(R.layout.color_button, null) as ImageButton
        val clearButton = inflater.inflate(R.layout.clear_button, null) as ImageButton

        // Add buttons
        buttons.add(pencilButton)
        buttons.add(sizeButton)
        buttons.add(lineButton)
        buttons.add(rectButton)
        buttons.add(ovalButton)
        buttons.add(fillButton)
        buttons.add(colorButton)
        buttons.add(clearButton)
    }

    override fun getItem(position: Int): Any = buttons[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = buttons.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = getItem(position) as ImageButton
}