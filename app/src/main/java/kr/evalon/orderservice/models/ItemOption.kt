package kr.evalon.orderservice.models

data class ItemOption(
    val code: String = "",
    val name: String = "",
    val type: Type = Type.ESSENTIAL,
    val targetItems : List<String> = emptyList()
) {
    enum class Type {
        ESSENTIAL,
        OPTIONAL
    }
}