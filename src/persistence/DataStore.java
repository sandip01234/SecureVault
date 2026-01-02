package persistence;

import auth.User;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.*;


public class DataStore {

    private static final String FILE = "data.ser";
    public static Map<String , User> users = new HashMap<>();
    static {load();}

    @SuppressWarnings("unchecked")
    private static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
