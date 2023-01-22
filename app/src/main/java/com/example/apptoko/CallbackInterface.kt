package com.example.apptoko

import com.example.apptoko.response.cart.Cart

interface CallbackInterface {
    fun passResultCallback(total:String, cart: ArrayList<Cart>)
}