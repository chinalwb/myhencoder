#### 属性动画，Bitmap / drawable, 硬件加速


##### ViewPropertyAnimator

```
view.animate()
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



#### Listeners

开始 / 结束

???



## 硬件加速
???

## 离屏缓冲
???