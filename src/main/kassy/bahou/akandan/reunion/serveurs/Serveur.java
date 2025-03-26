package main.kassy.bahou.akandan.reunion.serveurs;

import main.kassy.bahou.akandan.reunion.utils.Configuration;
import main.kassy.bahou.akandan.reunion.utils.ThreadManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

    public static void main(String[] args) {
        // Récupération du port dans config.properties
        int port = Configuration.getIntProperty("server.port");
        if (port <= 0) {
            System.err.println("Port invalide dans config.properties. Utilisation du port par défaut : 5002");
            port = 5002; // Valeur par défaut
        }

        System.out.println("Démarrage du serveur sur le port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"))) {
            System.out.println("Serveur en écoute sur le port " + port);

            // Boucle infinie pour accepter les connexions entrantes
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket.getRemoteSocketAddress());

                // Soumission d'une tâche asynchrone pour gérer le client
                ThreadManager.submitTask(() -> {
                    try {
                        // Ici, vous pouvez implémenter la logique d'échange avec le client
                        // (authentification, chat, etc.)
                        // Ex. : BufferedReader/PrintWriter, protocoles, etc.

                        // Exemple minimal :
                        clientSocket.getOutputStream().write("Bienvenue sur le serveur\n".getBytes());
                        clientSocket.getOutputStream().flush();

                        // Fermeture de la connexion après envoi
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // En cas d'arrêt du serveur, on arrête le pool de threads
            ThreadManager.shutdown();
        }
    }
}
