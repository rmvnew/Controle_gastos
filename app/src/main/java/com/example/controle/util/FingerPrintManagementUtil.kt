//package com.example.controle.util
//
//
//
//import androidx.biometric.BiometricPrompt
//import androidx.fragment.app.FragmentActivity
//import java.util.concurrent.Executor
//
//class FingerPrintManagementUtil(
//    activity:FragmentActivity,
//    executor:Executor,
//    callback: android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
//) {
//
//    private var biometricPrompt = BiometricPrompt(activity,executor,callback,null)
//
//    fun showBiometricPronpt(){
//        val prompt: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
//            .setTitle("Sign in")
//            .setSubtitle("Expense Manager")
//            .setDescription("Confirm fingerprint to continue")
//            .setNegativeButtonText("Cancel")
//            .build()
//
//        biometricPrompt.authenticate(prompt)
//    }
//
//}