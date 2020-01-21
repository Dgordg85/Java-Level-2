package Lesson_6.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerMain {
    private volatile Vector<ClientHandler> clients;

    public ServerMain() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;


        try {
            AuthService.connect();
            //String str = AuthService.getNickByLoginPass("login1", "pass1");
            //System.out.println(str);
            server = new ServerSocket(15000);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }
    void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o: clients){
            if (!o.getBlacklist().contains(from.getNick())){
                o.sendMsg(msg);
            }

        }
    }

    void personalMsg(ClientHandler from, String client, String msg){
        ClientHandler ch = getClient(client);
        if (ch != null){
            ch.sendMsg("Личное сообщение от " + from.getNick() + ": " + msg);
            from.sendMsg("Личное сообщение для " + ch.getNick() + ": " + msg);
        } else {
            from.sendMsg("Не удалось отправить сообщение: " + msg + " для " + client + "\nНет такого пользователя!");
        }
    }

    void subscribe(ClientHandler client){
        clients.add(client);
        broadcastClientList();
    }

    void unsubscribe(ClientHandler client){
        clients.remove(client);
        broadcastClientList();
    }

    boolean isNickUnique(String nick){
        for (ClientHandler client : clients){
            if (client.getNick().equals(nick)){
                return false;
            }
        }
        return true;
    }

    ClientHandler getClient(String nick){
        for (ClientHandler client : clients){
            if (client.getNick().equals(nick)){
                return client;
            }
        }
        return null;
    }

    public  void  broadcastClientList(){
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        for (ClientHandler o : clients){
            sb.append(o.getNick() + " ");
        }
        String out = sb.toString();
        for (ClientHandler o: clients){
            o.sendMsg(out);
        }
    }
}
