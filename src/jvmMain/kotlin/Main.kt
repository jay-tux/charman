
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.maybeInit
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import views.mainView
import java.sql.Connection

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        mainView()
    }
}

fun main() {
    val db = Database.connect("jdbc:sqlite:charman.sqlite", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    maybeInit(db)
    application {
        println("Database initialized")
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}
