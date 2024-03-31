package com.example.transport_programm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.transport_programm.databinding.ItemCharacterLineBinding

typealias OnDeletePressedListener = (Character) -> Unit

class CharacterAdapter(
    private val characters: List<Character>,
    private val onDeletePressedListener: OnDeletePressedListener
): BaseAdapter(), View.OnClickListener {
    override fun getCount(): Int {
        return characters.size
    }

    override fun getItem(position: Int): Character {
        return characters[position]
    }

    override fun getItemId(position: Int): Long {
        return characters[position].id
    }

    private fun getDefaultView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?,
        isDropdownView: Boolean
    ): View {
        val binding =
            convertView?.tag as ItemCharacterLineBinding? ?: createBinding(parent!!.context)
        val character = getItem(position)

        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.deleteImageView.visibility = if (isDropdownView) View.VISIBLE else View.GONE

        return binding.root
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getDefaultView(position, convertView, parent, isDropdownView = false)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getDefaultView(position, convertView, parent, isDropdownView = true)
    }

    override fun onClick(view: View?) {
        val character = view!!.tag as Character
        onDeletePressedListener.invoke(character)
    }

    private fun createBinding(context: Context): ItemCharacterLineBinding {
        val binding = ItemCharacterLineBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding
        return binding
    }
}