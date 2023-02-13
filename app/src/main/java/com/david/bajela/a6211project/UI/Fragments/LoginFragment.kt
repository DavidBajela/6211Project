package com.david.bajela.a6211project.UI.Fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.david.bajela.a6211project.R
import com.david.bajela.a6211project.UI.LoginViewModel
import com.david.bajela.a6211project.databinding.LoginFragmentBinding

class LoginFragment : Fragment(R.layout.login_fragment) {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var l: FragListerner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragListerner)
            l = context
        else throw RuntimeException("$context interface not implemented")
        l.setTitle("Login")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.btlLogin.setOnClickListener { login() }
        binding.btnReg.setOnClickListener { reg() }
        binding.etUname.text.append(viewModel.email)
        binding.logEtPass.text.append(viewModel.pass)


        binding.etUname.addTextChangedListener {
            viewModel.email = binding.etUname.text.toString()

            binding.logEtPass.addTextChangedListener {
                viewModel.pass = binding.logEtPass.text.toString()
            }
        }

    }

    private fun login() {
        if (viewModel.login(requireContext())) {
            this.view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_loginFragment_to_mesageFragment)
            }
        } else {
            Toast.makeText(this.activity, viewModel.reason(), Toast.LENGTH_LONG).show()

        }
    }

    private fun reg() {
        this.view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_loginFragment_to_registerFragmet)

        }
    }

    override fun onStart() {
        super.onStart()
        l.showbar()
    }

}