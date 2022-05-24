package com.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*public class Main {

    public static void main(String[] args) throws Exception {

//parti 1

        ServerSocket ss = new ServerSocket(1234);
        System.out.println("attend la connexion ...");
        Socket s =ss.accept();
        System.out.println("connexion du client"+s.getRemoteSocketAddress());

        //stream de communication
        InputStream is = s.getInputStream();
        OutputStream os =s.getOutputStream();
        System.out.println("j attend que le client envoie un octet");

        int nb = is.read(); //lire un octet
        System.out.println("j ai recu un nombre "+nb);
        int res = nb*12;
        System.out.println("j envoie la reponse"+res);
        os.write(res);
        s.close();

    }

}*/



public class Main extends  Thread{
    //part 2
     private int numberClient;
    public static void main(String[] args){
        new Main().start();
    }
  @Override
  public  void run(){

      try {
          ServerSocket ss = new ServerSocket(1234);
          System.out.println("demarrage du serveur....");

          while(true){
              Socket socket =ss.accept();
              ++numberClient;
              new Conversation(socket,numberClient).start();

          }

      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  class  Conversation extends Thread{



        private Socket socket;
        private  int numClt;
        public Conversation(Socket s, int num){
            this.socket=s;
            this.numClt=num;
        }

        @Override
        public void run(){

            try {
                        InputStream   is = socket.getInputStream();
                        InputStreamReader isr =new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        //pour envoyer un chaine de caratere il faut:
                        OutputStream os = socket.getOutputStream();
                        PrintWriter pw = new PrintWriter(os,true);


                        //address IP du client connecter
                        String IP =socket.getRemoteSocketAddress().toString();
                        System.out.println("bienvenu vous etes le client numero"+numberClient+"votre address IP est "+IP);


            while (true){
                        //lire la requete et envoyer la reponse
                         String req =br.readLine();
                         System.out.println("le client "+IP+"a envoye une requete "+req);
                         pw.println(req.length());
            }



            } catch (IOException e) {
                     e.printStackTrace();
                }

        }
  }

}
