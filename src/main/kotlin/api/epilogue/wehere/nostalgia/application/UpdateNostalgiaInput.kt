package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.nostalgia.domain.Nostalgia

data class UpdateNostalgiaInput(
    val title: String?,
    val description: String?,
    val visibility: Nostalgia.NostalgiaVisibility?,
    val images: List<String>?
)
