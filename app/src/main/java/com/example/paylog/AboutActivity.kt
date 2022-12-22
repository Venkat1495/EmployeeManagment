package com.example.paylog

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class AboutActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        this.videoView=this.findViewById(R.id.videoView)

        val mediaController=MediaController(this)
        mediaController.setAnchorView(videoView)

        val offlineUri= Uri.parse("android.resource://$packageName/${R.raw.paylogsquare}")

//        videoView.setMediaController(mediaController)
        videoView.setVideoURI(offlineUri)
        videoView.requestFocus()

        videoView.setOnCompletionListener {
            it.start()
        }

        videoView.start()
    }
}