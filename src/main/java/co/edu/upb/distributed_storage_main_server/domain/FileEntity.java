package co.edu.upb.distributed_storage_main_server.domain;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "file")
@XmlRootElement(name = "File")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    private @Getter @Setter String name;
    private @Getter @Setter String path;
    private @Getter @Setter Long sizeBytes;
    private @Getter @Setter String mimeType;

    @Column(name = "owner_id")
    private @Getter @Setter Long ownerId;

    @Column(name = "directory_id")
    private @Getter @Setter Long directoryId;
}
