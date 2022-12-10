package com.project.pokedex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.project.pokedex.data.models.Team
import com.project.pokedex.databinding.FragmentTeamsBinding
import com.project.pokedex.ui.adapters.TeamsAdapter


class TeamsFragment : Fragment() {

    private lateinit var binding: FragmentTeamsBinding
    private lateinit var teamsAdapter: TeamsAdapter
    var firebaseDatabase: FirebaseDatabase? = null
    var database: DatabaseReference? = null
    var databaseReference: DatabaseReference? = null

    private val onClickHandler: (String?) -> Unit ={
        // Navigate to Teams Detail fragment
        val manager: FragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Key", it)
        val fragment = TeamsDetailFragment()
        fragment.arguments = bundle
        transaction.replace(com.project.pokedex.R.id.fragment_container, fragment).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("Team")
        getdataFromDB()
    }

    private fun getdataFromDB() {
        database = FirebaseDatabase.getInstance().reference.child("Team")
        val options: FirebaseRecyclerOptions<Team> = FirebaseRecyclerOptions.Builder<Team>()
            .setQuery(database!!, Team::class.java)
            .build()
        teamsAdapter = TeamsAdapter(onClickHandler, options)
        binding.rvTeams.adapter = teamsAdapter

        // Check if there is any data in DB, show message if there is not
        database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                binding.tvEmptyState.isVisible = !dataSnapshot.exists()
                binding.tvHeader.isVisible = dataSnapshot.exists()
                if(dataSnapshot.exists() || binding.tvEmptyState.isVisible){
                    binding.progressBar.isInvisible
                } else {
                    binding.progressBar.isVisible = true
                }
                teamsAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }


    // Tell app to start getting data from DB
    override fun onStart() {
        super.onStart()
        teamsAdapter.startListening()
    }

    // Tell the app to stop getting data from DB
    override fun onStop() {
        super.onStop()
        teamsAdapter.stopListening()
    }
}