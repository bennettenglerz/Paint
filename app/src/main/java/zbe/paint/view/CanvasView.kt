package zbe.paint.view

import android.content.Context
import android.graphics.*
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import zbe.paint.model.ButtonState
import android.view.MotionEvent
import zbe.paint.model.AppState
import zbe.paint.model.Line


class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Set defaults
    val shapes = HashMap<Parcelable, zbe.paint.model.Shape>()
    var appState = AppState(shapes, ButtonState.DEFAULT.ordinal, 1, Color.BLACK, true)

    private val paint = Paint()

    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f

    init {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = appState.color
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = appState.size.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (shape in shapes) {
            when (shape.value) {
                zbe.paint.model.Shape.LINE -> {
                    val line = shape.key as Line
                    canvas!!.drawLine(line.startX, line.startY, line.endX, line.endY, paint)
                }
                zbe.paint.model.Shape.RECT -> {
                    val rect = shape.key as RectF
                    canvas!!.drawRect(rect, paint)
                }
                zbe.paint.model.Shape.OVAL -> {
                    val oval = shape.key as RectF
                    canvas!!.drawOval(oval, paint)
                }
            }
        }
    }

    private fun touchDown(x: Float, y: Float) {
        startX = x
        startY = y
    }

    private fun touchMove(x: Float, y: Float) {
        endX = x
        endY = y
    }

    private fun touchUp() {
        when (appState.buttonPressed) {
            ButtonState.LINE.ordinal -> {
                shapes.put(Line(startX, startY, endX, endY), zbe.paint.model.Shape.LINE)
            }
            ButtonState.RECT.ordinal -> {
                shapes.put(RectF(startX, startY, endX, endY), zbe.paint.model.Shape.RECT)
            }
            ButtonState.OVAL.ordinal -> {
                shapes.put(RectF(startX, startY, endX, endY), zbe.paint.model.Shape.OVAL)
            }
            else -> {
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(event.x, event.y)
            MotionEvent.ACTION_MOVE -> touchMove(event.x, event.y)
            MotionEvent.ACTION_UP -> touchUp()
        }

        invalidate()

        return true
    }

    fun clear(canvas: Canvas?) {
        shapes.clear()
        canvas?.drawColor(Color.WHITE)
    }
}
