package co.edu.upb.distributed_storage_main_server.grpc.client.directory;

import co.edu.upb.distributed_storage_main_server.DTOs.directory.CreateDirectoryResponse;

public interface IDirectoryClient {
    CreateDirectoryResponse createDirectory(String path);
}
