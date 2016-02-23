package com.polimi.jgc.findamate;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by Javi on 18/02/2016.
 */
    public class Main
    {
        public static String APP_ID = "E26DA9BB-7E36-1AC7-FF12-6D7227BD4700";
        public static String SECRET_KEY = "A668187A-4CD0-4F92-FF03-A42E36455000";
        public static String VERSION = "v1";

        public static void main( String[] args )
        {
            Backendless.initApp( APP_ID, SECRET_KEY, VERSION );

            registerUser();
            registerUserAsync();
        }

        private static void registerUser()
        {
            BackendlessUser user = new BackendlessUser();
            user.setEmail( "spidey@backendless.com" );
            user.setPassword( "greeng0blin" );

            BackendlessUser registeredUser = Backendless.UserService.register( user );
            System.out.println( "User has been registered - " + registeredUser.getObjectId() );
        }

        private static void registerUserAsync()
        {
            AsyncCallback<BackendlessUser> callback = new AsyncCallback<BackendlessUser>()
            {
                @Override
                public void handleResponse( BackendlessUser registeredUser )
                {
                    System.out.println( "User has been registered - " + registeredUser.getObjectId() );
                }

                @Override
                public void handleFault( BackendlessFault backendlessFault )
                {
                    System.out.println( "Server reported an error - " + backendlessFault.getMessage() );
                }
            };

            BackendlessUser user = new BackendlessUser();
            user.setEmail( "green.goblin@backendless.com" );
            user.setPassword( "sp1dey" );
            user.setProperty( "phoneNumber", "212-555-1212" );

            Backendless.UserService.register( user, callback );
        }
    }


