# ThreadLocal

## ThreadLocal核心机制
* 每个Thread线程内部都有一个ThreadLocalMap
* ThreadLocalMap中Entry存储线程本地对象ThreadLocal(key)和线程的变量副本(value)
* Thread中的ThreadLocalMap是由ThreadLocal维护，由ThreadLocal负责向ThreadLocalMap进行变量值的set、get操作

`因此对于不同的线程，每次获取存储的副本值时，别的线程并不能获得当前线程的副本值，形成副本的隔离，互不干扰`

Thread内部ThreadLocalMap在类中描述
   ` public class Thread implements Runnable {
   /*  ThreadLocal values pertaining to this thread. This map is maintained
     *  by the ThreadLocal class. */
        //与此线程相关的threadlocal值。Map是由threadlocal维护的
        ThreadLocal.ThreadLocalMap threadLocals = null;
    }
    `
## ThreadLocal解析
核心方法

` private T setInitialValue()
 public T get()
 public void set(T value)
 public void remove()
`
* setInitialValue() 用于获取初始化当前线程的变量值
* get() 用于获取当前线程的变量值
* set() 用于设置当前线程的变量值
* remove() 用于移除取当前线程的变量值

### setInitialValue()

```
private T setInitialValue() {
    T value = initialValue();
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
    return value;
}

protected T initialValue() {
    return null;
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}

void createMap(Thread t, T firstValue) {
    t.threadLocals = new ThreadLocalMap(this, firstValue);
}
```

### get()
```
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```

### set()
```
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```
### remove()

```
public void remove() {
    ThreadLocalMap m = getMap(Thread.currentThread());
    if (m != null)
        m.remove(this);
    }

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```
     
## 类图

![示例ThreadLocalMap](https://raw.githubusercontent.com/wxmylife/wxmylife/master/art/ThreadLocalMap.webp)