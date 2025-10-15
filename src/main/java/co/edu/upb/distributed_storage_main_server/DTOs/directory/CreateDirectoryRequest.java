package co.edu.upb.distributed_storage_main_server.DTOs.directory;

import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@XmlRootElement(namespace = Singleton.DIRECTORY_NAMESPACE_URI, name = "CreateDirectoryRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class CreateDirectoryRequest {
    private String path;
}
