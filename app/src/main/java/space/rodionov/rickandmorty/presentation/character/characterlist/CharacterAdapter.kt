package space.rodionov.rickandmorty.presentation.character.characterlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.ItemCharacterBinding
import space.rodionov.rickandmorty.domain.model.Character

class CharacterAdapter(
    private val context: Context,
    private val onCharacterClick: (Character) -> Unit
) : ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(CharacterComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(
            binding,
            onItemClick = {
                val character = getItem(it)
                if (character != null) {
                    onCharacterClick(character)
                }
            }
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val curItem = getItem(position)
        if (curItem != null) holder.bind(curItem)
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.apply {
                Glide.with(itemView)
                    .load(character.image)
                    .error(R.drawable.image_placeholder)
                    .into(image)

                tvName.text = character.name
                tvSpeciesAndGender.text = context.getString(R.string.species_and_gender, character.species, character.gender)
                tvLocation.text = context.getString(R.string.location, character.location.name)
                tvStatus.text = character.status
            }
        }

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
        }
    }

    class CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }
}