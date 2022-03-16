package binar.academy.myfileprocessingpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import binar.academy.myfileprocessingpractice.databinding.ActivityVideoHandleBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

class VideoHandleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoHandleBinding

    private var mPlayer: ExoPlayer? = null

    private val videoURL =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initExoPlayer()
    }

    private fun initExoPlayer() {
        // Create a player instance.
        mPlayer = ExoPlayer.Builder(this).build()

        // Bind the player to the view.
        binding.playerView.player = mPlayer

        //setting exoplayer when it is ready.
        mPlayer!!.playWhenReady = true

        // Set the media source to be played.
        mPlayer!!.setMediaSource(buildMediaSource())

        // Prepare the player.
        mPlayer!!.prepare()
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initExoPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initExoPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        //release player when done
        mPlayer!!.release()
        mPlayer = null
    }

    //creating mediaSource
    private fun buildMediaSource(): MediaSource {
        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        // Create a progressive media source pointing to a stream uri.
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL))

        return mediaSource
    }
}