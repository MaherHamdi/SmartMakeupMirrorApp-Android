package com.example.smartmakeupmirrorapp.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.smartmakeupmirrorapp.BuildConfig
import com.example.smartmakeupmirrorapp.R
import com.example.smartmakeupmirrorapp.Models.Product
import com.example.smartmakeupmirrorapp.Models.Video


class VideoAdapter(private val videos: List<Video>): RecyclerView.Adapter<VideoAdapter.ViewHolder>()  {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private  val videoView: VideoView =itemView.findViewById(R.id.videoView)



        fun bind(video: Video) {
            val videoUri = Uri.parse("android.resource://${BuildConfig.APPLICATION_ID}/${video.video_url}")
            videoView.setVideoURI(videoUri)


            val mediaController = MediaController(itemView.context)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setOnPreparedListener { mp ->
                mp.setOnVideoSizeChangedListener { _, _, _ ->
                    videoView.start()
                }
            }
            videoView.requestFocus()
            videoView.start()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_video, parent, false)
        return VideoAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)

        /*      holder.itemView.setOnClickListener {
                  onItemClick?.invoke(product)
              }*/
    }

    override fun getItemCount(): Int {
        return videos.size
    }


}