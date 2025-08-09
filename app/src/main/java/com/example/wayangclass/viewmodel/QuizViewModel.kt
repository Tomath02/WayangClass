package com.example.wayangclass.viewmodel

// Contoh QuizViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wayangclass.model.QuizModel
import com.google.firebase.database.FirebaseDatabase

class QuizViewModel : ViewModel() {

    private val _quizList = MutableLiveData<List<QuizModel>>()
    val quizList: LiveData<List<QuizModel>> = _quizList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDataFromFirebase() {
        _isLoading.value = true
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                val tempList = mutableListOf<QuizModel>()
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            tempList.add(quizModel)
                        }
                    }
                }
                _quizList.value = tempList
                _isLoading.value = false
            }
            .addOnFailureListener {
                // Handle error
                _isLoading.value = false
            }
    }
}
