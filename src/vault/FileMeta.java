package vault;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FileMeta implements Serializable {
    public String fileName;
    public int version;
    public LocalDateTime uploadedAt;


}
