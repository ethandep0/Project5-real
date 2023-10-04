package com.example.project5

import SharedViewModel
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // initialize the ViewModel
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        // observe changes to the translatedText property
        sharedViewModel.translatedText.observe(this) { translatedText ->
            //update text when change detected (when text is translated)
            binding.translatedTextView.text = translatedText
        }

        //set default languages to avoid problems
        sharedViewModel.sourceLanguage.value = "english"
        sharedViewModel.translationLanguage.value = "english"


        // source language RadioGroup
        // set shared view model source language
        val sourceRadioGroup: RadioGroup = binding.sourceRadioGroup
        sourceRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.englishSourceButton.id -> {
                    sharedViewModel.sourceLanguage.value = "english"
                }
                binding.spanishSourceButton.id -> {
                    sharedViewModel.sourceLanguage.value = "spanish"
                }
                binding.germanSourceButton.id -> {
                    sharedViewModel.sourceLanguage.value = "german"
                }
            }
        }

        //translation language RadioGroup
        // set shared view model translation language
        val translationRadioGroup: RadioGroup = binding.translationRadioGroup
        translationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.englishTranslationButton.id -> {
                    sharedViewModel.translationLanguage.value = "english"
                }
                binding.spanishTranslationButton.id -> {
                    sharedViewModel.translationLanguage.value = "spanish"
                }
                binding.germanTranslationButton.id -> {
                    sharedViewModel.translationLanguage.value = "german"
                }
            }

        }
    }
}