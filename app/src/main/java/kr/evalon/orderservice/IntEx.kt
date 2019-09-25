package kr.evalon.orderservice



fun Int.toFormattedPrice() = String.format("%,d 원",this)