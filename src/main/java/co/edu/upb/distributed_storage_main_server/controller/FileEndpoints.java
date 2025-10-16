package co.edu.upb.distributed_storage_main_server.controller;

import co.edu.upb.distributed_storage_main_server.DTOs.file.*;
import co.edu.upb.distributed_storage_main_server.repository.FileRepository;
import co.edu.upb.distributed_storage_main_server.services.file.IFileServices;
import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.File;
import java.io.InputStream;

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

    @PayloadRoot(namespace = Singleton.FILE_NAMESPACE_URI, localPart = "UploadFileRequest")
    @ResponsePayload
    public UploadFileResponse uploadFile(@RequestPayload UploadFileRequest request) {
        File file = new File(request.getFilename());
        if (!file.exists()) {
            throw new RuntimeException("Archivo no encontrado: " + request.getFilename());
        }

        // DataHandler: convierte el archivo en binario para SOAP
        FileDataSource fds = new FileDataSource(file);
        var handler = new DataHandler(fds);

        request.setFileData(handler);


        var response = new UploadFileResponse();
        try (InputStream in = request.getFileData().getInputStream()) {
            byte[] bytes = in.readAllBytes();
            var success = iFileServices.uploadFile(request.getFilename(), bytes);

            response.setSuccess(success);
            response.setMessage(success ? "Archivo subido correctamente." : "Error al subir el archivo.");

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Internal Error: " + e.getMessage());
        }

        return response;
    }
}
