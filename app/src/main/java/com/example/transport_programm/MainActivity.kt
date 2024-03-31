package com.example.transport_programm

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import com.example.transport_programm.databinding.ActivityMainBinding
import com.example.transport_programm.databinding.DialogAddCharacterBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CharacterAdapter

    private val  data = mutableListOf(
        Character(id= Random.nextLong(), name= "Henry", isCustom= false),
        Character(id= Random.nextLong(), name= "Josh", isCustom= false),
        Character(id= Random.nextLong(), name= "Liza", isCustom= false),
        Character(id= Random.nextLong(), name= "Ann", isCustom= false),
        Character(id= Random.nextLong(), name= "Ivan", isCustom= false),
        Character(id= Random.nextLong(), name= "Arthur", isCustom= false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapter(data) {
            deleteCharacter(it)
        }
        binding.spinnerList.adapter = adapter //TODO to complete

        binding.spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create Character")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { d, wich ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()){
                    createCharacter(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = Random.nextLong(),
            name = name,
            isCustom = true
        )
        data.add(character)
        adapter.notifyDataSetChanged()
    }

//    private fun showCharacterInfo(character: Character) {
//        val dialog = android.app.AlertDialog.Builder(this)
//            .setTitle(character.name)
//            .setMessage(getString(R.string.character_info, character.name, character.id))
//            .setPositiveButton("OK") {_, _ -> }
//            .create()
//        dialog.show()
//    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, whitch ->
            if (whitch == DialogInterface.BUTTON_POSITIVE) {
                data.remove(character)
                adapter.notifyDataSetChanged()
            }
        }
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Delete Character")
            .setMessage("Are you sure you eant to  delete the character ${character}?")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }
}