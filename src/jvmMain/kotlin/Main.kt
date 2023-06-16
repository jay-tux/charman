
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cml.CMLException
import cml.Library
import cml.PosInfo
import data.Scripts
import ui.mainUI
import uiData.CharacterData

fun main(args: Array<String>) {
    CMLOut.tee()

    if(args.isNotEmpty()) {
        try {
            Scripts.loadFile(args[0])
            Library.invoke("main", listOf(), PosInfo("<runtime>", 0, 0))?.let {
                println("Main exited completely. Result: ${it.repr()}")
            } ?: println("Cannot run function `main' in `${args[0]}'")
        }
        catch(e: CMLException) {
            System.err.println("Exception while running function `main' (${e.javaClass.name}):")
            System.err.println(e.localizedMessage)
        }
        catch(e: Exception) {
            System.err.println("Exception while running function `main' (${e.javaClass.name}):")
            e.message?.let { System.err.println(it) }
            System.err.println("At: ${e.stackTrace.joinToString("\n\t")}")
        }
        return
    }

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
