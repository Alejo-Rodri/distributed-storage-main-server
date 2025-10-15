package co.edu.upb.distributed_storage_main_server.DTOs.file;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "MoveRenameFileResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoveRenameFileResponse {
    private @Getter @Setter boolean success;
}
