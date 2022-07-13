package com.zionhuang.music.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zionhuang.innertube.YouTube.EXPLORE_BROWSE_ID
import com.zionhuang.innertube.models.BrowseEndpoint
import com.zionhuang.music.R
import com.zionhuang.music.databinding.LayoutRecyclerviewBinding
import com.zionhuang.music.ui.adapters.YouTubeItemPagingAdapter
import com.zionhuang.music.ui.fragments.base.PagingRecyclerViewFragment
import com.zionhuang.music.utils.NavigationEndpointHandler
import com.zionhuang.music.viewmodels.YouTubeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExploreFragment : PagingRecyclerViewFragment<YouTubeItemPagingAdapter>() {
    override fun getViewBinding() = LayoutRecyclerviewBinding.inflate(layoutInflater)
    override fun getToolbar(): Toolbar = binding.toolbar

    private val youTubeViewModel by activityViewModels<YouTubeViewModel>()
    override val adapter = YouTubeItemPagingAdapter(NavigationEndpointHandler(this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            youTubeViewModel.browse(BrowseEndpoint(EXPLORE_BROWSE_ID)).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_and_settings, menu)
        menu.findItem(R.id.action_search).actionView = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> findNavController().navigate(R.id.action_exploreFragment_to_searchSuggestionFragment)
            R.id.action_settings -> findNavController().navigate(R.id.settingsActivity)
        }
        return true
    }
}