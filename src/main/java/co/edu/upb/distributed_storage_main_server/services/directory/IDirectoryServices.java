package co.edu.upb.distributed_storage_main_server.services.directory;

import co.edu.upb.distributed_storage_main_server.DTOs.directory.CreateDirectoryResponse;

public interface IDirectoryServices {
    CreateDirectoryResponse createDirectory(String path);
}
