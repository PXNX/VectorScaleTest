package pxnx.scaletest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.viewinterop.AndroidView
import com.jsibbold.zoomage.ZoomageView
import com.richpath.RichPathView
import pxnx.scaletest.ui.theme.ScaleTestTheme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaleTestTheme {

/*
//not pixelated, touchevents arrive elsewhere
                AndroidView({ ctx ->
                    ZoomLayout(ctx).apply {
                        setHasClickableChildren(true)
                        setMaxZoom(40f)

                        addView(ZoomView(ctx).apply {
                            addView(RichPathView(ctx)
                                .apply {
                                    setVectorDrawable(R.drawable.map_test_shape)
                                    setOnPathClickListener { path ->
                                        Log.w("MAIN", "path: $path - name: ${path.name}")
                                        path.strokeWidth = 60f

                                        if (path.strokeColor == ContextCompat.getColor(
                                                this@MainActivity,
                                                R.color.teal_200
                                            )
                                        )
                                            path.strokeColor = ContextCompat.getColor(
                                                this@MainActivity,
                                                R.color.Primary
                                            )
                                        else
                                            path.strokeColor = ContextCompat.getColor(
                                                this@MainActivity,
                                                R.color.teal_200
                                            )
                                    }
                                })
                        })
                    }
                }, Modifier.fillMaxSize())
 */

// pixelated when zoomed in, touchevents where expected

                Log.w("NNNN", "-")

                //   val vec = animatedVectorResource(R.drawable.map_test_shape).imageVector

                Text("Yee", Modifier.background(Blue), color = Color.White)



                AndroidView({ ctx ->

                    ZoomageView(ctx).apply {

                          setImageResource(R.drawable.map_test_shape)

                     /*   setContentView(
                            RichPathView(ctx).apply {
                            //    setVectorDrawable(R.drawable.map_test_shape)
                                /*   setOnPathClickListener { path ->
                                       Log.w("MAIN", "path: $path - name: ${path.name}")
                                       path.strokeWidth = 60f

                                       if (path.strokeColor == ContextCompat.getColor(
                                               this@MainActivity,
                                               R.color.teal_200
                                           )
                                       )
                                           path.strokeColor = ContextCompat.getColor(
                                               this@MainActivity,
                                               R.color.Primary
                                           )
                                       else
                                           path.strokeColor = ContextCompat.getColor(
                                               this@MainActivity,
                                               R.color.teal_200
                                           )
                                   }

                                 */
                            })

                      */

                        /*      ZoomView(ctx).apply {

                                  addView(ImageView(ctx).apply {
                                      setImageResource(R.drawable.map_test_shape)
                                      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

                                  })


                                  layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

                         */


                        /*      SubsamplingScaleImageView(ctx).apply {
                               /*   setHasClickableChildren(true)
                                  setMaxZoom(40f, TYPE_REAL_ZOOM)
                                  setTransformation(TRANSFORMATION_NONE)



                                  addView(ImageView(ctx).apply {
                                      this.setImageResource(R.drawable.map_test_shape)

                                  })
                                     */

                                 setImage(ImageSource.resource(R.drawable.ic_launcher_foreground))


                         */


                        /*       AndroidView({ ctx ->
                                   ZoomLayout(ctx).apply {
                                       setHasClickableChildren(true)
                                       setMaxZoom(40f, TYPE_REAL_ZOOM)
                                       setTransformation(TRANSFORMATION_NONE)

                                       addView(ImageView(ctx).apply {
                                           this.setImageResource(R.drawable.map_test_shape)

                                       })

                */

                        /*     addView(RichPathView(ctx)
                                 .apply {
                                     setVectorDrawable(R.drawable.map_test_shape)
                                     setOnPathClickListener { path ->
                                         Log.w("MAIN", "path: $path - name: ${path.name}")
                                         path.strokeWidth = 60f

                                         if (path.strokeColor == ContextCompat.getColor(
                                                 this@MainActivity,
                                                 R.color.teal_200
                                             )
                                         )
                                             path.strokeColor = ContextCompat.getColor(
                                                 this@MainActivity,
                                                 R.color.Primary
                                             )
                                         else
                                             path.strokeColor = ContextCompat.getColor(
                                                 this@MainActivity,
                                                 R.color.teal_200
                                             )
                                     }
                                 })

                         */
                    }
                }, Modifier.fillMaxSize())


            }
        }

    }
}