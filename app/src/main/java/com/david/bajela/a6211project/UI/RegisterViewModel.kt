package com.david.bajela.a6211project.UI

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.david.bajela.a6211project.Data.Repo
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {

    var firstname: String = ""
    var lastname: String = ""
    var email: String = ""
    var password: String = ""

    fun reg(): Boolean {
//       if (!Repo.userExist(email) && email.matches(Patterns.EMAIL_ADDRESS.toRegex())) { email validation line

        if (!Repo.userExist(email)) {
            if (firstname.isNotBlank() && lastname.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                Repo.registerUser(firstname, lastname, email, password)
                return true
            }
        }
        return false
    }

    fun reason(): String {
        var s: String = "Unkown Reason"
        when {
            (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank()) -> s =
                "Please fill in alll fealsd"
            Repo.userExist(email) -> s = "there is alreadey a user with this email"
        }
        return s
    }

}