package auth;

import security.HashUtil;
import persistence.DataStore;

public class AuthService {


    public static boolean register(String username, String password) {
        if (DataStore.users.containsKey(username)) return false;
        DataStore.users.put(username, new User(username, HashUtil.sha256(password)));
        DataStore.save();
        return true;
    }

    public static User login(String username, String password) {
        User u = DataStore.users.get(username);
        if (u != null && u.getPasswordHash().equals(HashUtil.sha256(password))) {
            return u;
        }
        return null;
    }

}
