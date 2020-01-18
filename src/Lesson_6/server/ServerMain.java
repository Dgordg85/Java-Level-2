package Lesson_6.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class ServerMain {
    private volatile Vector<ClientHandler> clients;

    public ServerMain() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;


        try {
            server = new ServerSocket(15000);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                System.out.println("Клиент подключился");

                clients.add(new ClientHandler(this, socket, clients.size()));
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
        }
    }
    public void broadcastMsg(String msg) {
        synchronized (clients){
            for (ClientHandler o: clients){
                o.sendMsg(msg);
            }
        }

    }

    public void deleteClient(int id){
        synchronized (clients){

            Iterator<ClientHandler> iterator = clients.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getId() == id){
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
