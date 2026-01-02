package vault;

import security.EncryptionUtil;
import util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static persistence.DataStore.save;
import static vault.VersionManager.versionMap;

public class VaultManager {

    private static final Path VAULT = Paths.get("vault");

    static {
        try { FileUtil.createDirIfNotExists(VAULT); } catch (Exception ignored) {}
    }

    public static void storeFile(Path file) throws Exception {
        String name = file.getFileName().toString();
        int version = VersionManager.getLatestVersion(name) + 1;
        Path dest = VAULT.resolve(name + "_v" + version + ".enc");
        EncryptionUtil.encrypt(file, dest);
        VersionManager.addVersion(name);
    }

    public static void retrieveFile(String fileName, int version, Path dest) throws Exception {
        Path src = VAULT.resolve(fileName + "_v" + version + ".enc");
        EncryptionUtil.decrypt(src, dest);
    }

    public static Map<String, List<FileMeta>> getAllFiles() {
        return VersionManager.getAllFiles(); // access via getter
    }

    public static void deleteFile(String fileName) throws IOException {
        List<FileMeta> versions = versionMap.get(fileName);
        if (versions != null) {
            for (FileMeta meta : versions) {
                Path filePath = VAULT.resolve(meta.fileName + "_v" + meta.version + ".enc");
                java.nio.file.Files.deleteIfExists(filePath);
            }
            versionMap.remove(fileName);
            save();
        }
    }
}
