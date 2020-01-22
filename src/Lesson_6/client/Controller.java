package Lesson_6.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final  String IP_ADRESS = "localhost";
    final int PORT = 15000;

    @FXML
    HBox upperPanel;

    @FXML
    HBox bottomPanel;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    ListView<String> clientList;

    @FXML
    ListView<VBox> messagesView;

    @FXML
    HBox textButtonBox;


    private boolean isAuthorized;
    private String nick = "";

    public void setAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;

        if (!isAuthorized){
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            textButtonBox.setVisible(false);
            textButtonBox.setManaged(false);
            clientList.setManaged(false);
            clientList.setVisible(false);

        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            textButtonBox.setVisible(true);
            textButtonBox.setManaged(true);
            clientList.setManaged(true);
            clientList.setVisible(true);
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                   try{
                       while (true){
                           String str = in.readUTF();
                           if (str.startsWith("/authok")){
                               String[] strArr = str.split(" ");
                               nick = strArr[1];
                               setAuthorized(true);
                               break;
                           } else {
                               setMsg(str);
                           }
                       }

                       while (true){
                           String str = in.readUTF();
                           if (str.equals("/serverClosed")) break;
                           if (str.startsWith("/clientlist")){
                               String[] tokens = str.split(" ");
                               Platform.runLater(new Runnable() {
                                   @Override
                                   public void run() {
                                       clientList.getItems().clear();
                                       for (int i = 1; i < tokens.length; i++) {
                                           clientList.getItems().add(tokens[i]);
                                       }
                                   }
                               });

                           } else {
                               setMsg(str);
                           }

                       }
                   } catch (IOException e){
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
                       setAuthorized(false);
                   }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(){
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMsg(String str) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label message = new Label(str);
                VBox messageBox = new VBox(message);
                if(nick != "") {
                    String[] mass = str.split(":");
                    if(nick.equalsIgnoreCase(mass[0])) {
                        messageBox.setAlignment(Pos.CENTER_RIGHT);
                    }
                }
                messagesView.getItems().add(messageBox);
            }
        });
    }


    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()){
            connect();
        }

        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
