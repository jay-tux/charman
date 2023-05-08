package dialogs

import androidx.compose.runtime.Composable
import data.ClassTrait

@Composable
fun featureDialogContent(
    feature: ClassTrait?,
    onExit: () -> Unit,
    onAdd: (ClassTrait) -> Unit,
    onMod: (ClassTrait) -> Unit
) {

}