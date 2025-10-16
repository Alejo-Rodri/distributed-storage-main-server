package co.edu.upb.distributed_storage_main_server.grpc.client.file;

public interface IFileClient {
    boolean uploadFile(String filename, byte[] bytes);
}
