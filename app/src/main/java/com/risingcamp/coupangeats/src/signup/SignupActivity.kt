package com.risingcamp.coupangeats.src.signup

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivitySignupBinding
import com.risingcamp.coupangeats.src.login.LoginActivity


var check_all = true
var check_1 = true
var check_2 = true
var check_3 = true
var check_4 = true
var check_5 = true
var check_6 = true
var check_7 = true
var check_8 = true
var check_9 = true
var check_10 = true
var click_signup = true

var check_sum = 0
var check_necessary_sum = 0


class SignupActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setEmailEdt()
        setPwdEdt()
        setNameEdt()
        setPhoneEdt()
        termsAllAgree()
        termsAgree()
        setSignupBtn()
        goLogin()
    }

    fun setEmailEdt(){
        binding.signupEmailEdt.setOnFocusChangeListener { view, gainFocus ->
            binding.signupEmailLine.visibility = View.VISIBLE

            if(gainFocus){
            } else{

                if(binding.signupEmailEdt.length()==0){
                    hideKeyboard()
                    binding.signupEmailLine.setBackgroundColor(Color.parseColor("#DE263F"))
                    binding.signupEmailWrongHint.visibility = View.VISIBLE
                } else{
                    hideKeyboard()
                    if(binding.signupEmailEdt.text!!.contains("@")){
                        binding.signupEmailComplete.visibility = View.VISIBLE
                        binding.signupEmailWrongHint.visibility = View.GONE
                        binding.signupEmailLine.visibility = View.GONE
                    } else{
                        binding.signupEmailLine.setBackgroundColor(Color.parseColor("#DE263F"))
                        binding.signupEmailWrongHint.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun setPwdEdt(){
        hideKeyboard()
        binding.signupPwdEdt.setOnFocusChangeListener { view, getFocus ->
            binding.signupPwdLine.visibility = View.VISIBLE

            if(getFocus){
                binding.signupPwdEdt.addTextChangedListener(object: TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        hideKeyboard()
                        binding.signupPwdLine.setBackgroundColor(Color.parseColor("#DE263F"))
                        binding.signupPwdHintLayout1.visibility = View.VISIBLE
                        binding.signupPwdHintLayout2.visibility = View.VISIBLE
                        binding.signupPwdHintLayout3.visibility = View.VISIBLE

                        if(binding.signupPwdEdt.text!=binding.signupEmailEdt.text && binding.signupPwdEdt.length()!=0){
                            binding.signupPwdHintImg2.setImageResource(R.drawable.ic_signup_email_complete)
                            binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                            binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#107E25"))

                            binding.signupPwdHintImg3.setImageResource(R.drawable.ic_signup_email_complete)
                            binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                            binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#107E25"))
                        } else{
                            binding.signupPwdHintImg2.setImageResource(R.drawable.ic_signup_pwd_hint_1)
                            binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                            binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#DE263F"))

                            binding.signupPwdHintImg3.setImageResource(R.drawable.ic_signup_pwd_hint_1)
                            binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                            binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#DE263F"))
                        }

                        if(binding.signupPwdEdt.text!!.length >= 8 && binding.signupPwdEdt.text!!.length <= 20 && binding.signupPwdEdt.text != binding.signupEmailEdt.text){
                            binding.signupPwdHintLayout1.visibility = View.GONE
                            binding.signupPwdHintLayout2.visibility = View.GONE
                            binding.signupPwdHintLayout3.visibility = View.GONE
                            binding.signupPwdHintLayout4.visibility = View.VISIBLE
                            binding.signupPwdComplete.visibility = View.VISIBLE
                            binding.signupPwdLine.setBackgroundColor(Color.parseColor("#396AF1"))
                        }
                    }
                    override fun afterTextChanged(p0: Editable?) {
                    }
                })
            } else{
                hideKeyboard()
                binding.signupPwdLine.setBackgroundColor(Color.parseColor("#DE263F"))
                binding.signupPwdHintLayout1.visibility = View.VISIBLE
                binding.signupPwdHintLayout2.visibility = View.VISIBLE
                binding.signupPwdHintLayout3.visibility = View.VISIBLE

                if(binding.signupPwdEdt.text!=binding.signupEmailEdt.text && binding.signupPwdEdt.length()!=0){
                    binding.signupPwdHintImg2.setImageResource(R.drawable.ic_signup_email_complete)
                    binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                    binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#107E25"))

                    binding.signupPwdHintImg3.setImageResource(R.drawable.ic_signup_email_complete)
                    binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                    binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#107E25"))
                } else{
                    binding.signupPwdHintImg2.setImageResource(R.drawable.ic_signup_pwd_hint_1)
                    binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                    binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#DE263F"))

                    binding.signupPwdHintImg3.setImageResource(R.drawable.ic_signup_pwd_hint_1)
                    binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                    binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#DE263F"))
                }

                if(binding.signupPwdEdt.text!!.length >= 8 && binding.signupPwdEdt.text!!.length <= 20 && binding.signupPwdEdt.text != binding.signupEmailEdt.text){
                    binding.signupPwdHintLayout1.visibility = View.GONE
                    binding.signupPwdHintLayout2.visibility = View.GONE
                    binding.signupPwdHintLayout3.visibility = View.GONE
                    binding.signupPwdHintLayout4.visibility = View.VISIBLE
                    binding.signupPwdComplete.visibility = View.VISIBLE
                    binding.signupPwdLine.visibility = View.GONE
                }
            }
        }
    }

    fun setNameEdt(){
        binding.signupNameEdt.setOnFocusChangeListener { view, getFocus ->
            binding.signupNameLine.visibility = View.VISIBLE

            if(getFocus){
            } else{
                hideKeyboard()
                if(binding.signupNameEdt.length()==0){
                    binding.signupNameLine.setBackgroundColor(Color.parseColor("#DE263F"))
                    binding.signupNameWrongHint.visibility = View.VISIBLE
                } else{
                    binding.signupNameLine.visibility = View.GONE
                    binding.signupNameWrongHint.visibility = View.GONE
                    binding.signupNameComplete.visibility = View.VISIBLE
                }
            }
        }
    }

    fun setPhoneEdt(){
        binding.signupPhoneEdt.setOnFocusChangeListener { view, getFocus ->
            binding.signupPhoneLine.visibility = View.VISIBLE

            if(getFocus){
            } else{
                hideKeyboard()
                if(binding.signupPhoneEdt.length()!=0 && binding.signupPhoneEdt.text!!.startsWith("010") && binding.signupPhoneEdt.length()==11){
                    binding.signupPhoneLine.visibility = View.GONE
                    binding.signupPhoneWrongHint.visibility = View.GONE
                    binding.signupPhoneComplete.visibility = View.VISIBLE
                } else{
                    binding.signupPhoneLine.setBackgroundColor(Color.parseColor("#DE263F"))
                    binding.signupPhoneWrongHint.visibility = View.VISIBLE
                }
            }
        }
    }

    fun hideKeyboard(){
        binding.signupTotalLayout.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.signupEmailEdt.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.signupPwdEdt.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.signupNameEdt.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.signupPhoneEdt.windowToken, 0)

            binding.signupEmailEdt.clearFocus()
            binding.signupPwdEdt.clearFocus()
            binding.signupNameEdt.clearFocus()
            binding.signupPhoneEdt.clearFocus()
        }
    }

    fun termsAllAgree(){
        binding.signupTermsAllAgreeLayout.setOnClickListener {
            if(check_all == true){
                setTermsAllChecked()
                check_sum = 7
                check_necessary_sum = 5
                check_all = false
                check_1 = false
                check_2 = false
                check_3 = false
                check_4 = false
                check_5 = false
                check_6 = false
                check_7 = false
                check_8 = false
                check_9 = false
                check_10 = false
                Log.d("체크썸","$check_sum / $check_necessary_sum")
            } else{
                if(check_all == false){
                    setTermsAllNotChecked()
                    check_sum = 0
                    check_necessary_sum = 0
                    check_all = true
                    check_1 = true
                    check_2 = true
                    check_3 = true
                    check_4 = true
                    check_5 = true
                    check_6 = true
                    check_7 = true
                    check_8 = true
                    check_9 = true
                    check_10 = true
                    Log.d("체크썸","$check_sum / $check_necessary_sum")
                }
            }
        }
    }

    fun setTermsAllChecked(){
        binding.signupTermsAllAgreeCheckbox.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox1.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox2.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox3.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox4.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox5.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox6.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_2)
        binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_2)
    }

    fun setTermsAllNotChecked(){
        binding.signupTermsAllAgreeCheckbox.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox1.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox2.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox3.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox4.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox5.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox6.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_1)
        binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_1)
    }

    fun termsAgree(){
        binding.signupTermsLine1.setOnClickListener {
            if(check_1==true){
                binding.signupTermsCheckbox1.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_1 = false
                checkSum10()
            } else{
                binding.signupTermsCheckbox1.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_1 = true
                checkSum10()
            }
        }
        binding.signupTermsLine2.setOnClickListener {
            if(check_2==true){
                binding.signupTermsCheckbox2.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_2 = false
                checkSum10()

            } else{
                binding.signupTermsCheckbox2.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_2 = true
                checkSum10()
            }
        }
        binding.signupTermsLine3.setOnClickListener {
            if(check_3==true){
                binding.signupTermsCheckbox3.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_3 = false
                checkSum10()

            } else{
                binding.signupTermsCheckbox3.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_3 = true
                checkSum10()
            }
        }
        binding.signupTermsLine4.setOnClickListener {
            if(check_4==true){
                binding.signupTermsCheckbox4.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_4 = false
                checkSum10()

            } else{
                binding.signupTermsCheckbox4.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_4 = true
                checkSum10()
            }
        }
        binding.signupTermsLine5.setOnClickListener {
            if(check_5==true){
                binding.signupTermsCheckbox5.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_5 = false
                checkSum10()

            } else{
                binding.signupTermsCheckbox5.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_5 = true
                checkSum10()
            }
        }
        binding.signupTermsLine6.setOnClickListener {
            if(check_6==true){
                binding.signupTermsCheckbox6.setImageResource(R.drawable.ic_signup_terms_agree_2)
                binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_2)
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_2)
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_2)
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 5
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_6 = false
                check_7 = false
                check_8 = false
                check_9 = false
                check_10 = false
                checkSum10()
            } else{
                binding.signupTermsCheckbox6.setImageResource(R.drawable.ic_signup_terms_agree_1)
                binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_1)
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_1)
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_1)
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 5
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_6 = true
                check_7 = true
                check_8 = true
                check_9 = true
                check_10 = true
                checkSum10()
            }
        }
        binding.signupTermsLine7.setOnClickListener {
            if(check_7==true){
                binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_2)
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_2)
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_2)
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 4
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_7 = false
                check_8 = false
                check_9 = false
                check_10 = false
                checkSum10()
            } else{
                binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_1)
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_1)
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_1)
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 4
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_7 = true
                check_8 = true
                check_9 = true
                check_10 = true
                checkSum10()
            }
        }
        binding.signupTermsLine8.setOnClickListener {
            if(check_8==true){
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_8 = false
                checkSum10()
            } else{
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_8 = true
                checkSum10()
            }
        }
        binding.signupTermsLine9.setOnClickListener {
            if(check_9==true){
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_9 = false
                checkSum10()
            } else{
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_9 = true
                checkSum10()
            }
        }
        binding.signupTermsLine10.setOnClickListener {
            if(check_10==true){
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_10 = false
                checkSum10()
            } else{
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_10 = true
                checkSum10()
            }
        }
    }

    fun checkSum10(){
        if(check_sum == 10){
            binding.signupTermsAllAgreeCheckbox.setImageResource(R.drawable.ic_signup_terms_agree_2)
        } else if(check_sum < 10){
            binding.signupTermsAllAgreeCheckbox.setImageResource(R.drawable.ic_signup_terms_agree_1)
        }
    }

    fun checkSum5(){
        if(check_necessary_sum==5){
            binding.signupTermsWarnLayout.visibility = View.GONE
            if(check_sum == 10){
                binding.signupTermsAllAgreeCheckbox.setImageResource(R.drawable.ic_signup_terms_agree_2)
            }

        }
    }

    fun setSignupBtn(){
        binding.signupComplete.setOnClickListener {
            if(check_necessary_sum == 5){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else if(check_necessary_sum < 5){
                binding.signupTermsWarnLayout.visibility = View.VISIBLE
            }
        }
    }

    fun goLogin(){
        binding.signupQuitBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}