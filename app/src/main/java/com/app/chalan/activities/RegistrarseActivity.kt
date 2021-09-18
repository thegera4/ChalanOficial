package com.app.chalan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.app.chalan.R
import com.app.chalan.databinding.ActivityIntroBinding
import com.app.chalan.databinding.ActivityRegistrarseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegistrarseActivity : BaseActivity() {

    private lateinit var binding: ActivityRegistrarseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

    }

    //funcion para la action bar no mostrada en RegistrarseActivity que incluye boton back
    //de la esquina superior izquierda y click listener de boton para Registrarse
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarRegistrarseActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        binding.toolbarRegistrarseActivity.setNavigationOnClickListener { onBackPressed() }

        binding.btnRegistrarse.setOnClickListener { registrarUsuario() }
    }

    //funcion para registrar usuario con FireBase
    private fun registrarUsuario(){
        val name: String = binding.etNombreRegistrarse.text.toString().trim{ it <= ' '}
        val email: String = binding.etEmailRegistrarse.text.toString().trim{ it <= ' '}
        val password: String = binding.etPasswordRegistrarse.text.toString().trim{ it <= ' '}

        if (validarForma(name, email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                        task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            val registeredEmail = firebaseUser.email!!
                            val user = User(firebaseUser.uid, name, registeredEmail)
                            FirestoreClass().registerUser(this, user)
                        } else {
                            Toast.makeText(this, "Registration failed! Please try again",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    //funcion para validar y evitar que dejen en blanco campos de nombre, correo y password
    private fun validarForma(name: String, email: String, password: String) : Boolean{
        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Porfavor introduce tu nombre.")
                false
            }
            TextUtils.isEmpty(email) ->{
                showErrorSnackBar("Porfavor introduce un email válido de al menos 8 caracteres.")
                false
            }
            TextUtils.isEmpty(password) || binding.etEmailRegistrarse.length() < 8 ->{
                showErrorSnackBar("Porfavor introduce una contraseña válida. No se aceptan" +
                        "espacios en blanco y usar mínimo 8 caracteres.")
                false
            } else -> {
                true
            }
        }
    }

    //funcion para mostrar mensaje al terminar el registro exitoso en Firebase y
    //aseguramiento de esconder la progress bar
    fun usuarioRegistradoCorrectamente(){
        Toast.makeText(
            this, "Te has registrado satisfactoriamente!", Toast.LENGTH_SHORT).show()
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
    }

} //FIN