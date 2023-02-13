package com.david.bajela.a6211project

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.david.bajela.a6211project.Data.Repo
import com.david.bajela.a6211project.UI.Fragments.FragListerner
import com.david.bajela.a6211project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragListerner {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.mAppbar.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        Repo.updateDatabase()
        super.onDestroy()
    }

    override fun showbar() {
        binding.mAppbar.visibility = View.VISIBLE

    }

    override fun setTitle(Title: String) {
        binding.toolbar.title=Title
    }

    override fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    override fun dialog(title: String, message: String, di: DialogInterface.OnClickListener) {
        val a: AlertDialog.Builder = AlertDialog.Builder(this)
        a.setTitle(title)
        a.setMessage(message)
        a.setPositiveButton("yes", di)
        a.setNegativeButton("no", di)
        a.create().show()
    }

    override fun welcomeUser(useer: String) {
        binding.toolbar.title="Welcome"
        binding.toolbar.subtitle=useer
    }
}