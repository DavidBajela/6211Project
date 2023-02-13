package com.david.bajela.a6211project.UI.Fragments

import android.content.DialogInterface

interface FragListerner {

    fun showbar()

    fun setTitle(title:String)

    fun toast(s:String)

    fun dialog(title:String,message:String,di: DialogInterface.OnClickListener)

    fun welcomeUser(useer:String)
}
