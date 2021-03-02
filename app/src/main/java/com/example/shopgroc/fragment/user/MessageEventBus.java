package com.example.shopgroc.fragment.user;

import com.example.shopgroc.model.Rider;
import com.example.shopgroc.model.User;

public class MessageEventBus {
    User user;
    Rider rider;
    public MessageEventBus(User user){
        this.user = user;
    }
    public User getBusData(){
        return user;
    }

    public MessageEventBus(Rider rider){
        this.rider = rider;
    }
    public Rider getBusDataRIder(){
        return rider;
    }
}
