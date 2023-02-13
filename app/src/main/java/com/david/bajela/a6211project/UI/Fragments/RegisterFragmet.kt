package com.david.bajela.a6211project.UI.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import com.david.bajela.a6211project.Data.Repo
import com.david.bajela.a6211project.R
import com.david.bajela.a6211project.UI.RegisterViewModel
import com.david.bajela.a6211project.databinding.RegisterFragmentBinding

class RegisterFragmet : Fragment(R.layout.register_fragment) {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: RegisterFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RegisterFragmentBinding.bind(view)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.regEtEmail.text.append(viewModel.email)
        binding.regEtFname.text.append(viewModel.firstname)
        binding.regEtLname.text.append(viewModel.lastname)
        binding.regEtPass.text.append(viewModel.password)
        binding.btnReg.setOnClickListener { gotologin() }
        binding.regEtEmail.addTextChangedListener {
            viewModel.email = binding.regEtEmail.text.toString()
        }

        binding.regEtFname.addTextChangedListener {
            viewModel.firstname = binding.regEtFname.text.toString()
        }
        binding.regEtLname.addTextChangedListener {
            viewModel.lastname = binding.regEtLname.text.toString()
        }
        binding.regEtPass.addTextChangedListener {
            viewModel.password = binding.regEtPass.text.toString()
        }
    }

    fun gotologin() {
        if (viewModel.reg()) {
            Repo.updateUsers()
            this.view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_registerFragmet_to_loginFragment)
            }
        } else Toast.makeText(this.activity, viewModel.reason(), Toast.LENGTH_LONG).show()
    }

}