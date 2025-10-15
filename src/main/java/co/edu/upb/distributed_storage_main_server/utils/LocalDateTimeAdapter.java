package co.edu.upb.distributed_storage_main_server.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String s) throws Exception {
        return (s == null || s.isEmpty()) ? null : LocalDateTime.parse(s, FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime t) throws Exception {
        return (t == null) ? null : t.format(FORMATTER);
    }
}
