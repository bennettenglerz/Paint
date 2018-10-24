package zbe.paint.model

enum class ButtonState {
    SIZE, LINE, RECT, OVAL, FILL, COLOR, CLEAR, DEFAULT
}
enum class Shape {
    LINE, RECT, OVAL
}

interface OnAppStateChangedListener {
    fun onAppStateChanged()
}
