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

#### PropertyValuesHolder - 多个属性同时改变做动画

```
PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat
```

### KeyFrame - 一个动画的多个关键帧

```
PropertyValuesHolder.ofKeyFrame("translationX", keyFrame1, keyFrame2, kf3, kf4)
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

ObjectAnimator.ofObject

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