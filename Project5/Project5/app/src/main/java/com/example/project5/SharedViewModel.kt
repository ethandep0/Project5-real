import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel : ViewModel() {
    var translatedText = MutableLiveData<String>() //variable to pass translated language between two.
    var sourceLanguage = MutableLiveData<String>() // from language
    var translationLanguage = MutableLiveData<String>() //to language
}