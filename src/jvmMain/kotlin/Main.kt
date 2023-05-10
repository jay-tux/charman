
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cml.Scripts

@Composable
@Preview
fun App() {
    MaterialTheme {
        Text("Re-doing everything :(")
    }
}

fun main() {
    val loader = Scripts()
    loader.loadFile("/home/jay/hdd/kotlin/charman/cml/small.cml")
    application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}
