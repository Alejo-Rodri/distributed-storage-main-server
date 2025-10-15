package co.edu.upb.distributed_storage_main_server.controller;

import co.edu.upb.distributed_storage_main_server.DTOs.file.*;
import co.edu.upb.distributed_storage_main_server.repository.FileRepository;
import co.edu.upb.distributed_storage_main_server.services.file.IFileServices;
import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FileEndpoints {
    private final FileRepository fileRepository;
    private final IFileServices iFileServices;

    public FileEndpoints(FileRepository fileRepository, IFileServices iFileServices) {
        this.fileRepository = fileRepository;
        this.iFileServices = iFileServices;
    }

    @PayloadRoot(namespace = Singleton.FILE_NAMESPACE_URI, localPart = "GetFileRequest")
    @ResponsePayload
    public GetFileResponse getFile(@RequestPayload GetFileRequest request) {
        var response = new GetFileResponse();
        var file = fileRepository.findById(request.getId()).orElse(null);
        response.setFile(file);

        return response;
    }

    // mover o renombrar un archivo
    @PayloadRoot(namespace = Singleton.FILE_NAMESPACE_URI, localPart = "MoveRenameFileRequest")
    @ResponsePayload
    public MoveRenameFileResponse mvRenameFile(@RequestPayload MoveRenameFileRequest request) {
        var response = new MoveRenameFileResponse();
        response.setSuccess(iFileServices.mvRenameFile(request.getSourceFile(), request.getDestinationDir()));

        return response;
    }

    @PayloadRoot(namespace = Singleton.FILE_NAMESPACE_URI, localPart = "DeleteFileByIdRequest")
    @ResponsePayload
    public DeleteFileByIdResponse deleteFileByIdResponse(@RequestPayload DeleteFileByIdRequest request) {
        var response = new DeleteFileByIdResponse();
        var file = fileRepository.findById(request.getId());

        file.ifPresent(fileEntity ->
                response.setSuccess(iFileServices.deleteFile(fileEntity.getPath()))
        );

        return response;
    }
}
