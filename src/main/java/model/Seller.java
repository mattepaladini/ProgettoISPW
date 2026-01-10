package model;

import java.io.Serializable;

public class Seller extends User implements Serializable {

    private CardCatalog catalog;

    public Seller(String username, String password) {
        super(username, password, UserType.SELLER);
    }


    public void addCatalog(CollectableCard card){
        //.......
    }

    public void removeCard(CollectableCard card){
        //.......
    }
    public String getSellerName(){

        return this.getUsername();
    }

}
