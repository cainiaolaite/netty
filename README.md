# 简介
这是一个关于netty 每个功能的应用
主包为 com.hua.netty 其子包代表这个应用的功能

## com.hua.netty.http  （netty创建http服务器）
    ObserveChannelLifeCycle 观察通道的生命周期
    一.观察所得
        1.IE 请求时
            SocketChannel 初始化通道,为管道添加通道
            channelActive 激活通道
            handlerAdded 添加通道
            channelRegistered 注册通道
            channelActive 激活通道
            收到http请求，url:/
            channelReadComplete 通道读取完成
            channelReadComplete 通道读取完成
            channelInactive 未激活通道
            channelUnregistered 解除注册通道
            handlerRemoved 删除通道
        2.客户端关闭时
            channelReadComplete 通道读取完成
            exceptionCaught 通道异常
            channelInactive 未激活通道
            channelUnregistered 解除注册通道
            handlerRemoved 删除通道
        
## com.hua.netty.socket  （netty创建socket服务器）
    利用Netty 搭建的简单的 socket 通信
    1.实现了客户端通过socket向服务器，写数据，并接收服务器发过来的数据
    
## com.hua.netty.chat  (netty创建聊天服务器)
    需求：
        1.服务器能显示用户的上线
        2.如果有用户上线，服务器能通知其他的用户，某某已经加入
        3.用户的消息能发送给加入了聊天室的所有人员
## com.hua.netty.userevent  (通过channel的输入与输出的触发时间来检测输入与输出是否闲置，他会触发用户事件)
    需求：
        判断channel 是否闲置
        读闲置
        写闲置
        读写都闲置
    
    作用：这里要说以下长连接的特点，长连接特点是可以双向通信，
          并不是说客户端就时刻和保持连接着,在网络中断（wifi断了），
          服务器和客户端是感觉不到彼此的存在。想要他们感觉到彼此
          的存在就必须不间断的发送心跳包，这样读写就不会闲置。
          读写不闲置就说明服务器和客户端都保持在线。
        
## com.hua.netty.websocket 
    

