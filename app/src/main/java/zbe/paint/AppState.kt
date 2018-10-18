package zbe.paint

enum class AppState {
    PENCIL, SIZE, LINE, RECT, OVAL, FILL, COLOR, CLEAR, DEFAULT
}

interface OnAppStateChangedListener {
    fun onAppStateChanged()
}