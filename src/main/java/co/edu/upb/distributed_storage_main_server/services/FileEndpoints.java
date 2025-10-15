package co.edu.upb.distributed_storage_main_server.services;

import co.edu.upb.distributed_storage_main_server.DTOs.file.GetFileRequest;
import co.edu.upb.distributed_storage_main_server.DTOs.file.GetFileResponse;
import co.edu.upb.distributed_storage_main_server.repository.FileRepository;
import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FileEndpoints {
    private final FileRepository fileRepository;

    public FileEndpoints(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PayloadRoot(namespace = Singleton.FILE_NAMESPACE_URI, localPart = "GetFileRequest")
    @ResponsePayload
    public GetFileResponse getFile(@RequestPayload GetFileRequest request) {
        var response = new GetFileResponse();
        var file = fileRepository.findById(request.getId()).orElse(null);
        response.setFile(file);

        return response;
    }
}
