package co.edu.upb.distributed_storage_main_server.DTOs.file;

import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "UploadFileResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class UploadFileResponse {
    private boolean success;
    private String message;
}
