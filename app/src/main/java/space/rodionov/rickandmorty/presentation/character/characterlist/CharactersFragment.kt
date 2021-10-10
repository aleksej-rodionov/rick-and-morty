package space.rodionov.rickandmorty.presentation.character.characterlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentCharactersBinding
import space.rodionov.rickandmorty.domain.model.Character
import space.rodionov.rickandmorty.presentation.MainActivity
import javax.inject.Inject

private const val TAG = "FRAGMENT LOGS"

class CharactersFragment : Fragment(R.layout.fragment_characters) {

//    @Inject
//    lateinit var storage: SharedPrefStorageImpl

    //    private val viewModel: CharactersViewModel by viewModels()
//    private val viewModel by lazy {
//        ViewModelProvider(this).get(CharactersViewModel::class.java)
//    }
    @Inject
    lateinit var assistedFactory: CharactersViewModelAssistedFactory
    private val viewModel: CharactersViewModel by viewModels { assistedFactory.create(this/*, storage*/) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharactersBinding.bind(view)

        viewModel.setSSHMode(viewModel.getMode())

        viewModel.isNight.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: mode = $it")
        }

        val charactersAdapter = CharacterAdapter(
            requireContext(),
            onCharacterClick = {
                onCharacterClick(it)
            }
        )

        binding.apply {
            recyclerView.apply {
                adapter = charactersAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }

            viewModel.characters.observe(viewLifecycleOwner) {

                charactersAdapter.submitList(it)
                charactersAdapter.notifyDataSetChanged()
                progressBar.visibility = View.INVISIBLE
            }

            nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == v.getChildAt(0).measuredHeight
                    - v.measuredHeight
                ) {
                    if (viewModel.nextPage < 34) {
                        progressBar.visibility = View.VISIBLE
                        viewModel.nextPage++
                        viewModel.getNewPage(/*viewLifecycleOwner*/)
                    }
                }
            })

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.onRefresh(/*viewLifecycleOwner*/)
                swipeRefreshLayout.isRefreshing = false
            }
        }

        setHasOptionsMenu(true)
    }

    private fun onCharacterClick(character: Character) {
        val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
            character.id
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_mode -> {
                val night = viewModel.isNight.value ?: false
                viewModel.saveMode(!night)
                viewModel.setSSHMode(viewModel.getMode())

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}