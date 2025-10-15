package co.edu.upb.distributed_storage_main_server.domain;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Entity
@Table(name = "file")
@XmlRootElement(name = "File")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path;
    private Long sizeBytes;
    private String mimeType;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "directory_id")
    private Long directoryId;
}
