package tn.farah.smartmakeupapp.data.models

import com.squareup.moshi.Json

class Video(@Json(name="_id")val _id:String,
            @Json(name="video_url")val video_url:String) {
}