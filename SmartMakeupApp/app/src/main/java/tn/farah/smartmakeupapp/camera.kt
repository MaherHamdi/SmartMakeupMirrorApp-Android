package tn.farah.smartmakeupapp
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.hardware.camera2.CameraManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedImage
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import retrofit2.Call
import retrofit2.Callback
import android.opengl.GLES11Ext
import androidx.core.content.ContextCompat
import com.google.ar.core.ArCoreApk.Availability
import com.google.ar.core.ArCoreApk.InstallStatus
import com.google.ar.core.AugmentedFace
import com.google.ar.core.CameraConfig
import com.google.ar.core.CameraConfigFilter
import com.google.ar.core.TrackingState
import retrofit2.Response
import tn.farah.smartmakeupapp.Adapter.ProductAdapter
import tn.farah.smartmakeupapp.Adapter.ProductCamAdapter
import tn.farah.smartmakeupapp.data.models.NewProducts
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo
import java.util.EnumSet

class Camera : AppCompatActivity(), SurfaceHolder.Callback  {

    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var surfaceView: SurfaceView
    private lateinit var arSession: Session
    private lateinit var surfaceTexture: SurfaceTexture

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductCamAdapter
    private lateinit var cameraManager: CameraManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

   ///////////////////////////



        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        surfaceView = findViewById(R.id.surfaceView)
        surfaceTexture = SurfaceTexture(0)

        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)



        recyclerView = findViewById(R.id.recyclerViewCam)



        val TAG1 = "productList"
      //  val newProducts = NewProducts(true)
        ProductRepo.apiService.getProducts()
            .enqueue(object : Callback<List<Product>> {


                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    if (response.isSuccessful) {
                        recyclerView?.layoutManager =
                            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

                        val newProductsList = response.body()
                        newProductsList?.let {
                            adapter = ProductCamAdapter(newProductsList)
                            recyclerView.adapter = adapter

                            adapter.onItemClick = {
                                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            //    val intent =
                                //    Intent(applicationContext, product_detail::class.java)
                            //    intent.putExtra("product", it)
                                startActivity(intent)
                            }
                        }
                        Log.e(TAG1, "Response  successful. : ${newProductsList}")

                    } else {
                        Log.e(TAG1, "Response not successful. Status code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.e(TAG1, "Network request failed", t)
                }
            })







    }
    /*override fun surfaceCreated(holder: SurfaceHolder) {



        val TAG1 = "productList"
        try {
            // Vérification de la disponibilité de ARCore
            val availability = ArCoreApk.getInstance().checkAvailability(this)
            if (availability.isTransient) {
                // Si la disponibilité est transitoire, attendez qu'elle soit définitive.
                // Réessayer après 2s ou lorsque onResume() est appelé.
                Handler().postDelayed({ recreate() }, 2000)
            }


            if (availability.isSupported) {
                // Créer une session ARCore
                arSession = Session(this)
                // Configurer la session ARCore pour détecter les images augmentées
                val config = Config(arSession)
                config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D

                //config.augmentedImageDatabase = buildAugmentedImageDatabase(arSession)
                arSession.configure(config)
                arSession.resume()
            } else {
                Toast.makeText(this, "ARCore is not available", Toast.LENGTH_LONG).show()
                finish()
                return
            }
            // Set a camera configuration that usese the front-facing camera.
            val filter = CameraConfigFilter(arSession).setFacingDirection(CameraConfig.FacingDirection.FRONT)
            val cameraConfig = arSession.getSupportedCameraConfigs(filter)[0]
            arSession.cameraConfig = cameraConfig
            val faces = arSession.getAllTrackables(AugmentedFace::class.java)
            faces.forEach { face ->
                if (face.trackingState == TrackingState.TRACKING) {
                    // UVs and indices can be cached as they do not change during the session.
                    val uvs = face.meshTextureCoordinates
                    val indices = face.meshTriangleIndices
                    // Center and region poses, mesh vertices, and normals are updated each frame.
                    val facePose = face.centerPose
                    val faceVertices = face.meshVertices
                    val faceNormals = face.meshNormals
                    // Render the face using these values with OpenGL.
                }
            }
            //  val config = Config(arSession)
          //  config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
           // arSession.configure(config)
            // Ouvrir la caméra et attacher la surfaceView comme surface d'aperçu
       /*     val camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
            val parameters = camera.parameters
            parameters.set("orientation", "landscape")
            camera.setDisplayOrientation(90)
            camera.parameters = parameters
            camera.setPreviewDisplay(holder)
            camera.startPreview()*/
        } catch (e: Exception) {
            Log.e(TAG1,"Failed to start camera preview", e)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Redimensionner la surface d'aperçu si nécessaire
        if (holder.surface == null) {
            // La surface n'est pas encore prête pour la prévisualisation, on attend
            return
        }

        // Stopper la prévisualisation avant de redimensionner la surface
        arSession.pause()

        // Configurer la caméra ARCore avec l'ID de texture de la surface de prévisualisation
      //  arSession.setCameraTextureName(holder.surfaceTexture.textureId)
      //  arSession.setCameraTextureName(0)

        // Redémarrer la prévisualisation
        arSession.resume()
    }


    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Arrêter la caméra
        arSession.pause()
        arSession.close()
    }*/

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Créer une session ARCore
        arSession = Session(this)
        // Configurer la session ARCore pour détecter les images augmentées

        // Set a camera configuration that usese the front-facing camera.
        val filter = CameraConfigFilter(arSession).setFacingDirection(CameraConfig.FacingDirection.FRONT)
        // Return only camera configs that target 30 FPS camera capture frame rate.
        filter.targetFps = EnumSet.of(CameraConfig.TargetFps.TARGET_FPS_30)
        // Return only camera configs that will not use the depth sensor.
        filter.depthSensorUsage = EnumSet.of(CameraConfig.DepthSensorUsage.DO_NOT_USE)
        val cameraConfig = arSession.getSupportedCameraConfigs(filter)[0]
        arSession.cameraConfig = cameraConfig

        val config = Config(arSession)
        //config.setDepthMode(Config.DepthMode.AUTOMATIC)
        config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
      //
        arSession.configure(config)
        // ARCore's face detection works best on upright faces, relative to gravity.
        val faces = arSession.getAllTrackables(AugmentedFace::class.java)
        faces.forEach { face ->
            if (face.trackingState == TrackingState.TRACKING) {
                // UVs and indices can be cached as they do not change during the session.
                val uvs = face.meshTextureCoordinates
                val indices = face.meshTriangleIndices
                // Center and region poses, mesh vertices, and normals are updated each frame.
                val facePose = face.centerPose
                val faceVertices = face.meshVertices
                val faceNormals = face.meshNormals
                // Render the face using these values with OpenGL.
            }

/*
           // Ouvrir la caméra et attacher la surfaceView comme surface d'aperçu
           camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
           val parameters = camera.parameters
           parameters.set("orientation", "landscape")
           camera.setDisplayOrientation(90)
           camera.parameters = parameters
           camera.setPreviewDisplay(holder)
           camera.startPreview()*/

        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Redimensionner la surface d'aperçu si nécessaire
        if (surfaceHolder.surface == null) {
            // La surface n'est pas encore prête pour la prévisualisation, on attend
            return
        }

        // Stopper la prévisualisation avant de redimensionner la surface
     /*   camera.stopPreview()

        // Redimensionner la surface d'aperçu
        val parameters = camera.parameters
        val previewSize = getOptimalPreviewSize(parameters.supportedPreviewSizes, width, height)
        parameters.setPreviewSize(previewSize.width, previewSize.height)
        camera.parameters = parameters

        // Redémarrer la prévisualisation avec les nouvelles dimensions
        camera.setPreviewDisplay(surfaceHolder)
        camera.startPreview()*/
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        // Libérer la caméra et la surface d'aperçu
      //  camera.stopPreview()
        //camera.release()
        }


    private fun getOptimalPreviewSize(sizes: List<Camera.Size>, width: Int, height: Int): Camera.Size {
        var optimalSize: Camera.Size? = null
        var minDiff = Double.MAX_VALUE

        for (size in sizes) {
            val ratio = size.width.toDouble() / size.height
            val diff = Math.abs(ratio - width.toDouble() / height)
            if (diff < minDiff) {
                optimalSize = size
                minDiff = diff
            }
        }

        return optimalSize!!
    }




    private fun buildAugmentedImageDatabase(session: Session): AugmentedImageDatabase {
        val augmentedImages = mutableListOf<AugmentedImage>()
        val augmentedImageDatabase = AugmentedImageDatabase(session)

        // Ajouter l'image augmentée à la liste et à la base de données d'images augmentées
        val augmentedImageName = "lipstick"
       //ugmentedImages.add(AugmentedImage.builder().setName(augmentedImageName).setBitmap(augmentedImageBitmap).build())
       //ugmentedImageDatabase.addImage(augmentedImageName, augmentedImageBitmap)

        // Ajouter d'autres images augmentées à la liste et à la base de données d'images augmentées, si nécessaire
        // ...

        // Configurer la base de données d'images augmentées pour la session AR
        val config = session.config
        config.augmentedImageDatabase = augmentedImageDatabase
        session.configure(config)

        return augmentedImageDatabase    }

//////////////////////////////////////////////////////////////////////////////
// Determine ARCore installation status.
// Requests an ARCore installation or updates ARCore if needed.
fun isARCoreSupportedAndUpToDate(): Boolean {
    when (ArCoreApk.getInstance().checkAvailability(this)) {
        Availability.SUPPORTED_INSTALLED -> return true

        Availability.SUPPORTED_APK_TOO_OLD,
        Availability.SUPPORTED_NOT_INSTALLED -> {
            when(ArCoreApk.getInstance().requestInstall(this, true)) {
                InstallStatus.INSTALLED -> return true
                else -> return false
            }
        }

        else -> {
            // Handle the error. For example, show the user a snackbar that tells them
            // ARCore is not supported on their device.
            return false
        }
    }
}


}