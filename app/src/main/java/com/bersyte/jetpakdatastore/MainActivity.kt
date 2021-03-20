package com.bersyte.jetpakdatastore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.bersyte.jetpakdatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

/**
 * Done by : Isaias_cuvula
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get reference to our userManager class
        userManager = UserManager(applicationContext)

        binding.btnSave.setOnClickListener {
            // Gets the user input and saves it
            storeUser()
        }


        // this function retrieves the saved data
        // as soon as they are stored and even
        // after app is closed and started again
        observeData()
    }


    // this function saves the data to
    // preference data store on click of
    // save Button
    private fun storeUser() {

        val name = binding.etName.text.toString()
        val age = binding.etAge.text.toString().toInt()

        // Stores the values
        // Since the storeUser function of UserManager
        // class is a suspend function
        // So this has to be done in a coroutine scope
        lifecycleScope.launch {
            userManager.storeUser(age, name)
            Toast.makeText(
                this@MainActivity,
                "User Saved",
                Toast.LENGTH_SHORT
            ).show()

            //clear edit text
            binding.etName.text.clear()
            binding.etAge.text.clear()
        }
    }


    private fun observeData() {
        /**
         * Updates age
         * every time user age changes it will be observed by userAgeFlow
         * here it refers to the value returned from the userAgeFlow function
         * of UserManager class
         */

        userManager.userAgeFlow.asLiveData().observe(this) { age ->
            age?.let {
                binding.tvAge.text = "Age: $age"
            }
        }

        /**
         *Updates name
         *every time user name changes it will be observed by userNameFlow
         *here it refers to the value returned from the usernameFlow function
         *of UserManager class
         */

        userManager.userNameFlow.asLiveData().observe(this) { name ->
            name?.let {
                binding.tvName.text = "Name: $name"
            }
        }

    }
}