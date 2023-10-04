package com.example.project5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.project5.databinding.FragmentTranslatorInputBinding
import SharedViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

/**
 * A simple [Fragment] subclass.
 * Use the [TranslatorInputFrag.newInstance] factory method to
 * create an instance of this fragment.
 */

class TranslatorInputFrag : Fragment() {
    private var _binding: FragmentTranslatorInputBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslatorInputBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val textWatcher = object : TextWatcher {

            // called when the text changes
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //text typed
                val newText = s.toString()

                //TODO: ADD TRANSLATION LOGIC
                // - get text (bonus points: determine language then change source button?)
                // - translate to destination translation language

                //both in format: "english" , "spanish" , "german"
                val fromLang = sharedViewModel.sourceLanguage.value //from this language
                val toLang = sharedViewModel.translationLanguage.value //to this langauge

                val options = TranslatorOptions.Builder()

                //set source lang
                when {
                    fromLang == "english" -> options.setSourceLanguage(TranslateLanguage.ENGLISH)
                    fromLang == "spanish" -> options.setSourceLanguage(TranslateLanguage.SPANISH)
                    fromLang == "german" -> options.setSourceLanguage(TranslateLanguage.GERMAN)
                    else -> options.setSourceLanguage(TranslateLanguage.ENGLISH) //default
                }

                //set translation lang
                when {
                    toLang == "english" -> options.setTargetLanguage(TranslateLanguage.ENGLISH)
                    toLang == "spanish" -> options.setTargetLanguage(TranslateLanguage.SPANISH)
                    toLang == "german" -> options.setTargetLanguage(TranslateLanguage.GERMAN)
                    else -> options.setTargetLanguage(TranslateLanguage.ENGLISH) //default
                }

                val translator = Translation.getClient(options.build())

                val conditions = DownloadConditions.Builder()
                    .requireWifi()
                    .build()


                translator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {

                        //i dont think this is ever successful.

                        //if you put the translator thing below outside of this it will just hit the error so it never
                        //downloads the model.
                        translator.translate(newText)
                            .addOnSuccessListener { translatedText ->
                                sharedViewModel.translatedText.value = translatedText
                            }
                            .addOnFailureListener { exception ->
                                sharedViewModel.translatedText.value = "ERROR: Translation model had an issue while TRANSLATING - $exception"
                            }

                    }
                    .addOnFailureListener { exception ->
                        sharedViewModel.translatedText.value = "ERROR: Translation model had an issue while DOWNLOADING - $exception"
                    }

            }

            //interface so must override.
            //will prob not need these two.
            override fun afterTextChanged(p0: Editable?) {
                //prob dont need
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //prob dont need
            }

        }
        binding.translationEditText.addTextChangedListener(textWatcher)

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}