package com.example.video_app.presentation.video_screen

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.video_app.databinding.VideoScreenBinding
import com.example.video_app.domain.entity.VideoItem

class VideoFragment : Fragment() {
    private var _binding: VideoScreenBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var openedVideo: VideoItem

    companion object {
        private const val BUNDLE_KEY = "CURRENT_VIDEO"
        private const val KEY_ERROR_MESSAGE = "UNKNOWN KEY"

        @JvmStatic
        fun newInstance(video: VideoItem): VideoFragment {
            return VideoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_KEY, video)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("DEPRECATION")
            openedVideo = it.getParcelable(BUNDLE_KEY) ?:
                throw IllegalStateException(KEY_ERROR_MESSAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = VideoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvVideoName?.text = openedVideo.name
            tvDescription?.text = openedVideo.description
            playerView.player = this@VideoFragment.context?.let {
                ExoPlayer.Builder(it).build()
            }

            playerView.player?.setMediaItem(MediaItem.fromUri(Uri.parse(openedVideo.mp4Url)))
        }
    }

    override fun onStop() {
        super.onStop()
        binding.playerView.player?.release()
    }

    override fun onResume() {
        super.onResume()
        binding.playerView.player?.playWhenReady = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}