## RSocket

### 一.  概述

RSocket 是一种二进制字节流传输协议，位于 OSI 七层模型中的5、6层，对应 TCP/IP 模型中的应用层。RSocket 并没有规定必须使用何种底层传输层协议，开发者可以使用不同的底层传输协议，包括 TCP、WebSocket 和 Aeron。

RSocket 采用二进制格式，从而保证了传输的高效、节省带宽。RSocket 中的 R 是指 Reactive。因此，通过基于响应式流语义的流控制，RSocket 保证了消息传输中的双方不会因为请求的压力过大而崩溃。

### 二.  技术原理

### 三.  开发说明

### 四.  参考资料 

- https://www.vinsguru.com/rsocket-websocket-spring-boot
