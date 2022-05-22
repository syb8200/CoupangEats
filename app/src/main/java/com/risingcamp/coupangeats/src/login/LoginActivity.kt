package com.risingcamp.coupangeats.src.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityLoginBinding
import com.risingcamp.coupangeats.src.MainActivity
import com.risingcamp.coupangeats.src.signup.SignupActivity

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
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
            if(binding.loginEmailEdt.text!!.contains("@") && binding.loginEmailEdt.text!!.isNotEmpty() && binding.loginPwdEdt.text!!.isNotEmpty()){
                showCustomToast("로그인 성공!")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else{
                showCustomToast("로그인 실패...")
            }
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

}