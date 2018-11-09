#### ScalableImageView

* 双击 - GestureDetectorCompt (androidx.core.view.GestureDetectorCompat)
* new GestureDetectorCompat(context, OnGestureListener)
*  - GestureDetectorCompat 是手势侦测
*  - OnGestureListener 是回调方法
*  - onDown -- ACTION_DOWN 事件序列的第一个事件
*  - onShowPress -- PRE_PRESS 等待100ms之后的回调
*  - onSingleTapUp -- 单击事件，长按事件不会触发这个 （gesture.setLongPressEnabled(false) 可以影响这个方法的回调 ）
*   - onScroll
*   - onLongPress
*   - onFling


*   - gesture.onDoubleTapListener() -- 
*   -- GestureDetector.SimpleOnGestureListener()
*   - onSingleTapConfirmed -- 确认单击需要被执行
*   - onSingleTapUp -- 每次抬起都会触发
*   - onDoubleTap -- 两次触摸间隔300ms之内
*   - onDoubleTapEvent -- 双击之后手指不抬起继续操作会持续触发这个回调方法

* onTouchEvent - GestureDetector.onTouchEvent(MotionEvent)


```
getAnimator().
```