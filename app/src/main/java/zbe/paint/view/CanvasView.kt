package zbe.paint.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import zbe.paint.model.AppState
import zbe.paint.model.DrawState
import android.view.MotionEvent


class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Set defaults
    var drawState = DrawState(AppState.DEFAULT, 1, Color.BLACK, true)

    private val paint = Paint()
    private var points = arrayListOf<Pair<Float, Float>>()
    private var path = Path()
    private var rect = RectF(0f, 0f, 0f, 0f)

    init {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = drawState.color
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = drawState.size.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        when (drawState.state) {
            AppState.PENCIL -> {
                canvas!!.drawPath(path, paint)
            }
            AppState.RECT -> {
                canvas!!.drawRect(rect, paint)
            }
            AppState.LINE -> {
                if (points.size > 1)
                    canvas!!.drawLine(points[0].first, points[0].second,
                            points[points.size - 1].first, points[points.size - 1].second, paint)
            }
            AppState.OVAL -> {
                canvas!!.drawOval(rect, paint)
            }
            AppState.CLEAR -> clear(canvas)
            else -> {
            }
        }
    }

    private fun touchDown(x: Float, y: Float) {
        points = arrayListOf(Pair(x, y))
    }

    private fun touchMove(x: Float, y: Float) {
        points.add(Pair(x, y))
    }

    private fun touchUp(x: Float, y: Float) {
        when (drawState.state) {
            AppState.PENCIL -> {
                // change points to path
                val iter = points.iterator()
                var previousPoint = iter.next()
                path.moveTo(previousPoint.first, previousPoint.second)

                while (iter.hasNext()) {
                    val point = iter.next()
                    path.quadTo((previousPoint.first + point.first) / 2, (previousPoint.second + point.second) / 2, point.first, point.second)
                    previousPoint = point
                }
            }
            AppState.LINE -> {
                if (points.size > 1)
                    rect = RectF(points[0].first, points[0].second, x, y)
            }
            AppState.RECT -> {
                if (points.size > 1)
                    rect = RectF(points[0].first, points[0].second, x, y)
            }
            AppState.OVAL -> {
                if (points.size > 1)
                    rect = RectF(points[0].first, points[0].second, x, y)
            }
            else -> {
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(event.x, event.y)
            MotionEvent.ACTION_MOVE -> touchMove(event.x, event.y)
            MotionEvent.ACTION_UP -> touchUp(event.x, event.y)
        }

        invalidate()

        return true
    }

    fun clear(canvas: Canvas?) {
        points = arrayListOf()
        path = Path()
        rect = RectF(0f, 0f, 0f, 0f)
        drawState = DrawState(AppState.DEFAULT, 1, Color.BLACK, true)

        canvas?.drawColor(Color.WHITE)
    }
}
