/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class ServerThread implements Runnable {

    private Socket socketOfServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private String in1=null, in2=null, in3="o";
    
    public BufferedReader getIs() {
        return is;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        in1="Server thread number " + clientNumber + " Started";
        System.out.println(in1);
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            in2="Khời động luông mới thành công, ID là: " + clientNumber;
            System.out.println(in2);
            write("get-id" + "," + this.clientNumber);
            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã đăng nhập---");
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split(",");
                if(messageSplit[0].equals("send-to-global")){
                    Server.serverThreadBus.boardCast(this.getClientNumber(),"global-message"+","+"Client "+messageSplit[2]+": "+messageSplit[1]);
                }
                if(messageSplit[0].equals("send-to-person")){
                    Server.serverThreadBus.sendMessageToPersion(Integer.parseInt(messageSplit[3]),"Client "+ messageSplit[2]+" (tới bạn): "+messageSplit[1]);
                }
            }
        } catch (IOException e) {
            isClosed = true;
            Server.serverThreadBus.remove(clientNumber);
            in3=this.clientNumber+" đã thoát";
            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã thoát---");
        }
    }
    public void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
    
}
