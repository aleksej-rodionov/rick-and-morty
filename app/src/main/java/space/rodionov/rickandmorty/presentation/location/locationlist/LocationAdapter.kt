package space.rodionov.rickandmorty.presentation.location.locationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.rickandmorty.databinding.ItemLocationBinding
import space.rodionov.rickandmorty.domain.model.Location

class LocationAdapter(
    private val onLocationClick: (Location) -> Unit
) : ListAdapter<Location, LocationAdapter.LocationViewHolder>(LocationComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(
            binding,
            onItemClick = {
                val location = getItem(it)
                if (location != null) {
                    onLocationClick(location)
                }
            }
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val curItem = getItem(position)
        if (curItem != null)           holder.bind(curItem)
    }

    inner class LocationViewHolder(
        private val binding: ItemLocationBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            binding.apply {
                tvName.text = location.name
                tvType.text = location.type
                tvDimension.text = location.dimension
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

    class LocationComparator : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Location, newItem: Location) =
            oldItem == newItem
    }
}