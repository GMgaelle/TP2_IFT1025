package server;

/**
 * la classe "ServerLauncher" est utilisé pour lancer le 
 * serveur en créant une instance de la classe "Server" 
 * et en appelant sa méthode "run()".
 *
 */
public class ServerLauncher {
    public final static int PORT = 1337;

    /**
     *  la méthode "run" de l'instance de la classe "Server" 
     * est appelée pour démarrer le serveur et le mettre en attente 
     * de connexions entrantes. Elle gère également les exceptions 
     * qui peuvent survenir lors de la création de l'instance de la classe "Server".
     * 
     * @param args : variable qui va contenir la prochaine connexion
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}