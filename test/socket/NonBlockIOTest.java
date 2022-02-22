package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xxx
 * @date 2022/2/21 14:24
 */
public class NonBlockIOTest {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);// 设置非阻塞模式

        ServerSocket socket = serverSocketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(1234);
        socket.bind(inetSocketAddress);// 将服务器绑定到选定的端口上

        Selector selector = Selector.open(); // 打开Selector来处理channel

        // 将serverSocket注册到selector上以接收连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer msg = ByteBuffer.wrap("hi!\r\n".getBytes());

        for (; ; ) {
            int select = selector.select();// 等待需要处理的新事件；阻塞将一直持续到下一个传入事件

            Set<SelectionKey> selectionKeys = selector.selectedKeys();// 获取所有接收事件的selectorKey实例
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) { // 遍历处理事件
                SelectionKey next = iterator.next();
                iterator.remove();

                if (next.isAcceptable()) { // 检查事件是否是一个新的已经就绪可以被接收的连接
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    SocketChannel client = channel.accept();
                    client.configureBlocking(false);
                    // 接收客户端，并将它注册到选择器
                    client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                }

                if (next.isWritable()) { // 检查套接字是否已经准备好写数据
                    SocketChannel client = (SocketChannel) next.channel();
                    ByteBuffer buffer = (ByteBuffer) next.attachment();
                    while (buffer.hasRemaining()) {
                        if (client.write(buffer) == 0) { // 将数据写到已连接的客户端
                            break;
                        }
                    }

                    client.close(); // 关闭连接
                }
            }
        }
    }
}
