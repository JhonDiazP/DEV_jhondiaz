package org.mycompany.servimark.service.management;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.mycompany.servimark.auditoria.AuditoriaDTO;
import org.mycompany.servimark.auditoria.AuditoriaInternalAPI;
import org.mycompany.servimark.auditoria.model.Auditoria;
import org.mycompany.servimark.service.ServiceDTO;
import org.mycompany.servimark.service.ServiceExternalAPI;
import org.mycompany.servimark.service.ServiceInternalAPI;
import org.mycompany.servimark.service.mapper.ServiceMapper;
import org.mycompany.servimark.service.model.UserService;
import org.mycompany.servimark.service.repository.ServiceRepository;
import org.mycompany.servimark.service.repository.UserServiceRepository;
import org.mycompany.servimark.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ServiceManagement implements ServiceInternalAPI, ServiceExternalAPI {

    private final ServiceRepository serviceRepository;
    private final UserServiceRepository userServiceRepository;
    private final ServiceMapper serviceMapper;
    private final AuditoriaInternalAPI auditoriaInternalAPI;
    private int contErrors = 0;

    public ServiceManagement(ServiceRepository serviceRepository, ServiceMapper serviceMapper, UserServiceRepository userServiceRepository, AuditoriaInternalAPI auditoriaInternalAPI) {
        this.serviceMapper = serviceMapper;
        this.serviceRepository = serviceRepository;
        this.userServiceRepository = userServiceRepository;
        this.auditoriaInternalAPI = auditoriaInternalAPI;
    }

    public ResponseEntity<Map<String, Object>> getServices() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("data", serviceRepository.findAll());
        map.put("status", "true");
        map.put("message", "Servicios obtenidos exitosamente");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String,Object>> saveService(ServiceDTO serviceDTO) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            UUID serviceId = UUID.randomUUID();
            //Create entity service
            org.mycompany.servimark.service.model.Service service = serviceMapper.serviceDTOToService(serviceDTO);
            service.setId(serviceId.toString());
            //Save entity service
            ServiceDTO save = serviceMapper.serviceToServiceDTO(serviceRepository.save(service));
            //Create entity user_service
            User user = serviceDTO.user();
            UserService userService = new UserService();
            userService.setService(service);
            userService.setUser(user);
            //Save entity user_service
            userServiceRepository.save(userService);
            //Response
            map.put("data", save);
            map.put("status", "true");
            map.put("message", "Servicio guardado exitosamente");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (Exception e) {
            contErrors++;
            if(contErrors >= 3){
                AuditoriaDTO auditoria = new AuditoriaDTO(null, e.getMessage(), null);
                try {
                    auditoriaInternalAPI.createAuditoria(auditoria);
                } catch (Exception ex) {
                    auditoriaInternalAPI.createAuditoriaToTxt(auditoria);
                }
            }
        }
        map.put("status", "false");
        map.put("message", "Error al guardar el servicio");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
