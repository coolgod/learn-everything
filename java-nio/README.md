#知识点

## 核心概念
- Java NIO: Java New I/O
- Channel
- Buffer
- Selector

## Channel
### 不同类型Channel
- FileChannel：文件I/O，没有non-blocking mode
- DatagramChannel：UDP
- SocketChannel：TCP
- ServerSocketChannel：监听incoming TCP
- ...

### Scatter / Gather
- Scatter: 一个channel读到多个buffer中
- Gather：多个buffer写入到一个channel

## Buffer
### 不同类型Buffer
- ByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer
- ...

### Buffer常用方法
- `allocate()`
- `flip()`
- `clear()`：清空整个buffer
- `compact()`：清空已读data
- `rewind()`：position重置0
- `mark()`：用于position的`reset()`
- `equal()` / `compareTo()`

### Buffer三个指标
- capacity
- position：写入和读取模式的起始位置
- limit：写入（和capacity相同）和读取模式的终点

## 使用Selector
- 注册Channel
- 调用`select()`

## Others
https://github.com/jjenkov/java-nio-server