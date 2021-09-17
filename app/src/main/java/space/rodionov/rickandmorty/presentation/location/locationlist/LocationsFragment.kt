package space.rodionov.rickandmorty.presentation.location.locationlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentLocationsBinding
import space.rodionov.rickandmorty.domain.model.Location

private const val TAG = "LOGS"

@AndroidEntryPoint
class LocationsFragment : Fragment(R.layout.fragment_locations) {

    private val viewModel: LocationsViewModel by viewModels()
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLocationsBinding.bind(view)

        val locationAdapter = LocationAdapter(
            onLocationClick = {
                onLocationClick(it)
            }
        )

        binding.apply {
            recyclerView.apply {
                adapter = locationAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                )
            }

            viewModel.locations.observe(viewLifecycleOwner) {
                Log.d(TAG, "list.size = ${it.size}")
                Log.d(TAG, "newpage = ${viewModel.nextPage}")
                locationAdapter.submitList(it)
                locationAdapter.notifyDataSetChanged()
                progressBar.visibility = View.INVISIBLE
            }

            nestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
                override fun onScrollChange(
                    v: NestedScrollView,
                    scrollX: Int,
                    scrollY: Int,
                    oldScrollX: Int,
                    oldScrollY: Int
                ) {
                    if (scrollY == v.getChildAt(0).measuredHeight
                    - v.measuredHeight) {
                        if (viewModel.nextPage < 6) {
                            progressBar.visibility = View.VISIBLE
                            viewModel.nextPage++
                            viewModel.getNewPage()
                        }
                    }
                }
            })

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.onRefresh()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun onLocationClick(location: Location) {
        val action = LocationsFragmentDirections.actionLocationsFragmentToLocationDetailFragment(location.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}