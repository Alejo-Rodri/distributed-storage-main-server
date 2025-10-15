package co.edu.upb.distributed_storage_main_server.services.directory;

import co.edu.upb.distributed_storage_main_server.DTOs.directory.CreateDirectoryResponse;
import co.edu.upb.distributed_storage_main_server.grpc.client.directory.IDirectoryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectoryServices implements IDirectoryServices {
    private final IDirectoryClient iDirectoryClient;

    @Override
    public CreateDirectoryResponse createDirectory(String path) {
        return iDirectoryClient.createDirectory(path);
    }
}
