/**
 * Cette interface définit une methode permettant de réagir aux 
 * événements associés à une commande envoyée par le client. 
 * Les classes qui implémentent cette interface 
 * fournissent leur propre logique pour traiter ces événements.
 */
package server;

@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
