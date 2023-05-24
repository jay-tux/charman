
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.Scripts
import ui.mainUI
import uiData.CharacterData

fun main() {
    Scripts.loadCache()
    application {
        Window(title = "D&D Character Manager", onCloseRequest = {
            CharacterData.saveAll()
            exitApplication()
        }) {
            mainUI()
        }
    }
}
