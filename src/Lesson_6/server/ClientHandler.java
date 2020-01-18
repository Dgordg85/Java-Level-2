package Lesson_6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ServerMain server;
    private String nick;

    public ClientHandler(ServerMain server, Socket socket) {

        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
          
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = in.readUTF();
                            if (str.startsWith("/auth")){
                                String[] token = str.split(" ");
                                String newNick = AuthService.getNickByLoginPass(token[1], token[2]);
                                if (newNick != null){
                                    sendMsg("/authok");
                                    nick = newNick;
                                    server.subscribe(ClientHandler.this);
                                    break;
                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }

                        while (true){
                            String str = in.readUTF();
                            if (str.equals("/end")){
                                out.writeUTF("/serverClosed");
                                break;
                            }
                            System.out.println("client: " + str);
                            server.broadcastMsg(nick + ": " + str);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(String str){
        try {
            out.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
