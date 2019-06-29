#### 属性动画，Bitmap / drawable, 硬件加速


##### android.view.ViewPropertyAnimator

```
view.animate() // ViewPropertyAnimator
	.translationX(200)
	.translationY(100)
	.rotation(180)
	.setStartDelay(1000)
	.start();
```

translate
rotate
scale
alpha

#### View
- setTranstionX

##### ObjectAnimator

`CircleView.java`

- setRadius(float r) -- 设定新值 -- `invalidate()`
- getRadius() -- 初始值


```
ObjectAnimator animator = ObjectAnimator.ofFloat(
	view,
	"radius",
	Utils.dp2px(150)
);
animator.setStartDelay(1000);
animator.start();
```

```
AnimatorSet animatorSet = new AnimatorSet();
animatorSet.playSequentially(animator1, animator2);
animatorSet.start()
```

#### PropertyValuesHolder - 两种用法:

#### 1. 多个属性同时改变做动画: `PropertyValuesHolder.ofFloat(..) ... ObjectAnimator.ofPropertyValuesHolder(..)`

```
PropertyValuesHolder rightFlipHolder = PropertyValuesHolder.ofFloat("rightFlip", -45);
PropertyValuesHolder canvasRotationHolder = PropertyValuesHolder.ofFloat("canvasRotation", 270);
PropertyValuesHolder leftFlipHolder = PropertyValuesHolder.ofFloat("leftFlip", 45);
ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, rightFlipHolder, canvasRotationHolder, leftFlipHolder);
objectAnimator.setStartDelay(1000);
objectAnimator.setDuration(2000);
objectAnimator.start();

// 这个效果等于 animatorSet.playTogether(...)
```

### 2. KeyFrame - 一个动画的多个关键帧 `PropertyValuesHolder.ofKeyFrame("transationX", keyframe1, .. keyframeX) ... ObjectAnimator.ofPropertyValuesHolder(targetView, propertyValuesHolder)`

```
Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 0.4 * max); // 时间完成度 20%, 任务完成度 40%
Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6 * max); // 时间完成度 80%, 任务完成度 60%
Keyframe keyframe4 = Keyframe.ofFloat(1, 1 * max);
PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyFrame("translationX", keyframe1, keyframe2, keyframe3, keyframe4); // 改变指定属性, 并应用关键帧确定改变速率
ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder);
animator.setStartDelay(1000);
animator.setDuration(1000);
animator.start();
```

##### ValueAnimator


#### Interpolator - 插值器
```
view.animate()
	.translationY(300)
	.setInterpolator(new DecelerateInterpolator())
	.start();
```

#### TypeEvaluator - 估值器

ObjectAnimator.ofObject(targetView, "propertyName", typeEvaluator, targetPropertyValue)

class PointEvaluator implements TypeEvaluator<Point> {
	@Override
	public Point evaluate(float fraction, Point start, Point end) {
		// (endValue - startValue) * fraction + startValue
	}
}

```
ViewPropertyAnimator
	底层用到了 ValueAnimator
	将所有修改的属性保存到了NameValuesHolder 对象当中
	执行 start 方法的时候, 执行到的代码是:
	ValueAnimator.addUpdateListener(mAnimatorEventListener);
	其中:
	mAnimatorEventListener#onAnimationUpdate
	方法会把所有修改的属性一次性修改(调用 setValue 方法)
	然后只执行一次 invalidate

view.animate()
	.translateX(100)
	.translateY(100)
	.rotate(45)
	.start();



ObjectAnimator
	继承了 ValueAnimator

	ObjectAnimator.ofFloat(view, "translationX", 100)
	ObjectAnimator.ofFloat(view, "translationY", 100)

	ObjectAnimator.ofPropertyValuesHolder(view, pvh1, pvh2)

	这些方法其实都是往 ValueAnimator 当中的 mValues 里面加了 PropertyValueHolder



ValueAnimator



PropertyValuesHolder
	
	PropertyValuesHolder.ofFloat("property", 200F)
		最终也是添加 Keyframe 保存起来

	PropertyValuesHolder.ofKeyFrame("translationX", keyframe1, keyframe2)
		最终是添加 Keyframe 

	calculateValue(float fraction) // 根据完成度返回相应值


android.animation.Keyframe
android.animation.Keyframes
```



#### Listeners

开始 / 结束

???



## 硬件加速
???

## 离屏缓冲
???
