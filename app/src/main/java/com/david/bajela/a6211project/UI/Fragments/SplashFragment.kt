package com.david.bajela.a6211project.UI.Fragments

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.AsyncTask
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.david.bajela.a6211project.Data.Repo
import com.david.bajela.a6211project.MainActivity
import com.david.bajela.a6211project.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import kotlin.concurrent.thread

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var INTRO: MediaPlayer

    override fun onStart() {
        super.onStart()
        INTRO= MediaPlayer.create(this.context, R.raw.zero2seven)
        playIntro()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CoroutineScope(IO).launch {
            Repo.setUP(context)
            delay(1000)
            done()
        }

    }

    private fun done() {
        INTRO.stop()
        INTRO.release()
//        Navigation.findNavController(this.requireView()).navigate(R.id.action_splashFragment_to_loginFragment)
        this.view?.let {    
            Navigation.findNavController(it)
                .navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

    private fun playIntro() {
        thread {
            INTRO.start()
        }
    }
}