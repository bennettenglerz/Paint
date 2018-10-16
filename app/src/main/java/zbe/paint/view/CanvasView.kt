package zbe.paint.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import zbe.paint.AppState

class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Set defaults
    private var state: AppState = AppState.DEFAULT
    var color: Int = Color.BLACK
    var fill: Boolean = true

    fun setState(state: AppState) {
        this.state = state

        when (state) {
            AppState.PENCIL ->{

            }
            AppState.SIZE ->{

            }
            AppState.LINE ->{

            }
            AppState.RECT ->{

            }
            AppState.OVAL ->{

            }
            AppState.FILL ->{

            }
            AppState.COLOR ->{

            }
            AppState.CLEAR ->{

            }
            AppState.DEFAULT ->{
                // Do nothing
            }
        }
    }
}