package com.example.wayangclass.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wayangclass.adapter.QuizListAdapter
import com.example.wayangclass.databinding.ActivityMainBinding
import com.example.wayangclass.model.QuizModel
import com.example.wayangclass.viewmodel.QuizViewModel
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var quizModelList : MutableList<QuizModel>
    private lateinit var viewModel: QuizViewModel
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

//        quizModelList = mutableListOf()
//        getDataFromFirebase()
        setupRecyclerView()

        // Observasi perubahan data dari ViewModel
        viewModel.quizList.observe(this) { quizList ->
            // Update adapter dengan data baru
            adapter.updateData(quizList) // Anda perlu menambahkan fungsi updateData di adapter
            binding.progressBar.visibility = View.GONE // Sembunyikan progress bar setelah data diterima
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Panggil fungsi untuk mengambil data
        viewModel.fetchDataFromFirebase()

    }

    private fun setupRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = QuizListAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

//    private fun getDataFromFirebase(){
//        binding.progressBar.visibility = View.VISIBLE
//        FirebaseDatabase.getInstance().reference
//            .get()
//            .addOnSuccessListener { dataSnapshot->
//                if(dataSnapshot.exists()){
//                    for (snapshot in dataSnapshot.children){
//                        val quizModel = snapshot.getValue(QuizModel::class.java)
//                        if (quizModel != null) {
//                            quizModelList.add(quizModel)
//                        }
//                    }
//                }
//                setupRecyclerView()
//            }
//
//
//    }
}