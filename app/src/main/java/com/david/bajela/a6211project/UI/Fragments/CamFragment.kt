package com.david.bajela.a6211project.UI.Fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.david.bajela.a6211project.Data.Repo
import com.david.bajela.a6211project.R
import com.david.bajela.a6211project.databinding.FragmentCamBinding
import java.io.File
import java.util.*

class CamFragment : Fragment(R.layout.fragment_cam) {

    private val TAG = "Camerax"
    private val FILE_NAME_FORMAT = "yy-MM-dd-HH-ss-SSS"
    private val REQUEST_CODE_PERMISSIONS = 123
    private val REQUESTED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private lateinit var binding: FragmentCamBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var outputDirection: File

    //    private lateinit var cameraEexcutor: ExecutorService
    private lateinit var l: FragListerner


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCamBinding.bind(view)
        outputDirection = od()
        //      cameraEexcutor= Executors.newSingleThreadExecutor()
        binding.photobtn.setOnClickListener { takePhoto() }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragListerner)
            l = context
        else throw RuntimeException("$context interface not implemented")

    }

    override fun onStart() {
        super.onStart()
        if (allPermissionGrannted()) {
            startCam()
            say("we have permission")
        } else {
            request_permision()
        }

    }

    private fun takePhoto() {
        val imageCapture = imageCapture
        val photoFile = File(
            outputDirection,
            SimpleDateFormat(
                FILE_NAME_FORMAT,
                Locale.getDefault()
            ).format(System.currentTimeMillis()) + "jpg"
        )
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "onError " + exception.message)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val saveUrI = Uri.fromFile(photoFile)
                    Repo.setImage(saveUrI.toString())
                    navback()
                }
            })
    }

    private fun navback() {
        this.view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_camFragment_to_noteFragment)
        }
    }

    private fun od(): File {

        val mediaDir = requireContext().externalMediaDirs.firstOrNull().let {
            File(it, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir
        else {
            requireContext().filesDir
        }
    }

    private fun allPermissionGrannted() =
        REQUESTED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(), it
            ) == PackageManager.PERMISSION_GRANTED
        }

    private fun request_permision() =
        ActivityCompat.requestPermissions(
            requireActivity(), REQUESTED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )

    private fun say(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGrannted()) {
                startCam()
            } else {
                say("permission not given")
            }
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startCam() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.d(TAG, "start Camera failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

}