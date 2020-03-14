package com.lorisleiva.minecravel;

import java.io.IOException;

public class UsersAsVillagersThread extends Thread
{
    @Override
    public void run()
    {
        try {
            Minecravel.usersAsVillagersManager.init();
            Minecravel.usersAsVillagersManager.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
