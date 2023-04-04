package com.kaplan57.azerimedtask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaplan57.azerimedtask.R
import com.kaplan57.azerimedtask.adapter.PhonesAdapter
import com.kaplan57.azerimedtask.databinding.ActivityMainBinding
import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity
import com.kaplan57.azerimedtask.local_db.roomdatabase.PhonesAppDatabase
import com.kaplan57.azerimedtask.network.instance.RetrofitInstance
import com.kaplan57.azerimedtask.repository.PhonesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainPage : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var mAdapter: PhonesAdapter

    private val searchView by lazy {
        binding.toolBar.menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        searchView.queryHint = "Search for characters..."
        setUpListeners()
        searchForItems()
    }

    private fun searchForItems() {
        PhonesRepository.getAllData(
            this,
            {
                visibilitiesOfViews(false)
            },
            {
                setUpRecyclerView(it)
                binding.progressBar.visibility = View.GONE
            }
        )
    }

    private fun callGlobalData() {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val response = try {
                RetrofitInstance.instance.getData()
            } catch (e:Exception){
                Log.d("aaaaa", "callGlobalData: $e")
                return@launch
            }

            if(response.isSuccessful)
                response.body()?.let{
                    setUpRecyclerView(it.list)
                    setToLocalDBAsync(it.list)
                    visibilitiesOfViews(true)
                }
            else
                Log.d("aaaaa", "callGlobalData: ${response.message()} ")

            binding.progressBar.visibility = View.GONE

        }
    }

    private fun setToLocalDBAsync(list: List<PhonesEntity>) {
            PhonesRepository.setAllData(this@MainPage,list)
    }

    private fun visibilitiesOfViews(state:Boolean) {
        Log.d("aaaaaa", "visibilitiesOfViews:$state ")
        binding.progressBar.visibility = if(state) View.VISIBLE else View.GONE
        binding.btnSynchronization.visibility = if(state) View.GONE else View.VISIBLE
    }

    private fun setUpListeners() {
        binding.btnSynchronization.setOnClickListener {
            callGlobalData()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getDataUsingQuery(newText)
                return false
            }

        })

        binding.deleteAll.setOnClickListener {
            PhonesRepository.deleteAll(
                this
            )
                mAdapter.notifyItemChanged(listOf())
                visibilitiesOfViews(false)

        }
    }

    private fun getDataUsingQuery(text: String?) {
        if(text == "") {
            PhonesRepository.getAllData(
                this,
                null
            ) {
                Log.d("aaaaaa", "getDataUsingQuery: ")
                mAdapter.notifyItemChanged(it)
            }
        }
        else
            PhonesRepository.getResearchedData(
                this,
                text!!
            ) {
                mAdapter.notifyItemChanged(it)
            }
    }

    private fun setUpRecyclerView(list: List<PhonesEntity>) = binding.recyclerView.apply {
        mAdapter = PhonesAdapter(list,this@MainPage)
        adapter = mAdapter
        layoutManager = LinearLayoutManager(this@MainPage)
    }
}