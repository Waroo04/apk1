package com.example.apk1

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apk1.databinding.FragmentAdminBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable back button in Toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.navigation_home)
            }
        })

        // Fetch and decode Base64 credentials
        db.collection("credentials").document("admin_access")
            .get()
            .addOnSuccessListener { document ->
                val encoded = document.getString("admin_access")
                if (encoded != null) {
                    val decoded = String(Base64.decode(encoded, Base64.DEFAULT))

                    if (decoded == "Admin:password") {
                        Log.d("CTF", "Admin Access Granted!")

                        // Fetch hidden flag
                        db.collection("admin_panel").document("hidden_flag")
                            .get()
                            .addOnSuccessListener { flagDoc ->
                                val flag = flagDoc.getString("hidden_flag")
                                Log.d("FLAG", "FLAG: $flag") // Hidden in logs
                            }
                    } else {
                        Log.d("CTF", "Access Denied!")
                    }
                }
            }
    }
}
