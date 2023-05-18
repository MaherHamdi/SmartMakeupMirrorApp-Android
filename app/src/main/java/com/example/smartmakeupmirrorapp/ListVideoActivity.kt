package com.example.smartmakeupmirrorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartmakeupmirrorapp.Adapter.VideoAdapter
import com.example.smartmakeupmirrorapp.Models.Video

class ListVideoActivity : AppCompatActivity() {
    private lateinit var videos: ArrayList<Video>
    private lateinit var recyclerViewVideo: RecyclerView
    private lateinit var videoAdapter : VideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_video)
        recyclerViewVideo=findViewById(R.id.list_video)
        recyclerViewVideo.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        videos= ArrayList()
        videos.add(Video("1","${R.raw.video1}"))
        videos.add(Video("2","${R.raw.video2}"))
        videos.add(Video("3","${R.raw.video3}"))
        videos.add(Video("4","${R.raw.video4}"))
        videos.add(Video("5","${R.raw.video5}"))
        videos.add(Video("6","${R.raw.video6}"))


        videoAdapter = VideoAdapter(videos)
        recyclerViewVideo.adapter =videoAdapter

    }
}