#### 多点触控

1. 图片由大变小时，图片的位置不居中问题的解决，-- 是用fraction来解决
2. 图片由小变大时，放大的图像保持点击的位置，需要在 onDoubleTap 的方法中计算出正确的offset
	```
	(e.getX() - getWidth() / 2) - (e.getX() - getWidth() / 2 * bigScale / smallScale)
	```
	??
3. 双指捏撑 - ScaleGestureDetector # OnScaleGestureListener
	- 放缩倍数 - ScaleGestureDector.getScaleFactor()
	- 焦点 - 
4. onTouchEvent -> scaleGestureDetector.onTouchEvent(event)


#### 随手指移动的图片

接管型：

1. MultiTouchView1
2. 多个手指触控，在加入和减少某个手指的时候：
	index 会改变
	id 不变
3. event.getX() == event.getX(0)
4. onTouchEvent里面的event都是针对View的，不是针对手指的
5. event.getActionIndex() — 正在发生事件的 pointer index
6. event.getPointerId(actionIndex) —  根据 action index 得到 pointer ID
7. MotionEvent.ACTION_POINTER_DOWN:







- ViewConfiguration(context)
- viewConfiguration.getScaledPagingTouchSlop() — 翻页操作的临界值
- viewConfiguration.getScaledMinimumFlingVelocity() — 得到最小的快滑速度
- VelocityTracker.obtain() — 初始化对象
- velocityTracker.addMovement(MotionEvent) — 每次有事件发生都要 addMovement
- velocityTracker.clear() 
- velocity.computeCurrentVelocity(1000, maxVelocity) — 计算速度
- velocityTracker.getXVelocity() — 得到横向速度
- getParent().requestDisallowInterceptTuochEvent(true) — 不让父 view 拦截

- view.getScrollX() — 获得 view 在x 轴方向的偏移量



- overScroller.startScroll(startX, startY, scrollDistance, 0) // ???
- postInvalidateOnAnimation()
- computeScroll() 方法会在 draw 方法中被调用, 并且是在 onDraw 方法之前被调用

