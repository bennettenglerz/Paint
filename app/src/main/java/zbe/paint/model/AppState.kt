package zbe.paint.model

import android.os.Parcelable
import java.io.Serializable

data class AppState(var shapes: Map<Parcelable, zbe.paint.model.Shape>,
                      var buttonPressed: Int, var size: Int, var color: Int,
                      var fill: Boolean): Serializable