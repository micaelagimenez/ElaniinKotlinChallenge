package com.project.pokedex.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.pokedex.databinding.FragmentTeamsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TeamsDetailFragment : Fragment() {
    private lateinit var binding: FragmentTeamsDetailsBinding
    var key: String = ""
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamsDetailsBinding.inflate(inflater, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            key = bundle.getString("Key", "null")
        }

        // Read selected team's data from DB to fill input fields
        val textRef: DatabaseReference = database.child("Team").child(key)
        textRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot: DataSnapshot? = task.result
                val currentName = snapshot?.child("name")?.getValue(String::class.java)
                val currentNum = snapshot?.child("number")?.getValue(String::class.java)
                val currentType = snapshot?.child("type")?.getValue(String::class.java)
                binding.etName.setText(currentName)
                binding.etNumber.setText(currentNum)
                binding.etType.setText(currentType)
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btModify.setOnClickListener {
            database.child("Team").child(key).child("name").setValue(binding.etName.text.toString())
            database.child("Team").child(key).child("type").setValue(binding.etType.text.toString())
            database.child("Team").child(key).child("number")
                .setValue(binding.etNumber.text.toString())

            // Navigate back to Teams Fragment
            val manager: FragmentManager = parentFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(com.project.pokedex.R.id.fragment_container, TeamsFragment()).addToBackStack(null).commit()
        }
    }
}