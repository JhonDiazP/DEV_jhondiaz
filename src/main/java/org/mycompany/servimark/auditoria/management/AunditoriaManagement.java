package org.mycompany.servimark.auditoria.management;

import org.mycompany.servimark.auditoria.AuditoriaExternalAPI;
import org.mycompany.servimark.auditoria.AuditoriaInternalAPI;
import org.mycompany.servimark.auditoria.mapper.AuditoriaMapper;
import org.mycompany.servimark.auditoria.model.Auditoria;
import org.mycompany.servimark.auditoria.repository.AuditoriaRepository;

public class AunditoriaManagement implements AuditoriaExternalAPI, AuditoriaInternalAPI {

    private final AuditoriaRepository auditoriaRepository;
    private final AuditoriaMapper auditoriaMapper;

    public AunditoriaManagement(AuditoriaRepository auditoriaRepository, AuditoriaMapper auditoriaMapper) {
        this.auditoriaMapper = auditoriaMapper;
        this.auditoriaRepository = auditoriaRepository;
    }
    
    @Override
    public Auditoria createAuditoria(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }
}
