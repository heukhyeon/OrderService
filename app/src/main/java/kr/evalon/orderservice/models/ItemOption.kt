package kr.evalon.orderservice.models

data class ItemOption(
    val code: String = "",
    val name: String = "",
    val type: Type,
    val targetItems : List<String>
) {
    enum class Type {
        ESSENTIAL,
        OPTIONAL
    }
}