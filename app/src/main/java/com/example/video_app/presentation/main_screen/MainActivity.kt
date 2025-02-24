package com.example.video_app.presentation.main_screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.video_app.App
import com.example.video_app.databinding.MainScreenBinding
import com.example.video_app.domain.entity.VideoItem
import com.example.video_app.presentation.main_screen.recycler.ScrollListener
import com.example.video_app.presentation.main_screen.recycler.VideoItemAdapter
import com.example.video_app.presentation.main_screen.screenstate.ErrorVisibility
import com.example.video_app.presentation.main_screen.viewmodel.MainScreenViewModel
import com.example.video_app.presentation.main_screen.viewmodel.MainScreenViewModelFactory
import com.example.video_app.presentation.main_screen.screenstate.ScreenState
import com.example.video_app.presentation.main_screen.screenstate.UploadModes
import com.example.video_app.presentation.video_screen.VideoFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var _binding: MainScreenBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var adapter: VideoItemAdapter
    @Inject lateinit var factory: MainScreenViewModelFactory
    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this, factory)[MainScreenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as App).appComponent.inject(this)
        configUI() // создаём и настраиваем все view компоненты
        startEventHandling() // запускаем обработку состояний экрана
    }

    // Начальная настройка компонентов
    private fun configUI() {
        adapter = VideoItemAdapter { video ->
            supportFragmentManager.commit {
                addToBackStack(null)
                add(binding.root.id, VideoFragment.newInstance(video))
            }
        }

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener (ScrollListener {
                // догружаем видео в процессе скроллинга ленты
                viewModel.uploadNewVideos()
            })
            recyclerView.itemAnimator = null
            swipeRefreshLayout.setOnRefreshListener {
                // полностью очищаем всю ленту
                adapter.clearContent()
                // загружаем видео заново
                viewModel.provideVideos()
                swipeRefreshLayout.isRefreshing = false
            }
            fabClear.setOnClickListener {
                viewModel.clearCache()
                // выводим пустой экран
                showEmptyScreen()
            }
        }
    }

    // Начинает процесс обработки состояний экрана
    private fun startEventHandling() = lifecycleScope.launch {
        viewModel.screenState.collect { currentState ->
            setStateOfUI(currentState)
        }
    }

    // Изменяет UI в зависимости от полученного состояния
    private fun setStateOfUI(state: ScreenState) {
        when(state) {
            is ScreenState.Success -> showNewVideos(state.videos, state.addMode)
            is ScreenState.Error -> showError(state.errorMessage, state.isShown)
            is ScreenState.Loading -> showLoading()
            is ScreenState.Empty -> showEmptyScreen()
            is ScreenState.Init -> viewModel.provideVideos()
        }
    }

    // Отображает сообщения пустого экрана
    private fun showEmptyScreen() {
        stopLoading()
        if (binding.tvEmptyScreen.visibility == View.INVISIBLE) {
            binding.tvEmptyScreen.visibility = View.VISIBLE
        }
    }

    // Скрывает сообщение пустого экрана
    private fun hideEmptyScreenMessage() {
        if (binding.tvEmptyScreen.visibility == View.VISIBLE) {
            binding.tvEmptyScreen.visibility = View.INVISIBLE
        }
    }

    // Останавливает отображение процесса загрузки
    private fun stopLoading() {
        if (binding.pgLoading.visibility == View.VISIBLE) {
            binding.pgLoading.visibility = View.GONE
        }
    }

    // Отображает процесс загрузки
    private fun showLoading() {
        if (binding.pgLoading.visibility == View.GONE) {
            binding.pgLoading.visibility = View.VISIBLE
        }
    }

    // Отображает сообщение об ошибке
    private fun showError(errorMessage: String, mode: ErrorVisibility) {
        if (mode == ErrorVisibility.SHOW) {
            stopLoading()
            hideEmptyScreenMessage()
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    // отображает загруженные видео
    private fun showNewVideos(videos: List<VideoItem>, addMode: UploadModes) {
        stopLoading()
        hideEmptyScreenMessage()
        if (videos.isEmpty() && addMode == UploadModes.RESET) {
            adapter.setVideos(emptyList())
            showEmptyScreen()
        } else {
            adapter.addVideos(videos)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}