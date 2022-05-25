package com.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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







//serveur pour la simple connection via telnet en utilisant  telnet localhost 1234
/*public class Main extends  Thread{
    //part 2 serveuc connection client

     private int numberClient;
     private boolean isActive=true;
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

}*/


/*

public class Main extends  Thread{
    //part 3 seuver jeu
     private int numberClient;
     private boolean isActive=true;
     private int numberSecret;
     private boolean fin;
     private String winner;
    public static void main(String[] args){
        new Main().start();
    }
  @Override
  public  void run(){

      try {
          ServerSocket ss = new ServerSocket(1234);

          //creer un nombre secret et verifier
          numberSecret =  new Random().nextInt(1000);
          System.out.println("demarrage du serveur....");
          System.out.println(" le nombre secret est "+numberSecret);
          while(isActive){
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
                        pw.println("Devinez le nombre secret ?");


            while (true){
                        //lire la requete et envoyer la reponse
                         String req =br.readLine();
                         int nombre=0;
                         boolean correctFormatRequest=false;
                         try{
                            nombre=Integer.parseInt(req);
                            correctFormatRequest=true;

                         }catch(NumberFormatException e){
                            correctFormatRequest=false;

                         }

                         
                         System.out.println("Client IP: "+IP+"Tentative avec le nombre "+nombre);
                        if(correctFormatRequest){
                            if(fin==false){
                                if(nombre > numberSecret){
                               pw.println("Votre nombre est superieur au nombre secret");
                                }else if (nombre < numberSecret){
                                   pw.println("Votre nombre est inferieur au nombre secret au nombre secret");
                              
                                }else{
                                    pw.println("BRAVO, vous avez gagner");
                                    winner=IP;
                                    System.out.println("BRAVO au gagnant, IP client:"+winner);
                                    fin=true;
   
                                }
   
                            }else{
                            pw.println("le jeu est terminer et le gagnant est :"+winner);
   
                            }
                        }
                        

                         System.out.println("le client "+IP+"a envoye une requete "+req);
                         pw.println("Length"+req.length());
            }



            } catch (IOException e) {
                     e.printStackTrace();
                }

        }
  }

}
*/



public class Main extends  Thread{
    //part 3 seuver chat
     private int numberClient;
     private boolean isActive=true;
     private List<Conversation> clients=new ArrayList<Conversation>();
    public static void main(String[] args){
        new Main().start();
    }
  @Override
  public  void run(){

      try {
          ServerSocket ss = new ServerSocket(1234);
          System.out.println("serveur demarrer");
          while(isActive){
              Socket socket =ss.accept();
              ++numberClient;
              Conversation conversation=new Conversation(socket,numberClient);
              clients.add(conversation);



          }

      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  class  Conversation extends Thread{
        protected Socket socketclient;
        protected  int numClt;
        public Conversation(Socket s, int num){
            this.socketclient=s;
            this.numClt=num;
        }

        public void broadcastMessage(String message, Socket socket){
            try{
                for(Conversation client:clients){
                    //if(client.socketclient!=socket){
                        PrintWriter printWriter = new PrintWriter(client.socketclient.getOutputStream(),true);
                        printWriter.println(message);
                    //}
                }

            }catch(IOException e){
            e.printStackTrace();
            }

        }

                        @Override
                        public void run(){
                        try {
                        InputStream   is = socketclient.getInputStream();
                        InputStreamReader isr =new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        PrintWriter pw = new PrintWriter(socketclient.getOutputStream(),true);
                        String ipClient =socketclient.getRemoteSocketAddress().toString();
                        pw.println("bienvenue, vous etes le client numero"+numClt);
                        System.out.println("Connexion du client numero"+numClt+"IP="+ipClient);

                        while (true){
                        //lire la requete et envoyer la reponse
                        String req = br.readLine();
                        broadcastMessage(req, socketclient);
                        }} catch (IOException e) {
                        e.printStackTrace();
                        }}}}
