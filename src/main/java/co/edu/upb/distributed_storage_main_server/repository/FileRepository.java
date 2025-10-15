package co.edu.upb.distributed_storage_main_server.repository;

import co.edu.upb.distributed_storage_main_server.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwnerId(Long ownerId);

    // Encuentra todos los archivos dentro de un directorio específico
    List<FileEntity> findByDirectoryId(Long directoryId);

    // Busca archivos por nombre dentro de un directorio (útil para validaciones o búsquedas)
    List<FileEntity> findByNameAndDirectoryId(String name, Long directoryId);

    // Busca por ruta completa (si la manejas de forma única)
    FileEntity findByPath(String path);
}
