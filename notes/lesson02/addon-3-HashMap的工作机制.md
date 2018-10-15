### HashMap的工作机制

[How HashMap works in Java](https://howtodoinjava.com/java/collections/how-hashmap-works-in-java/) 

读了上面这篇文章之后感觉写的很清楚。简单翻译下加深印象。
读完需要10分钟左右。

-----

#### HashMap怎么工作？

##### 1. 一句话描述
如果有人问我 HashMap是怎么工作的？我会简单的说：基于哈希原理。就是这么简单，在详细解说这个问题之前，我们必须搞明白哈希的基本原理。

##### 2. 什么是哈希？
哈希最简单的形式就是，根据一个变量或对象的属性通过某个算法得到的一个唯一的编码。

`译者注： MD5 / SHA-1 / SHA-256 这些都属于哈希算法，不属于加密算法` 

一个真正的哈希算法必须符合下面这个规则:
> 每次对同一个(==)或两个相等(equals)的对象应用哈希算法计算出来的结果应该是完全一样的。换句话说就是，两个相等(equals)的对象必须返回同样的哈希值。

有必要把原文贴出来：
> “Hash function should return the same hash code each and every time when the function is applied on same or equal objects. In other words, two equal objects must produce the same hash code consistently.”

>> 在Java中所有的对象默认都继承了 Object 类中定义的 hashCode() 方法. Object 的 hashCode 方法通过将对象的内部地址(internal address)转换为一个integer来为不同的对象计算出来不同的哈希值


##### 3. HashMap 中的内部类: Entry
从定义上来说 Map 就是一个能将传入的key映射到相应的value 的对象。

那么在`HashMap`内部就一定存在某种机制存储了这个键值对(key-value pair). 是的，这就是HashMap的内部类 `Entry`，他大致是长这样：

```
static class Entry<K ,V> implements Map.Entry<K, V>
{
    final K key;
    V value;
    Entry<K ,V> next;
    final int hash;
    ...//More code goes here
}
```

既然`Entry`是用来存储键值对(key-value pair)的，那么他必然要存储 `key` 和 `value`。并且你发现 `key` 被定义成了final，说明 key 是不能被更改的。

另外还有两个成员变量，`next` 和 `hash`。 接下来我们看看这俩是做什么用的。

##### 4. HashMap.put() 内部工作机制
在看 `put()` 方法的实现之前，我们必须要知道 `Entry` 对象是以数组的形式存储的。在 `HashMap` 类中是这样定义了这个 Entry 数组：

```
/**
 * The table, resized as necessary. Length MUST Always be a power of two.
 */
transient Entry[] table;
```

接下来我们来具体看下 `put()` 方法的代码实现：

```
/**
* Associates the specified value with the specified key in this map. If the
* map previously contained a mapping for the key, the old value is
* replaced.
*
* @param key
*            key with which the specified value is to be associated
* @param value
*            value to be associated with the specified key
* @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
*         if there was no mapping for <tt>key</tt>. (A <tt>null</tt> return
*         can also indicate that the map previously associated
*         <tt>null</tt> with <tt>key</tt>.)
*/
public V put(K key, V value) {
    if (key == null)
    return putForNullKey(value);
    int hash = hash(key.hashCode());
    int i = indexFor(hash, table.length);
    for (Entry<K , V> e = table[i]; e != null; e = e.next) {
        Object k;
        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
 
    modCount++;
    addEntry(hash, key, value, i);
    return null;
}
```

##### 4.1 put方法中做了什么？
我们来看看put方法的内部工作机制

* 首先，检查传入的key是不是null，如果是null的话，则对应的value会存在 table[0] 的位置上。这是因为 null 的 hashCode 永远返回 0.
* 然后，如果传入的key不是null，那么就会调用这个对象的 hashCode() 方法得到一个哈希值。然后基于这个哈希值来计算出来这个key应该被放到 `Entry[]` 数组的哪个index上。也就是 `int i = indexFor(hash, table.length);` 这句代码的作用。为什么不直接使用 hashCode 这个 int 值作为数组下标呢？因为默认情况下 Object 的 hashCode 方法（或者程序员override Object 类的 hashCode 之后返回的 hashCode 也可能会非常大）返回的这个 hashCode 的 int 值会非常大，比如 几万 几十万。而我们不可能创建一个无限大的数组来存储这样的下标，所以我们需要在 key 的 hashCode 的基础上，根据 Entry[] 数组的长度计算出来一个在数组长度范围内的index。JDK的设计者已经预料到这一点，所以他们实现了另外一个 hash `也就是indexFor()方法` 算法来根据传入的 key 的 hashCode 计算出一个符合数组长度范围的index.
* 至此，`indexFor(hash, table.length)` 方法就计算出来一个具体的用来存储到 Entry[] 数组的 index。

##### 4.2 解决哈希碰撞问题
现在是讲到关键部分了。我们知道两个不同的对象有可能会有相同的 hashCode , 那这两个不同的对象是怎么存到 Entry[] 数组的同一个 index (也称为 `bucket`) 的呢？(下面[X1]部分会解释为什么不同的对象可能会有相同的 hashCode).

答案是 `LinkedList`. 下面来解释此处的 LinkedList 跟 Entry[] 数组之间有什么关系。

如果你还有印象的话，`Entry` 类的定义中有一个 `next` 的成员变量。这个变量会指向他在 LinkedList 中的下一个对象。

* 所以为了解决哈希碰撞造成不同的对象指向同一个数组的index的问题，Entry 对象被设计成了一个（单）链表 LinkedList 格式. 
* 如果通过 key 的 hashCode 计算出来的 index 在 Entry[] 数组中还没有存储任何对象，那么这个key就存储在了这个位置上。
* 相反的，如果通过 key 的 hashCode 计算出来的 index 在 Entry[] 数组中已经存储了 Entry 对象，那么现在要 put 的这个 key 的 Entry （新的Entry）就会在这个index上替换原来的那个 Entry (原来的Entry)对象，并且，`新的Entry`的 `next` 指向`原来的Entry`
* 那么再考虑一个问题，如果我们 put 的这个对象（key）跟之前某个已经存储到 Map 的对象是同一个key会发生什么？理论上来说，新的对象会替换原来的对象。不过这是怎么做到的呢？
* 他内部是这样，通过传入的key的hashCode计算出来 index，如果发现在这个index上已经存储了对象（也就是发生了碰撞），那么就会先遍历这个 index 所指定的链表 LinkedList，如果发现新传入的key跟某个已经存在的key是同一个对象 (e.key == key), 或者新传入的key跟某个已经存在的key相等 (`key.equals(e.key)`) 那么就用新的value把原来的value替换掉。 -- 当然，如果遍历完成之后并没有发现新传入的key和这个链表中的某个对象的key是同一个对象或equals的话，就会作为一个新的Entry放入到这个链表中（确切的说按上面的分析，是在链表的头部插入新的Entry）
* 这样 HashMap 就完成了 put 操作

##### 5. HashMap.get() 内部工作机制
现在我们知道了这些键值对在 HashMap 中是通过 Entry数组 + Entry链表 来存储的，那么下一个问题就是，当我调用 HashMap.get(key) 方法的时候，HashMap 是如何查找这个返回给我这个key所对应的value的呢？

* 他的工作机制其实是跟 put 非常相似的
* 首先会通过传入的key的hashCode计算出来一个 index
* 然后用这个index去Entry数组中看这个位置有没有Entry对象
* 没有的话 直接返回null
* 有的话 遍历这个Entry的链表（注意这个链表上的所有对象都具有同样的hashCode, 因为只有发生hash碰撞的情况才会存储到这个链表中），如果传入的key跟这个链表中的某个Entry的key是同一个对象 (key == e.key) 或者 他俩相等 (`key.equals(e.key)`) 则返回这个Entry的value

代码如下：

```
/**
* Returns the value to which the specified key is mapped, or {@code null}
* if this map contains no mapping for the key.
*
* <p>
* More formally, if this map contains a mapping from a key {@code k} to a
* value {@code v} such that {@code (key==null ? k==null :
* key.equals(k))}, then this method returns {@code v}; otherwise it returns
* {@code null}. (There can be at most one such mapping.)
*
* </p><p>
* A return value of {@code null} does not <i>necessarily</i> indicate that
* the map contains no mapping for the key; it's also possible that the map
* explicitly maps the key to {@code null}. The {@link #containsKey
* containsKey} operation may be used to distinguish these two cases.
*
* @see #put(Object, Object)
*/
public V get(Object key) {
    if (key == null)
    return getForNullKey();
    int hash = hash(key.hashCode());
    for (Entry<K , V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
        Object k;
        if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
            return e.value;
    }
    return null;
}
```

##### 6. HashMap 内部工作机制的关键因素
1. HashMap 中是以数组形式存储了 Entry
2. 数组中的每个元素称为一个 bucket，这个 bucket 是一个链表，Entry数组中的这个元素是这个 bucket 链表的头结点
3. Key 这个对象的 hashCode() 方法返回的 hashCode 的 int 值会被用来计算他在Entry数组中的位置
4. Key 这个对象的 equals() 方法用来维护他在 bucket 链表中的唯一性
5. Value 对象的 hashCode() 和 equals() 方法并没有用在 HashMap的 get() 和 put() 方法中
6. null 对象的hashCode永远是0，并且null对象会被存储到Entry数组中下标为0的位置

##### Java 8 中对HashMap的改进
简述：
> 上述bucket的数据结构由链表改为了红黑树，用来提高hash碰撞频繁发生时的效率。
因为链表的查找算法时间复杂度为 `O(n)`, 而红黑树的查找算法复杂度为 `O(log n)`


#### [X1] 首先来说下什么是哈希碰撞。维基百科上是这么定义的：

```
In computer science, 
a collision or clash is a situation that occurs
when two distinct pieces of data have the same hash value, 
checksum, fingerprint, or cryptographic digest. 

Collisions are unavoidable whenever members of a very large set
(such as all possible person names, or all possible computer files) 
are mapped to a relatively short bit string.
```

简单翻译就是：
> 两个不同的数据通过某个哈希算法得到了相同的哈希值就称作碰撞。并且当将一个非常大的数据集映射到一个固定长度的哈希值的时候，碰撞是不可避免的。

怎么理解呢？

因为哈希算法是将传入的数据 （比如字符串，文件等等）进行某种算法计算出来一个固定长度的哈希值（不同算法产生的哈希值的长度不同，但同一个算法产生的哈希值的长度是固定的，下面会说），传入的数据可以说是无数种可能性，但产出的结果却是固定长度的，固定长度可以先理解为固定的数量。将无数的东西映射到有限的范围内，则在这个有限的范围内必定会出现有些不同的东西(要hash的数据)被计算成了同一个东西(hashCode).



