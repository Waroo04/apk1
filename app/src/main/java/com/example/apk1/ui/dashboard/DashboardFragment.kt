package com.example.apk1.ui.dashboard
//
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.apk1.databinding.FragmentDashboardBinding
import java.io.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up ViewModel observer (if needed)
        dashboardViewModel.text.observe(viewLifecycleOwner) {
        }

        // Initialize SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // Save Button Click Listener
        binding.saveButton.setOnClickListener {
            val clickCount = sharedPreferences.getInt("clickCount", 0) + 1
            val obj = if (clickCount == 19) {
                VulnerableObject()
            } else {
                VulnerableObject()
            }

            saveObject(requireContext(), obj)
            Toast.makeText(requireContext(), "Object saved!", Toast.LENGTH_SHORT).show()

            // Update click count in SharedPreferences
            sharedPreferences.edit().putInt("clickCount", clickCount).apply()
        }

        // Load Button Click Listener
        saveButton()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }

    // Function to save an object to internal storage
    private fun saveObject(context: Context, obj: VulnerableObject) {
        try {
            context.openFileOutput("object.dat", Context.MODE_PRIVATE).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(obj)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}

private fun saveButton() {

}

// Serializable class to store the flag
class VulnerableObject : Serializable
