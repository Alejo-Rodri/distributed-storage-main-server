package co.edu.upb.distributed_storage_main_server.controller;

import co.edu.upb.distributed_storage_main_server.DTOs.directory.CreateDirectoryRequest;
import co.edu.upb.distributed_storage_main_server.DTOs.directory.CreateDirectoryResponse;
import co.edu.upb.distributed_storage_main_server.services.directory.IDirectoryServices;
import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@AllArgsConstructor
public class DirectoryEndpoints {
    private final IDirectoryServices iDirectoryServices;

    @PayloadRoot(namespace = Singleton.DIRECTORY_NAMESPACE_URI, localPart = "CreateDirectoryRequest")
    @ResponsePayload
    public CreateDirectoryResponse createDirectory(@RequestPayload CreateDirectoryRequest request) {
        return iDirectoryServices.createDirectory(request.getPath());
    }
}
