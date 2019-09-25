package kr.evalon.orderservice.models

abstract class BaseItem(
    val code:String,
    val name:String,
    val price:Int,
    val categoryCodes: List<String>
)

