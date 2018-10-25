package zbe.paint.model

import android.os.Parcelable
import java.io.Serializable

data class AppState(var shapes: HashMap<Parcelable, zbe.paint.model.Shape>,
                      var buttonPressed: Int, var size: Int, var color: Int,
                      var fill: Boolean): Serializable

interface OnAppStateChangedListener {
    fun onAppStateChanged()
}

interface OnSizeChangedListener {
    fun onSizeChanged(size: Int)
}