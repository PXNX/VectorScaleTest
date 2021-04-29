package pxnx.scaletest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.*

class ZoomView : FrameLayout {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?) : super(context!!)

    interface ZoomViewListener {
        fun onZoomStarted(zoom: Float, zoomx: Float, zoomy: Float)
        fun onZooming(zoom: Float, zoomx: Float, zoomy: Float)
        fun onZoomEnded(zoom: Float, zoomx: Float, zoomy: Float)
    }

    private var zoom = 1.0f
    private var maxZoom = 40.0f
    private var smoothZoom = 1.0f
    private var zoomX = 0f
    private var zoomY = 0f
    private var smoothZoomX = 0f
    private var smoothZoomY = 0f
    private var scrolling = false

    private var miniMapHeight = -1

    private var lastTapTime: Long = 0
    private var touchStartX = 0f
    private var touchStartY = 0f
    private var touchLastX = 0f
    private var touchLastY = 0f
    private var startd = 0f
    private var pinching = false
    private var lastd = 0f
    private var lastdx1 = 0f
    private var lastdy1 = 0f
    private var lastdx2 = 0f
    private var lastdy2 = 0f

    private val m = Matrix()
    private val p = Paint()

    private var listener: ZoomViewListener? = null
    private var ch: Bitmap? = null
    fun getMaxZoom(): Float {
        return maxZoom
    }

    fun setMaxZoom(maxZoom: Float) {
        if (maxZoom < 1.0f) {
            return
        }
        this.maxZoom = maxZoom
    }

    fun zoomTo(zoom: Float, x: Float, y: Float) {
        this.zoom = min(zoom, maxZoom)
        zoomX = x
        zoomY = y
        smoothZoomTo(this.zoom, x, y)
    }

    fun smoothZoomTo(zoom: Float, x: Float, y: Float) {
        smoothZoom = clamp(1.0f, zoom, maxZoom)
        smoothZoomX = x
        smoothZoomY = y
        if (listener != null) {
            listener!!.onZoomStarted(smoothZoom, x, y)
        }
    }

    fun setListener(listener: ZoomViewListener?) {
        this.listener = listener
    }

    val zoomFocusX: Float
        get() = zoomX * zoom
    val zoomFocusY: Float
        get() = zoomY * zoom

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.pointerCount == 1)
            processSingleTouchEvent(ev)
        if (ev.pointerCount == 2)
            processDoubleTouchEvent(ev)

        rootView.invalidate()
        invalidate()
        return true
    }

    private fun processSingleTouchEvent(ev: MotionEvent) {
        val x = ev.x
        val y = ev.y
        val w = miniMapHeight * width.toFloat() / height
        val h = miniMapHeight.toFloat()

        val lx = x - touchStartX
        val ly = y - touchStartY
        val l = hypot(lx.toDouble(), ly.toDouble()).toFloat()
        val dx = x - touchLastX
        val dy = y - touchLastY
        touchLastX = x
        touchLastY = y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStartX = x
                touchStartY = y
                touchLastX = x
                touchLastY = y
                scrolling = false
            }
            MotionEvent.ACTION_MOVE -> if (scrolling || smoothZoom > 1.0f && l > 30.0f) {
                if (!scrolling) {
                    scrolling = true
                    ev.action = MotionEvent.ACTION_CANCEL
                    super.dispatchTouchEvent(ev)
                }
                smoothZoomX -= dx / zoom
                smoothZoomY -= dy / zoom
                return
            }
            MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_UP ->
                if (l < 30.0f) {
                    if (System.currentTimeMillis() - lastTapTime < 4) { // was 400
                        if (smoothZoom == 1.0f) {
                            smoothZoomTo(maxZoom, x, y)
                        } else {
                            smoothZoomTo(1.0f, width / 2.0f, height / 2.0f)
                        }
                        lastTapTime = 0
                        ev.action = MotionEvent.ACTION_CANCEL
                        super.dispatchTouchEvent(ev)
                        return
                    }
                    lastTapTime = System.currentTimeMillis()
                    performClick()
                }
            else -> {
            }
        }
        ev.setLocation(zoomX + (x - 0.5f * width) / zoom, zoomY + (y - 0.5f * height) / zoom)
        ev.x
        ev.y
        super.dispatchTouchEvent(ev)
    }

    private fun processDoubleTouchEvent(ev: MotionEvent) {
        val x1 = ev.getX(0)
        val dx1 = x1 - lastdx1
        lastdx1 = x1
        val y1 = ev.getY(0)
        val dy1 = y1 - lastdy1
        lastdy1 = y1
        val x2 = ev.getX(1)
        val dx2 = x2 - lastdx2
        lastdx2 = x2
        val y2 = ev.getY(1)
        val dy2 = y2 - lastdy2
        lastdy2 = y2

        // pointers distance
        val d = hypot(x2 - x1.toDouble(), y2 - y1.toDouble()).toFloat()
        val dd = d - lastd
        lastd = d
        val ld = abs(d - startd)
        atan2(y2 - y1.toDouble(), x2 - x1.toDouble())
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startd = d
                pinching = false
            }
            MotionEvent.ACTION_MOVE -> if (pinching || ld > 30.0f) {
                pinching = true
                val dxk = 0.5f * (dx1 + dx2)
                val dyk = 0.5f * (dy1 + dy2)
                smoothZoomTo(
                    1.0f.coerceAtLeast(zoom * d / (d - dd)),
                    zoomX - dxk / zoom,
                    zoomY - dyk / zoom
                )
            }
            MotionEvent.ACTION_UP -> pinching = false
            else -> pinching = false
        }
        ev.action = MotionEvent.ACTION_CANCEL
        super.dispatchTouchEvent(ev)
    }

    private fun clamp(min: Float, value: Float, max: Float): Float {
        return min.coerceAtLeast(value.coerceAtMost(max))
    }

    private fun lerp(a: Float, b: Float, k: Float): Float {
        return a + (b - a) * k
    }

    private fun bias(a: Float, b: Float, k: Float): Float {
        return if (abs(b - a) >= k) a + k * sign(b - a) else b
    }

    override fun dispatchDraw(canvas: Canvas) {
        zoom = lerp(bias(zoom, smoothZoom, 0.05f), smoothZoom, 0.2f)
        smoothZoomX =
            clamp(0.5f * width / smoothZoom, smoothZoomX, width - 0.5f * width / smoothZoom)
        smoothZoomY =
            clamp(0.5f * height / smoothZoom, smoothZoomY, height - 0.5f * height / smoothZoom)
        zoomX = lerp(bias(zoomX, smoothZoomX, 0.1f), smoothZoomX, 0.35f)
        zoomY = lerp(bias(zoomY, smoothZoomY, 0.1f), smoothZoomY, 0.35f)
        if (zoom != smoothZoom && listener != null) {
            listener!!.onZooming(zoom, zoomX, zoomY)
        }
        val animating =
            abs(zoom - smoothZoom) > 0.0000001f || abs(zoomX - smoothZoomX) > 0.0000001f || abs(
                zoomY - smoothZoomY
            ) > 0.0000001f

        if (childCount == 0)
            return

        m.setTranslate(0.5f * width, 0.5f * height)
        m.preScale(zoom, zoom)
        m.preTranslate(
            -clamp(0.5f * width / zoom, zoomX, width - 0.5f * width / zoom),
            -clamp(0.5f * height / zoom, zoomY, height - 0.5f * height / zoom)
        )

        val v = getChildAt(0)
        m.preTranslate(v.left.toFloat(), v.top.toFloat())

        if (animating && ch == null && isAnimationCacheEnabled) {
            v.isDrawingCacheEnabled = true
            ch = v.drawingCache
        }

        if (animating && isAnimationCacheEnabled && ch != null) {
            p.color = -0x1
            canvas.drawBitmap(ch!!, m, p)
        } else {
            ch = null
            canvas.save()
            canvas.concat(m)
            v.draw(canvas)
            canvas.restore()
        }

        rootView.invalidate()
        invalidate()
    }
}