package pxnx.scaletest

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.scaleMatrix
import com.richpath.RichPathView
import pxnx.scaletest.ui.theme.ScaleTestTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaleTestTheme {

                var angle by remember { mutableStateOf(0f) }
                var zoom by remember { mutableStateOf(1f) }
                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }

                AndroidView(
                    factory = { context ->
                        RichPathView(context).apply {


// hängt
// scaleType=ImageView.ScaleType.FIT_XY

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


                        }
                    },

                    Modifier
                        .offset {
                            IntOffset(
                                offsetX.roundToInt(),
                                offsetY.roundToInt()
                            )
                        }

                     /*   .graphicsLayer {
                            /*   translationX = 0.4f
                           translationY = 0.4f
                           rotationY = 53f
                           rotationX = 44f

                         */
                            scaleX = zoom
                            scaleY = zoom
                        }

                      */
                        .pointerInput(Unit) {
                            detectTransformGestures(
                                onGesture = { _, pan, gestureZoom, gestureRotate ->
                                    angle += gestureRotate
                                    zoom *= gestureZoom
                                    val x = pan.x * zoom
                                    val y = pan.y * zoom
                                    val angleRad = angle * PI / 180.0
                                    offsetX += (x * cos(angleRad) - y * sin(angleRad)).toFloat()
                                    offsetY += (x * sin(angleRad) + y * cos(angleRad)).toFloat()
                                }
                            )
                        }
                        .fillMaxSize(),
                    update = {
                        it.scaleX = zoom
                        it.scaleY=zoom
                    }
                )


            }
        }
    }
}
