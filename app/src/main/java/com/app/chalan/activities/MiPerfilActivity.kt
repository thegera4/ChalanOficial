package com.app.chalan.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.chalan.R
import com.app.chalan.activities.firebase.FirestoreClass
import com.app.chalan.activities.models.User
import com.app.chalan.activities.utils.Constants
import com.app.chalan.databinding.ActivityMiPerfilBinding
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class MiPerfilActivity : BaseActivity() {

    private var mSelectedImageFileUri: Uri? = null
    private var mProfileImageURL: String =""
    private lateinit var mUserDetails: User

    private lateinit var binding: ActivityMiPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        FirestoreClass().loadUserData(this)

        binding.civUserImageMiPerfil.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE)
            }
        }

        binding.btnActualizarMiPerfil.setOnClickListener {
            if (mSelectedImageFileUri != null){
                uploadUserImage()
            }else{
                showProgressDialog(resources.getString(R.string.please_wait))
                updateUserProfileData()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            }
        } else {
            Toast.makeText(this, "Has denegado el permiso de acceso al almacenamiento." +
                    "Porfavor ve a la configuración de la aplicación para aceptar los permisos.",
                Toast.LENGTH_LONG).show()
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null){
            mSelectedImageFileUri = data.data
            try {
                var userImage = findViewById<ImageView>(R.id.civUserImageMiPerfil)
                Glide
                    .with(this@MiPerfilActivity)
                    .load(mSelectedImageFileUri)
                    .fitCenter()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(userImage)
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    //funcion para poner datos de usuario en campos
    fun setUserDataInUI(user: User){
        mUserDetails = user
        var userImage = findViewById<ImageView>(R.id.civUserImageMiPerfil)
        Glide
            .with(this@MiPerfilActivity)
            .load(user.image)
            .fitCenter()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(userImage)

        binding.etNombreMiPerfil.setText(user.name)
        binding.etEmailMiPerfil.setText(user.email)
        if (user.mobile != 0L){
            binding.etCelularMiPerfil.setText(user.mobile.toString())
        }
    }

    //funcion para action bar
    private fun setupActionBar(){
        val toolbarMyProfileActivity =
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarMiPerfil)
        setSupportActionBar(toolbarMyProfileActivity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile_title)
        }
        binding.toolbarMiPerfil.setNavigationOnClickListener { onBackPressed() }
    }

    //funcion para actualizar imagen de perfil de usuario
    private fun updateUserProfileData(){
        val userHashMap = HashMap<String, Any>()
        if (mProfileImageURL.isNotEmpty() && mProfileImageURL != mUserDetails.image){
            userHashMap[Constants.IMAGE] = mProfileImageURL
        }
        if (binding.etNombreMiPerfil.text.toString() != mUserDetails.name){
            userHashMap[Constants.NAME] = binding.etNombreMiPerfil.text.toString()
        }
        if (binding.etCelularMiPerfil.text.toString() != mUserDetails.mobile.toString()){
            userHashMap[Constants.MOBILE] = binding.etCelularMiPerfil.text.toString().toLong()
        }
        FirestoreClass().updateUserProfileData(this, userHashMap)
    }

    //funcion para cargar imagenes de usuario
    private fun uploadUserImage(){
        showProgressDialog(resources.getString(R.string.please_wait))
        if (mSelectedImageFileUri != null){
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "USER IMAGE " + System.currentTimeMillis()
                        + "." + Constants.getFileExtension(this, mSelectedImageFileUri))
            sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener {
                    taskSnapshot ->
                Log.i("Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString())
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        uri ->
                    Log.i("Downloadable Image URL", uri.toString())
                    mProfileImageURL = uri.toString()
                    updateUserProfileData()
                }
            }.addOnFailureListener{
                    exception ->
                Toast.makeText(this@MiPerfilActivity, exception.message,
                    Toast.LENGTH_LONG).show()
                hideProgressDialog()
            }
        }
    }

    //funcion de cosas por hacer cuando se actualiza correctamente el perfil del usuario
    fun profileUpdateSuccess(){
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }


} //FIN