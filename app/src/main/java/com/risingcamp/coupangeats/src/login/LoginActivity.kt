package com.risingcamp.coupangeats.src.login

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityLoginBinding
import com.risingcamp.coupangeats.src.MainActivity
import com.risingcamp.coupangeats.src.login.models.LoginResponse
import com.risingcamp.coupangeats.src.login.models.PostLoginRequest
import com.risingcamp.coupangeats.src.signup.SignupActivity
import com.risingcamp.coupangeats.src.signup.SignupService
import com.risingcamp.coupangeats.src.signup.models.postSignup.PostSignupRequest

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginInterface {

    val Login = 1
    var loginThread : LoginThread? = null

    var check : String? = null
    var jwt : String? = null
    var userId : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setEmailClearBtn()
        setPwdFocus()
        login()
        goSignup()
        goHome()
    }

    fun setEmailClearBtn(){
        binding.loginEmailEdt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                if(binding.loginEmailEdt.length()!=0){
                    binding.loginEmailClearBnt.visibility = View.VISIBLE
                } else{
                    binding.loginEmailClearBnt.visibility = View.GONE
                }
            }
        })

        binding.loginEmailClearBnt.setOnClickListener {
            binding.loginEmailEdt.text = null
        }
    }

    fun setPwdFocus(){
        binding.loginPwdEdt.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if(p1){
                    //????????????
                    binding.loginPwdLayout.setBackgroundResource(R.drawable.login_edt_selected)
                } else{
                    //????????? ????????????
                    binding.loginPwdLayout.setBackgroundResource(R.drawable.login_edt_unselected)
                }
            }
        })
    }

    fun login(){
        binding.loginBtn.setOnClickListener {

            if(binding.loginEmailEdt.length()==0 && binding.loginPwdEdt.length()==0){
                binding.loginFalseHint.text = "???????????? ??????????????????"
                loginThread = LoginThread()
                LoginThread().start()

            } else{ //?????? ???????????? ?????? ???????????? ???
                if(binding.loginEmailEdt.text!!.contains("@") == false){
                    binding.loginFalseHint.text = "???????????? ??????????????? ???????????? ??????????????????"
                    loginThread = LoginThread()
                    LoginThread().start()
                } else{
                    if(binding.loginPwdEdt.text!!.isEmpty()){
                        binding.loginFalseHint.text = "??????????????? ??????????????????"
                        loginThread = LoginThread()
                        LoginThread().start()
                    }
                }

                if(binding.loginPwdEdt.text!!.isNotEmpty() && binding.loginEmailEdt.text!!.isEmpty()){
                    binding.loginFalseHint.text = "???????????? ??????????????????"
                    loginThread = LoginThread()
                    LoginThread().start()
                }

                if(binding.loginEmailEdt.text!!.contains("@") && binding.loginEmailEdt.text!!.isNotEmpty() && binding.loginPwdEdt.text!!.isNotEmpty()){

                    val email = binding.loginEmailEdt.text.toString()
                    val password = binding.loginPwdEdt.text.toString()

                    val postRequest = PostLoginRequest(email = email, password = password)
                    LoginService(this).tryPostLogin(postRequest)

                    //var shared = ApplicationClass.sSharedPreferences.getString("X-ACCESS-TOKEN",null).toString()

                }

            }
        }

    }

    //[????????? ??????]
    //LoginHandler
    private val login_handler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                Login -> {
                    binding.loginFalseHint.visibility = View.VISIBLE
                    binding.loginFalseHint.postDelayed({
                        binding.loginFalseHint.visibility = View.INVISIBLE
                    },3000)
                }
            }
        }
    }
    //LoginThread
    inner class LoginThread : Thread() {
        var run = true
        override fun run() {
            while (run){

                val msg = Message()
                msg.what = Login
                login_handler.sendMessage(msg)

                run = false
            }
            super.run()
        }
    }

    fun goSignup(){
        binding.loginSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    fun goHome(){
        binding.loginQuitBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onPostLoginSuccess(response: LoginResponse) {
        jwt = response.result.jwt

        check = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
        ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.X_ACCESS_TOKEN, response.result.jwt).apply()
        Log.d("??????", "$jwt, $userId")

        //code = response.code
        Log.d("??????", "$response.code")

        //????????? api??? ?????? ??????????????? ?????? ??? main?????? ??????, ???????????? ????????? dialog
        if(response.code == 1000 && check == jwt){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else if(response.code != 1000 || check != jwt){
            val builder = AlertDialog.Builder(this)
            builder.setMessage("???????????? ????????? ?????? ??????????????? ???????????? ????????????.")
                .setPositiveButton("??????",
                    DialogInterface.OnClickListener { dialogInterface, id ->
                    })
            builder.show()
        }
    }

    override fun onPostLoginFailure(message: String) {
        Log.d("??????", "??????: $message")
    }

}