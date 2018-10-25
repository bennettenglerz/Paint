package zbe.paint.model

import android.os.Parcel
import android.os.Parcelable


class Line() : Parcelable {
    var startX = 0f
    var startY = 0f
    var endX = 0f
    var endY = 0f

    constructor(x1: Float, y1: Float, x2: Float, y2: Float) : this() {
        startX = x1
        startY = y1
        endX = x2
        endY = y2
    }

    constructor(parcel: Parcel) : this() {
        startX = parcel.readFloat()
        startY = parcel.readFloat()
        endX = parcel.readFloat()
        endY = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(startX)
        parcel.writeFloat(startY)
        parcel.writeFloat(endX)
        parcel.writeFloat(endY)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Line> {
        override fun createFromParcel(parcel: Parcel): Line {
            return Line(parcel)
        }

        override fun newArray(size: Int): Array<Line?> {
            return arrayOfNulls(size)
        }
    }
}