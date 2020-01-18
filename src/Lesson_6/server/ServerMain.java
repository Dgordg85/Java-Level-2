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
    void broadcastMsg(String msg) {
        for (ClientHandler o: clients){
            o.sendMsg(msg);
        }
    }

    void personalMsg(ClientHandler client, String msg){
        client.sendMsg(msg);
    }

    void subscribe(ClientHandler client){
        clients.add(client);
    }

    void unsubscribe(ClientHandler client){
        clients.remove(client);
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
}
