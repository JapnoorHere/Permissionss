package com.example.permissionss

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.permissionss.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var img = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.ivImage.setImageURI(it)

        }

        var singlePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                img.launch("image/*")
                Toast.makeText(this,"Permission allowed", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnPermission.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    img.launch("image/*")
                }
                shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)->{
                   var alertDialog=AlertDialog.Builder(this)
                    alertDialog.setTitle("Open Settings")
                    alertDialog.setPositiveButton("Open"){_,_->
                    var intent = Intent()
                        startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS))
                    }
                    alertDialog.show()
                }
                else ->
                    singlePermission.launch(READ_EXTERNAL_STORAGE)
            }
        }
    }

    }