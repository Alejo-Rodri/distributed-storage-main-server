package co.edu.upb.distributed_storage_main_server.DTOs.file;

import co.edu.upb.distributed_storage_main_server.domain.FileEntity;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "GetFileResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFileResponse {
    private @Getter @Setter FileEntity file;
}
