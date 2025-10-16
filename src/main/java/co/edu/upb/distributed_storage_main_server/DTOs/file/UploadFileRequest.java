package co.edu.upb.distributed_storage_main_server.DTOs.file;

import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import jakarta.activation.DataHandler;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlMimeType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(namespace = Singleton.FILE_NAMESPACE_URI, name = "UploadFileRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class UploadFileRequest {
    private String filename;

    @XmlMimeType("application/octet-stream")
    private DataHandler fileData;
}
