package zbe.paint.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import com.warkiz.tickseekbar.*
import zbe.paint.R
import zbe.paint.model.OnSizeChangedListener


class SizeDialog : DialogFragment() {

    private var mContext: Context? = null
    private var sizeView: TickSeekBar? = null
    var onSizeChangedListener: OnSizeChangedListener? = null
    var size = 0

    fun build(context: Context, initialSize: Int): SizeDialog {
        mContext = context

        sizeView = TickSeekBar
                .with(mContext!!)
                .max(5f)
                .min(0f)
                .progressValueFloat(false)
                .progress(initialSize.toFloat())
                .tickCount(6)
                .showTickMarksType(TickMarkType.DIVIDER)
                .tickMarksColor(Color.BLACK)
                .tickMarksSize(6)//dp
                .tickTextsSize(13)//sp
                .tickTextsColor(Color.BLACK)
                .showTickTextsPosition(TextPosition.ABOVE)
                .thumbColor(Color.BLACK)
                .thumbSize(14)
                .trackProgressColor(Color.BLACK)
                .trackProgressSize(4)
                .trackBackgroundColor(Color.BLACK)
                .trackBackgroundSize(2)
                .build()

        sizeView!!.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {
            }

            override fun onStartTrackingTouch(seekBar: TickSeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: TickSeekBar?) {
                seekBar ?: return
                size = seekBar.progress
                onSizeChangedListener?.onSizeChanged(size)
            }
        }

        sizeView!!.setBackgroundColor(Color.TRANSPARENT)

        return this
    }

    override fun onStart() {
        super.onStart()

        val window = dialog.window
        val windowParams = window!!.attributes
        windowParams.dimAmount = 0f
        windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.attributes = windowParams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(mContext!!).setView(sizeView).create().apply {
            setOnShowListener {
                val width: Int = resources.getDimensionPixelSize(R.dimen.chroma_dialog_width)
                val height: Int = WindowManager.LayoutParams.WRAP_CONTENT
                window.setLayout(width, height)
                window.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
    }
}