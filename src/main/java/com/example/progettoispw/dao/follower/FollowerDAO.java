package com.example.progettoispw.dao.follower;

import java.util.List;


//Implementa le operazioni di attach() e detach()
public interface FollowerDAO {
    // Iscrive buyerUsername come Observer del Subject sellerUsername
    void follow(String buyerUsername, String sellerUsername);

    //Cancella l'iscrizione dell'Observer dal Subject
    void unfollow(String buyerUsername, String sellerUsername);

    /*
      Restituisce la lista degli username degli Observer iscritti al Subject.
      Usato da notifyFollowers() per sapere a chi inviare le notifiche.
     */
    List<String> getFollowers(String sellerUsername);

    //Verifica se il compratore è già iscritto al venditore
    boolean isFollowing(String buyerUsername, String sellerUsername);
}
