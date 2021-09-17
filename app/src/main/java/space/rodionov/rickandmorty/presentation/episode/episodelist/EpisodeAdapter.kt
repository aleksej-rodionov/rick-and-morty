package space.rodionov.rickandmorty.presentation.episode.episodelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.rickandmorty.databinding.ItemEpisodeBinding
import space.rodionov.rickandmorty.domain.model.Episode

class EpisodeAdapter (
    private val onEpisodeClick: (Episode) -> Unit
    ) : ListAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(EpisodeComparator())    {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
            val binding =
                ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return EpisodeViewHolder(
                binding,
                onItemClick = {
                    val episode = getItem(it)
                    if (episode != null) {
                        onEpisodeClick(episode)
                    }
                }
            )
        }

        override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
            val curItem = getItem(position)
            if (curItem != null) holder.bind(curItem)
        }

        inner class EpisodeViewHolder(
            private val binding: ItemEpisodeBinding,
            private val onItemClick: (Int) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(episode: Episode) {
                binding.apply {
                    tvName.text = episode.name
                    tvAirDate.text = episode.airDate
                    tvEpisode.text = episode.episode
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

        class EpisodeComparator : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode) =
                oldItem == newItem
        }
    }