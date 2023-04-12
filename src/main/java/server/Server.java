package server;

import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * La classe Server permet aux clients de se connecter a la
 * plateforme d'inscription et execute les requetes demandees par ceux-ci (and more...)
 * 
 * @author gaelle
 * 
 * @param REGISTER_COMMAND : Var String
 * @param LOAD_COMMAND : Var String
 * @param server : objet socket servant a ouvrir une connexion S/C
 * @param client : objet socket servant a ouvrir une connexion C/S
 * @param objectInputStream : donnee recue du serveur
 * @param objectOutputStream : donnee envoyee au serveur
 * @param handlers : gestion de la liste d'attente (clients)
 */

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1); //creation d'un S avec port d'entree + waitlist client
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Cette methode ajoute un client en waitlist d'acces au serveur.
     * 
     * @param h : variable client a rajouter
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Cette methode notifie chaque connexion d'un client au serveur.
     * 
     * @param cmd : info1 client (IP?)
     * @param arg : info2 client (Port?)
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Cette methode est une boucle infinie qui permet au serveur d'accepter 
     * les connexions entrantes des clients. Elle crée des flux d'entrée et 
     * de sortie pour communiquer avec les clients, appelle une méthode "listen()" 
     * pour écouter les messages du client et y répondre, puis ferme la connexion 
     * avec le client en appelant la méthode "disconnect()".
     * 
     * @param objectInputStream : flux d'entree du socket du client
     * @param objectOutputStream : flux de sortie du socket du client
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cette methode traite les requetes du client a partir 
     * du flux de objectInputStream du socket.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Cette methode traite la commande envoyée par le client en 
     * la divisant en deux parties: la commande elle-même et son argument.
     * 
     * @param line : variable contenant le message du client
     * 
     * @return Cette methode retourne une paire (clé, valeur) en utilisant la 
     * commande comme clé et la chaîne d'arguments comme valeur.
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Cette methode met fin a la connexion du client au serveur
     * apres avoir terminé le traitement des requetes.
     * 
     * @throws IOException
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * La méthode "handleEvents(String cmd, String arg)" permet de gérer les 
     * événements associés aux différentes commandes reçues du client. 
     * Elle prend en entrée une commande (cmd) et un argument (arg), 
     * et exécute le traitement approprié en fonction de la commande reçue.
     * 
     * @param cmd : variable contenant les instructions
     * @param arg : variable contenant l'argument (...)
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        // TODO: implémenter cette méthode
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        // TODO: implémenter cette méthode
    }
}

