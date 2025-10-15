package co.edu.upb.distributed_storage_main_server.utils;

public enum Singleton {
    INSTANCE;

    private static final String BASE_XSD_LOCATION = "xml/schema/";

    public static final String FILE_XSD_LOCATION = BASE_XSD_LOCATION + "files.xsd";
    public static final String FILE_NAMESPACE_URI = "http://example.com/files";
}
