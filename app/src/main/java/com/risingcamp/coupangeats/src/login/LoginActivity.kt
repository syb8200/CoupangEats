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
    var jwt : String? = null

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
                    //포커스시
                    binding.loginPwdLayout.setBackgroundResource(R.drawable.login_edt_selected)
                } else{
                    //포커스 뺐겼을시
                    binding.loginPwdLayout.setBackgroundResource(R.drawable.login_edt_unselected)
                }
            }
        })
    }

    fun login(){
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmailEdt.text.toString()
            val password = binding.loginPwdEdt.text.toString()

            val postRequest = PostLoginRequest(email = email, password = password)
            LoginService(this).tryPostLogin(postRequest)

            var shared = ApplicationClass.sSharedPreferences.getString("X-ACCESS-TOKEN",null).toString()


            if(binding.loginEmailEdt.length()==0 && binding.loginPwdEdt.length()==0){
                binding.loginFalseHint.text = "아이디를 입력해주세요"
                loginThread = LoginThread()
                LoginThread().start()

            } else{ //둘중 하나라도 뭐가 적혀있을 때
                if(binding.loginEmailEdt.text!!.contains("@") == false){
                    binding.loginFalseHint.text = "아이디는 이메일주소 형식으로 입력해주세요"
                    loginThread = LoginThread()
                    LoginThread().start()
                } else{
                    if(binding.loginPwdEdt.text!!.isEmpty()){
                        binding.loginFalseHint.text = "비밀번호를 입력해주세요"
                        loginThread = LoginThread()
                        LoginThread().start()
                    }
                }

                if(binding.loginPwdEdt.text!!.isNotEmpty() && binding.loginEmailEdt.text!!.isEmpty()){
                    binding.loginFalseHint.text = "아이디를 입력해주세요"
                    loginThread = LoginThread()
                    LoginThread().start()
                }

                if(binding.loginEmailEdt.text!!.contains("@") && binding.loginEmailEdt.text!!.isNotEmpty() && binding.loginPwdEdt.text!!.isNotEmpty()){
                    //로그인 api로 정보 일치하는지 확인 후 main으로 이동, 일치하지 않으면 dialog
                    if(shared == jwt){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else{
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("입력하신 아이디 또는 비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("확인",
                            DialogInterface.OnClickListener { dialogInterface, id ->
                            })
                        builder.show()
                    }

                }

            }
        }

    }

    //[로그인 버튼]
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
    }

    override fun onPostLoginFailure(message: String) {

    }

}