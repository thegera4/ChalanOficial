package com.app.chalan.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.app.chalan.R
import com.app.chalan.activities.models.User
import com.app.chalan.databinding.ActivityEntrarBinding
import com.google.firebase.auth.FirebaseAuth

class EntrarActivity : BaseActivity() {

    private lateinit var binding: ActivityEntrarBinding

    private lateinit var auth: FirebaseAuth

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.btnEntrar.setOnClickListener {
            entrarUsuarioRegistrado()
        }

        setupActionBar()
    }

    //funcion para action bar personalizado en EntrarActivity y agregar click listener
    //al boton de Entrar en este mismo Activity asi como boton de esquina sup derecha BACK
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarEntrarActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        binding.toolbarEntrarActivity.setNavigationOnClickListener { onBackPressed() }
    }

    //funcion para logear a usuario ya registrado con Firebase
    private fun entrarUsuarioRegistrado(){
        val email: String = binding.etEmailEntrar.text.toString().trim{ it <= ' '}
        val password: String = binding.etPasswordEntrar.text.toString().trim{ it <= ' '}
        if (validarForma(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    task ->
                    hideProgressDialog()
                    if (task.isSuccessful){
                        Log.d("Sign in", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, BuscarOfrecerActivity::class.java))
                    } else {
                        Log.w("Sign in", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Fallo en la autentificacion." +
                                "Porfavor revisa tus datos.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    //funcion para validar datos y asegurar que no deje campos vacios el usuario
    private fun validarForma(email: String, password: String) : Boolean{
        return when{
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Porfavor introduce un email válido")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Porfavor introduce una contraseña válida")
                false
            } else -> {
                true
            }
        }
    }

    //funcion para hacer cosas cuando los datos de usuario son validados y correctos
    //para cuando quiere entrar (no registrarse)
    fun signInSuccess(user: User){
        hideProgressDialog()
        startActivity(Intent(this, BuscarOfrecerActivity::class.java))
        finish()
    }

} //FIN