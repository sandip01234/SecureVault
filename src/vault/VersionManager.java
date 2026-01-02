package vault;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;



public class VersionManager {

    private static final String META_FILE = "vault/metadata.ser";
    public static Map<String, List<FileMeta>> versionMap = new HashMap<>();

    static {
        load();
    }

    public static void addVersion(String fileName) {
        List<FileMeta> versions = versionMap.getOrDefault(fileName, new ArrayList<>());

        FileMeta meta = new FileMeta();
        meta.fileName = fileName;
        meta.version = versions.size() + 1;  // independent per file
        meta.uploadedAt = LocalDateTime.now();

        versions.add(meta);
        versionMap.put(fileName, versions); // important!
        save();
    }

    public static int getLatestVersion(String fileName) {
        List<FileMeta> versions = versionMap.get(fileName);
        if (versions == null || versions.isEmpty()) return 0;
        return versions.get(versions.size() - 1).version;
    }

    public static List<FileMeta> getAllVersions(String fileName) {
        return versionMap.getOrDefault(fileName, new ArrayList<>());
    }

    /** Public getter for all files (read-only copy) */
    public static Map<String, List<FileMeta>> getAllFiles() {
        return new HashMap<>(versionMap);
    }

    private static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(META_FILE))) {
            oos.writeObject(versionMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(META_FILE))) {
            versionMap = (Map<String, List<FileMeta>>) ois.readObject();
        } catch (Exception e) {
            versionMap = new HashMap<>();
        }
    }

//    public static void deleteFileMetadata(String fileName) {
//        versionMap.remove(fileName);
//        save();
//    }



}
