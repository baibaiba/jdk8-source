package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xxx
 * @date 2022/2/21 14:12
 */
public class BlockIOTest {
    public static void main(String[] args) throws IOException {
        // 将服务器绑定到指定端口
        ServerSocket serverSocket = new ServerSocket(2345);

        // 接收连接（阻塞等待）
        Socket accept = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        PrintWriter printWriter = new PrintWriter(accept.getOutputStream());
        String request, response;
        while ((request = bufferedReader.readLine()) != null) {
            if ("Done".equals(request)) {
                break;
            }

            // do something
            response = "";
            // 向客户端输入response
            printWriter.println(response);
        }
    }
}
