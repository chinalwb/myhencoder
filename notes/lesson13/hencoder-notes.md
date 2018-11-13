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
5. event.getActionIndex
6. MotionEvent.ACTION_POINTER_DOWN:
7. 
