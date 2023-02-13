package com.david.bajela.a6211project.UI

import android.content.Context
import androidx.lifecycle.ViewModel
import com.david.bajela.a6211project.Data.Repo

class LoginViewModel : ViewModel() {

    var email: String = ""
    var pass: String = ""

    fun login(c: Context): Boolean {
        if (Repo.userExist(email)) {
            if (email.isNotBlank() && pass.isNotBlank()) {
                if (Repo.userExist(email))
                    if (Repo.correctPass(email, pass)) {
                        Repo.login(c,email, pass)
                        return true
                    }
            }
        }
        return false
    }

    fun reason(): String {
        var s="Unkown"
        when {
            (email.isBlank()|| pass.isBlank()) -> s ="Please fill in alll fealsd"
            !Repo.userExist(email)||!Repo.correctPass(email, pass) -> s="user name or passwprd invalid"
        }
        return s
    }

}