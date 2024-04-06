package com.example.transport_programm

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import com.example.transport_programm.databinding.ActivityMainBinding
import com.example.transport_programm.databinding.DialogAddCharacterBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CharacterAdapter
    private lateinit var state: ActivityState

    private val  data = mutableListOf(
        Character(id= Random.nextLong(), name= "Harly", isCustom= false, type= "auto", axesCount= 2, liftingCapacity = 1000),
        Character(id= Random.nextLong(), name= "Lada", isCustom= false, type= "auto", axesCount= 3, liftingCapacity = 4000),
        Character(id= Random.nextLong(), name= "Kamaz", isCustom= false, type= "auto", axesCount= 4, liftingCapacity = 10000),
        Character(id= Random.nextLong(), name= "Yamal", isCustom= false, type= "moto", axesCount= 2, liftingCapacity = 500),
        Character(id= Random.nextLong(), name= "Suzuki", isCustom= false, type= "moto", axesCount= 3, liftingCapacity = 250),
        Character(id= Random.nextLong(), name= "pitbyke", isCustom= false, type= "auto", axesCount= 2, liftingCapacity = 100)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
        binding.infoButton.setOnClickListener { SetRandomColors() }

        state = savedInstanceState?.getParcelable("activity_state")?: ActivityState(
            selectedItem = 0,
            current_color = 0
        )
        binding.spinnerList.setSelection(state.selectedItem)
        binding.InfoTextView.setBackgroundColor(state.current_color)
        binding.ExtraInfoTextView.setBackgroundColor(state.current_color)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("activity_state", state)
    }

    private fun setupList() {
        adapter = CharacterAdapter(data) {
            deleteCharacter(it)
        }
        binding.spinnerList.adapter = adapter

        binding.spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                val character = data[position]
                binding.InfoTextView.text =
                    getString(R.string.character_info,
                        character.liftingCapacity, character.axesCount)
                binding.ExtraInfoTextView.text = ""

                if (character.type == "auto") { binding.AutoRadio.isChecked = true }
                else if (character.type == "moto") { binding.MotoRadio.isChecked = true }
                state.selectedItem = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.InfoTextView.text = ""
                binding.ExtraInfoTextView.text = ""
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
                val type =
                    if (dialogBinding.AddAutoButton.isChecked){
                        "auto"
                    }
                    else {
                        "moto"
                    }

                val lift = dialogBinding.LiftingCapacityEdiText.text.toString()
                val axes = dialogBinding.AxesCountEditText.text.toString()

                createCharacter(name, type, lift, axes)

            }
            .create()
        dialog.show()
    }

    private fun SetRandomColors() {
        val rndColor = Color.rgb(
            kotlin.random.Random.nextInt(256),
            kotlin.random.Random.nextInt(256),
            kotlin.random.Random.nextInt(256)
        )
        binding.InfoTextView.setBackgroundColor(rndColor)
        binding.ExtraInfoTextView.setBackgroundColor(rndColor)
        state.current_color = rndColor

        binding.ExtraInfoTextView.text =
            getString(R.string.extra_info,
                binding.spinnerList.selectedItemPosition,
                binding.spinnerList.count)
    }


    private fun createCharacter(name: String, type: String, lift: String, axes: String) {
        val character = Character(
            id = Random.nextLong(),
            name = "Some",
            isCustom = true,
            type = type,
            liftingCapacity = 0,
            axesCount = 2
        )

        if (name.isNotBlank()) character.name = name
        if (lift.isNotBlank()) character.liftingCapacity = lift.toInt()
        if (axes.isNotBlank()) character.axesCount = axes.toInt()

        data.add(character)
        adapter.notifyDataSetChanged()
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, whitch ->
            if (whitch == DialogInterface.BUTTON_POSITIVE) {
                data.remove(character)
                adapter.notifyDataSetChanged()
            }
        }
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Delete Character")
            .setMessage("Are you sure you eant to  delete the character ${character.name}?")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }
}