package zbe.paint.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import zbe.paint.AppState
import zbe.paint.DrawState
import android.view.MotionEvent
import android.graphics.Bitmap


class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Set defaults
    var drawState = DrawState(AppState.DEFAULT, 1, Color.BLACK, true)

    private val mPaint = Paint()

    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var mPath = Path()
    private var mBitmapPaint: Paint? = Paint()
    private var circlePaint = Paint()
    private var circlePath = Path()

    init {
        mPaint.setAntiAlias(true)
        mPaint.setDither(true)
        mPaint.setColor(Color.GREEN)
        mPaint.setStyle(Paint.Style.STROKE)
        mPaint.setStrokeJoin(Paint.Join.ROUND)
        mPaint.setStrokeCap(Paint.Cap.ROUND)
        mPaint.setStrokeWidth(12f)

        mPath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        circlePaint.setAntiAlias(true)
        circlePaint.setColor(Color.BLUE)
        circlePaint.setStyle(Paint.Style.STROKE)
        circlePaint.setStrokeJoin(Paint.Join.MITER)
        circlePaint.setStrokeWidth(4f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas.drawPath(mPath, mPaint)
        canvas.drawPath(circlePath, circlePaint)
    }

    private var mX: Float = 0.toFloat()
    private var mY:Float = 0.toFloat()
    private val TOUCH_TOLERANCE = 4f

    private fun touch_start(x: Float, y: Float) {
        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touch_move(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y

            circlePath.reset()
        }
    }

    private fun touch_up() {
        mPath.lineTo(mX, mY)
        circlePath.reset()
        // commit the path to our offscreen
        mCanvas!!.drawPath(mPath, mPaint)
        // kill this so we don't double draw
        mPath.reset()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touch_start(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touch_move(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touch_up()
                invalidate()
            }
        }
        return true
    }

}