
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.Scripts
import ui.mainUI

fun main() {
    Scripts.loadCache()
    application {
        Window(onCloseRequest = ::exitApplication) {
            mainUI()
        }
    }
}
