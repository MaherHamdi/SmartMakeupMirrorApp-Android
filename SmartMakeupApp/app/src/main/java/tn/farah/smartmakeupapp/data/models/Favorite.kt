package tn.farah.smartmakeupapp.data.models

import com.squareup.moshi.Json

class Favorite(@Json(name="_id")val _id:String,
               @Json(name="poduct")val poduct:String,
               @Json(name="user")val user:String,
               @Json(name="ischecked")val ischecked:Boolean) {
}