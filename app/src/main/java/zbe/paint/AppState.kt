package zbe.paint

enum class AppState {
    PENCIL, SIZE, LINE, RECT, OVAL, FILL, COLOR, CLEAR, DEFAULT
}

data class DrawState(var state: AppState, var size: Int, var color: Int, var fill: Boolean)

interface OnAppStateChangedListener {
    fun onAppStateChanged()
}