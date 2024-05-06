package org.mycompany.servimark.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface ServiceExternalAPI {
    ResponseEntity<Map<String,Object>> saveService(ServiceDTO serviceDTO);
}
