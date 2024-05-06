package org.mycompany.servimark.service.management;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

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

    public ServiceManagement(ServiceRepository serviceRepository, ServiceMapper serviceMapper, UserServiceRepository userServiceRepository) {
        this.serviceMapper = serviceMapper;
        this.serviceRepository = serviceRepository;
        this.userServiceRepository = userServiceRepository;
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
    }
}
