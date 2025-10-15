package co.edu.upb.distributed_storage_main_server.DTOs.file;

import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@XmlRootElement(namespace = Singleton.FILE_NAMESPACE_URI, name = "MoveRenameFileRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoveRenameFileRequest {
    private @Getter String sourceFile;
    private @Getter String destinationDir;
}
