package pxnx.scaletest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.otaliastudios.zoom.ZoomLayout
import com.richpath.RichPathView
import pxnx.scaletest.ui.theme.ScaleTestTheme

class MainActivity : ComponentActivity() {
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
                AndroidView({ ctx ->
                    ZoomLayout(ctx).apply {
                        setHasClickableChildren(true)
                        setMaxZoom(40f)

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
                    }
                }, Modifier.fillMaxSize())
            }
        }

    }
}