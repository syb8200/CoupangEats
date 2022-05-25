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
import com.risingcamp.coupangeats.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.risingcamp.coupangeats.config.ApplicationClass.Companion.sSharedPreferences
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivitySignupBinding
import com.risingcamp.coupangeats.src.login.LoginActivity
import com.risingcamp.coupangeats.src.signup.models.GetUsersPhone.GetUsersPhoneResponse
import com.risingcamp.coupangeats.src.signup.models.getUsersEmail.GetUsersEmailResponse
import com.risingcamp.coupangeats.src.signup.models.postSignup.PostSignupRequest
import com.risingcamp.coupangeats.src.signup.models.postSignup.SignupResponse

class SignupActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate), SignupInterface {

    var isUserExist : Boolean? = null
    var isUserExist_phone : Boolean? = null
    var userEmail : String? = null

    var check_all = false
    var check_1 = false
    var check_2 = false
    var check_3 = false
    var check_4 = false
    var check_5 = false
    var check_6 = false
    var check_7 = false
    var check_8 = false
    var check_9 = false
    var check_10 = false

    var check_sum = 0
    var check_necessary_sum = 0

    var jwt : String? = null

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
                binding.signupEmailComplete.visibility = View.GONE
            } else{

                if(binding.signupEmailEdt.length()==0){
                    hideKeyboard()
                    binding.signupEmailLine.setBackgroundColor(Color.parseColor("#DE263F"))
                    binding.signupEmailWrongHint.visibility = View.VISIBLE
                } else{
                    hideKeyboard()

                    var emailEdt = binding.signupEmailEdt.text.toString()
                    SignupService(this).tryGetUsersEmail(emailEdt)

                    if(isUserExist == true){
                        //중복O
                        binding.signupEmailLine.setBackgroundColor(Color.parseColor("#DE263F"))
                        binding.signupAlreadyHaveEmail.visibility = View.VISIBLE
                        binding.signupLogin.visibility = View.VISIBLE
                        binding.signupFindPwd.visibility = View.VISIBLE

                        binding.signupLogin.setOnClickListener {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    } else{
                        //중복X
                        if(binding.signupEmailEdt.text!!.contains("@")){
                            binding.signupEmailWrongHint.visibility = View.GONE
                            binding.signupEmailLine.visibility = View.GONE
                            binding.signupAlreadyHaveEmail.visibility = View.GONE
                            binding.signupLogin.visibility = View.GONE
                            binding.signupFindPwd.visibility = View.GONE
                            binding.signupEmailComplete.visibility = View.VISIBLE
                        } else{
                            binding.signupAlreadyHaveEmail.visibility = View.GONE
                            binding.signupLogin.visibility = View.GONE
                            binding.signupFindPwd.visibility = View.GONE
                            binding.signupEmailLine.setBackgroundColor(Color.parseColor("#DE263F"))
                            binding.signupEmailWrongHint.visibility = View.VISIBLE
                        }

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
                binding.signupPwdHintLayout1.visibility = View.VISIBLE
                binding.signupPwdHintImg1.imageTintList = ColorStateList.valueOf(Color.parseColor("#888888"))
                binding.signupPwdHintTxt1.setTextColor(Color.parseColor("#888888"))

                binding.signupPwdHintLayout2.visibility = View.VISIBLE
                binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#888888"))
                binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#888888"))

                binding.signupPwdHintLayout3.visibility = View.VISIBLE
                binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#888888"))
                binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#888888"))


                binding.signupPwdEdt.addTextChangedListener(object: TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        hideKeyboard()
                        binding.signupPwdLine.setBackgroundColor(Color.parseColor("#DE263F"))

                        binding.signupPwdHintImg1.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                        binding.signupPwdHintTxt1.setTextColor(Color.parseColor("#DE263F"))

                        binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                        binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#DE263F"))

                        binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                        binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#DE263F"))

                        if(binding.signupPwdEdt.text!=binding.signupEmailEdt.text && binding.signupPwdEdt.length()!=0){
                            binding.signupPwdHintTxt1.visibility = View.VISIBLE
                            binding.signupPwdHintImg1.visibility = View.VISIBLE

                            binding.signupPwdHintImg2.setImageResource(R.drawable.ic_signup_email_complete)
                            binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                            binding.signupPwdHintImg2.visibility = View.VISIBLE
                            binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#107E25"))
                            binding.signupPwdHintTxt2.visibility = View.VISIBLE

                            binding.signupPwdHintImg3.setImageResource(R.drawable.ic_signup_email_complete)
                            binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                            binding.signupPwdHintImg3.visibility = View.VISIBLE
                            binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#107E25"))
                            binding.signupPwdHintTxt3.visibility = View.VISIBLE
                        } else{
                            binding.signupPwdHintTxt1.visibility = View.VISIBLE
                            binding.signupPwdHintImg1.visibility = View.VISIBLE

                            binding.signupPwdHintImg2.setImageResource(R.drawable.ic_signup_pwd_hint_1)
                            binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                            binding.signupPwdHintImg2.visibility = View.VISIBLE
                            binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#DE263F"))
                            binding.signupPwdHintTxt2.visibility = View.VISIBLE

                            binding.signupPwdHintImg3.setImageResource(R.drawable.ic_signup_pwd_hint_1)
                            binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                            binding.signupPwdHintImg3.visibility = View.VISIBLE
                            binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#DE263F"))
                            binding.signupPwdHintTxt3.visibility = View.VISIBLE
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
                    binding.signupPwdHintImg1.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                    binding.signupPwdHintTxt1.setTextColor(Color.parseColor("#DE263F"))

                    binding.signupPwdHintImg2.setImageResource(R.drawable.ic_signup_email_complete)
                    binding.signupPwdHintImg2.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                    binding.signupPwdHintTxt2.setTextColor(Color.parseColor("#107E25"))

                    binding.signupPwdHintImg3.setImageResource(R.drawable.ic_signup_email_complete)
                    binding.signupPwdHintImg3.imageTintList = ColorStateList.valueOf(Color.parseColor("#107E25"))
                    binding.signupPwdHintTxt3.setTextColor(Color.parseColor("#107E25"))
                } else{
                    binding.signupPwdHintImg1.imageTintList = ColorStateList.valueOf(Color.parseColor("#DE263F"))
                    binding.signupPwdHintTxt1.setTextColor(Color.parseColor("#DE263F"))

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
                binding.signupNameComplete.visibility = View.GONE
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
                binding.signupPhoneComplete.visibility = View.GONE
            } else{

                hideKeyboard()

                if(binding.signupPhoneEdt.length()!=0 && binding.signupPhoneEdt.text!!.startsWith("010") && binding.signupPhoneEdt.length()==11){

                    var phoneEdt = binding.signupPhoneEdt.text.toString()
                    SignupService(this).tryGetUsersPhone(phoneEdt)

                    if(isUserExist_phone==true){
                        binding.signupPhoneLine.visibility = View.VISIBLE
                        binding.signupPhoneLine.setBackgroundColor(Color.parseColor("#DE263F"))
                        binding.signupAlreadyHavePhoneEmail.text = userEmail
                        binding.signupAlreadyHavePhoneEmail.visibility = View.VISIBLE
                        binding.signupAlreadyHavePhone.visibility = View.VISIBLE
                        binding.signupAuthPhone.visibility = View.VISIBLE
                    } else{
                        binding.signupPhoneLine.visibility = View.GONE
                        binding.signupPhoneWrongHint.visibility = View.GONE
                        binding.signupAlreadyHavePhoneEmail.visibility = View.GONE
                        binding.signupAlreadyHavePhone.visibility = View.GONE
                        binding.signupAuthPhone.visibility = View.GONE
                        binding.signupPhoneComplete.visibility = View.VISIBLE
                    }

                } else{ //아무것도 안적혀있거나, 010으로 시작하지 않거나, 길이가 11자가 아니거나
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
            if(check_all == false){
                setTermsAllChecked()
                check_sum = 10
                check_necessary_sum = 5
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
            } else{
                if(check_all == true){
                    setTermsAllNotChecked()
                    check_sum = 0
                    check_necessary_sum = 0
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
            if(check_1==false){
                binding.signupTermsCheckbox1.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_1 = true
                checkSum10()
            } else{
                binding.signupTermsCheckbox1.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_1 = false
                checkSum10()
            }
        }
        binding.signupTermsLine2.setOnClickListener {
            if(check_2==false){
                binding.signupTermsCheckbox2.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_2 = true
                checkSum10()

            } else{
                binding.signupTermsCheckbox2.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_2 = false
                checkSum10()
            }
        }
        binding.signupTermsLine3.setOnClickListener {
            if(check_3==false){
                binding.signupTermsCheckbox3.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_3 = true
                checkSum10()

            } else{
                binding.signupTermsCheckbox3.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_3 = false
                checkSum10()
            }
        }
        binding.signupTermsLine4.setOnClickListener {
            if(check_4==false){
                binding.signupTermsCheckbox4.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_4 = true
                checkSum10()

            } else{
                binding.signupTermsCheckbox4.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_4 = false
                checkSum10()
            }
        }
        binding.signupTermsLine5.setOnClickListener {
            if(check_5==false){
                binding.signupTermsCheckbox5.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                check_necessary_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_5 = true
                checkSum10()

            } else{
                binding.signupTermsCheckbox5.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                check_necessary_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_5 = false
                checkSum10()
            }
        }
        binding.signupTermsLine6.setOnClickListener {
            if(check_6==false){
                binding.signupTermsCheckbox6.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_6 = true
                checkSum10()
            } else{
                binding.signupTermsCheckbox6.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_6 = false
                checkSum10()
            }
        }
        binding.signupTermsLine7.setOnClickListener {
            if(check_7==false){
                binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_7 = true
                checkSum10()
            } else{
                binding.signupTermsCheckbox7.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_7 = false
                checkSum10()
            }
        }
        binding.signupTermsLine8.setOnClickListener {
            if(check_8==false){
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_8 = true
                checkSum10()
            } else{
                binding.signupTermsCheckbox8.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_8 = false
                checkSum10()
            }
        }
        binding.signupTermsLine9.setOnClickListener {
            if(check_9==false){
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_9 = true
                checkSum10()
            } else{
                binding.signupTermsCheckbox9.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_9 = false
                checkSum10()
            }
        }
        binding.signupTermsLine10.setOnClickListener {
            if(check_10==false){
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_2)
                check_sum += 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_10 = true
                checkSum10()
            } else{
                binding.signupTermsCheckbox10.setImageResource(R.drawable.ic_signup_terms_agree_1)
                check_sum -= 1
                Log.d("체크썸","$check_sum / $check_necessary_sum")
                check_10 = false
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

    fun setSignupBtn(){
        binding.signupComplete.setOnClickListener {
            if(check_necessary_sum == 5){

                val email = binding.signupEmailEdt.text.toString()
                val password = binding.signupPwdEdt.text.toString()
                val name = binding.signupNameEdt.text.toString()
                val phone = binding.signupPhoneEdt.text.toString()
                val agreements = arrayListOf(check_1, check_2, check_3, check_4, check_5, check_6, check_7, check_8, check_9, check_10)

                val postRequest = PostSignupRequest(email = email, password = password,name = name, phone = phone, agreements = agreements)
                SignupService(this).tryPostSignup(postRequest)

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

//------------------------------------------------------------------------------------------------------------------------------------------------
    override fun onPostSignupSuccess(response: SignupResponse) {
        jwt = response.result.jwt
        sSharedPreferences.edit().putString(X_ACCESS_TOKEN, response.result.jwt).apply()
        Log.d("jwt", "$jwt")
    }
    override fun onPostSignupFailure(message: String) {
        Log.d("오류", "오류: $message")
    }


    override fun onGetUsersEmailSuccess(emailResponse: GetUsersEmailResponse) {
        isUserExist = emailResponse.result.userExist
        Log.d("유재존재여부", "존재여부: $isUserExist")
    }
    override fun onGetUsersEmailFailure(message: String) {
        Log.d("오류", "오류: $message")
    }


    override fun onGetUsersPhoneSuccess(phoneResponse: GetUsersPhoneResponse) {
        isUserExist_phone = phoneResponse.result.userExist
        userEmail = phoneResponse.result.userEmail
        Log.d("유저존재여부", "존재여부: $isUserExist_phone / 유저이메일: $userEmail")
    }
    override fun onGetUsersPhoneFailure(message: String) {
        Log.d("오류", "오류: $message")
    }
}