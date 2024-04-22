package com.example.firealarmmobapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.SimpleExoPlayer
import com.example.firealarmmobapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val liveStreamUrl = "http://192.168.1.153/cam-lo.jpg" // Update with your live stream URL
    private var exoPlayer: SimpleExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadLiveStream()
    }

    @OptIn(UnstableApi::class) private fun loadLiveStream() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext())
        binding.textureView.player = exoPlayer

        val userAgent = Util.getUserAgent(requireContext(), "Yasds")
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), userAgent)
        val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(liveStreamUrl))

        exoPlayer?.prepare(videoSource)
        exoPlayer?.playWhenReady = true
    }

    @OptIn(UnstableApi::class) override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
    }
}
