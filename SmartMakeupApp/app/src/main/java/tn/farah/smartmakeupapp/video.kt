package tn.farah.smartmakeupapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class video : AppCompatActivity() {
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.videoView)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.video1}")
        videoView.setVideoURI(videoUri)

       // val videoUrl = "android.resource://" // Remplacez l'URL par l'URL de votre vidéo TikTok

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

      //  videoView.setVideoURI(Uri.parse("android.resource://"+R.raw.video1))
        videoView.requestFocus()
        videoView.start()
    }
}