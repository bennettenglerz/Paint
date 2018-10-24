package zbe.paint.model

enum class AppState {
    SIZE, LINE, RECT, OVAL, FILL, COLOR, CLEAR, DEFAULT
}
enum class Shape {
    LINE, RECT, OVAL
}

data class DrawState(var state: AppState, var size: Int, var color: Int, var fill: Boolean)

interface OnAppStateChangedListener {
    fun onAppStateChanged()
}

