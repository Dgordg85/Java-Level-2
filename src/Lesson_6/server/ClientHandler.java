package Lesson_6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ServerMain server;
    private String nick;
    private List<String> blacklist;

    public ClientHandler(ServerMain server, Socket socket) {

        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            blacklist = new ArrayList();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = in.readUTF();
                            if (str.startsWith("/auth")){
                                String[] token = str.split(" ");
                                String newNick = AuthService.getNickByLoginPass(token[1], token[2]);
                                if (newNick != null && server.isNickUnique(newNick)){
                                    sendMsg("/authok " + newNick);
                                    nick = newNick;
                                    server.subscribe(ClientHandler.this);
                                    AuthService.fillBlackList(ClientHandler.this);
                                    server.broadcastMsg(ClientHandler.this, "К чату присоединился " + nick + ".");
                                    break;
                                } else if (!server.isNickUnique(newNick)){
                                    sendMsg("Данный ник уже используется!");
                                }else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }

                        while (true){
                            String str = in.readUTF();
                            if (str.startsWith("/")){
                                if (str.equals("/end")){
                                    out.writeUTF("/serverClosed");
                                    server.broadcastMsg(ClientHandler.this, nick + " покинул чат!");
                                    break;
                                }
                                if (str.startsWith("/w")) {
                                    String[] strArr = str.split(" ", 3);
                                    server.personalMsg(ClientHandler.this, strArr[1], strArr[2]);
                                }
                                if (str.startsWith("/blacklist")){
                                    String[] strArr = str.split(" ");
                                    AuthService.addUserToBlacklist(ClientHandler.this, strArr[1]);
                                    sendMsg("Вы добавили пользователя " + strArr[1] + " в черный список!");
                                }
                            } else {
                                server.broadcastMsg(ClientHandler.this, nick + ": " + str);
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }catch (IndexOutOfBoundsException e) {
                        sendMsg("Неверный логин/пароль!");
                    }finally{
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

    public String getNick() {
        return nick;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }
}
