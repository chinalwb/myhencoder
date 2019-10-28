### 为什么override equals方法就必须要override hashCode方法？

首先，override Object类的equals方法要这样写：

@Override
public boolean equals(Object o) { // 注意这个地方，方法的参数是  Object 不能是 XXClass. 如果写成XXClass 就不能覆盖Object类的equals方法了
  if (o instanceof XXClass) {
     // 具体比较逻辑在这儿写
  }
  return false;
}


那么为什么override equals就必须要override hashCode呢？

我先说结论，然后再分析为什么。
你可以选择不override hashCode方法，只要你能保证你以及所有使用你写的这个类的人不用这个类的对象做任何与Hash有关系的操作就可以。

我再说下原因：
假设我们有一个Person类
简单起见：Person类中只有一个name属性
且我们认为只要name相等，这两个对象就互相equals
那么，我就override了equals方法：

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            return this.name.equals(((Person) o).name);
        }
        return false;
    }

OK. 注意此时我们并没有override hashCode方法。
我们来结合HashMap使用一下：
```
        Person a1 = new Person("Tom");
        HashMap<Person, Integer> map = new HashMap<Person, Integer>();
        map.put(a1, 100);
        Integer a2Int = map.get(new Person("Tom"));
        System.out.println("a2Int == " + a2Int);
```
猜测一下，这个结果输出什么？

他输出了 a2Int == null.

或许你不奇怪，你会说因为你 new 了一个新的对象，而新对象的hash值跟a1对象的hash值是不一样的。
所以从map里get会返回null。

是的，确实是这样。
那我们再来看另外一个例子。

```
       Integer int1 = new Integer(1);
        HashMap<Integer, String> map2 = new HashMap<Integer, String>();
        map2.put(int1, "Hello");
        String v = map2.get(new Integer(1));
```
跟上一个例子不同，在这个例子中，我们使用了Integer作为HashMap的key，上个例子我们是使用的我们自定义的Person类做为key。
这个代码，你认为 v 会返回什么？
null吗？因为new的Integer对象跟int1不是同一个对象？
Hello吗？因为new的Integer对象的hash值跟int1是相等的？

结果是 v 的值是 Hello
原因是： new的Integer对象的hash值跟int1的hash值是相等的。

那么此时你一定推测出来了Integer.java一定Override了 Object的hashCode方法。
我们去看下：
```
    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
    public static int hashCode(int value) {
        return value;
    }
```
顺便贴出来Integer.java的equals方法：
```
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            return value == ((Integer)obj).intValue();
        }
        return false;
    }
```
那么对于第一个例子如果我们想让 map.get(new Person("Tom"))不返回null
我们就需要，为Person类override hashCode方法。

当然hashCode的计算方法又是另外一个话题了，不过要保证的是
如果两个对象互相equals，那么你设计的hashCode算法对这两个对象计算出来的hash也应该相等。

所以如果你能保证你的类永远不会用在Hash相关的场景下，那你可以不override hashCode.
