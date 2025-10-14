package com.sistemadenuncias.sistemadenuncias.service;

import com.sistemadenuncias.sistemadenuncias.models.Denuncia;
import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.models.StatusDenuncia;
import com.sistemadenuncias.sistemadenuncias.repository.DenunciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.ReparticaoRepository;
import com.sistemadenuncias.sistemadenuncias.services.DenunciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DenunciaServiceTest {

    private DenunciaRepository denunciaRepository;
    private DenunciaService denunciaService;
    private ProvinciaRepository provinciaRepository;
    private ReparticaoRepository reparticaoRepository;

    @BeforeEach
    void setUp() {
        denunciaRepository = mock(DenunciaRepository.class);
        provinciaRepository = mock(ProvinciaRepository.class);
        reparticaoRepository = mock(ReparticaoRepository.class);

        denunciaService = new DenunciaService(denunciaRepository, provinciaRepository, reparticaoRepository);
    }

    @Test
    void deveBuscarDenunciaPorProtocolo() {
        // Arrange
        String protocolo = "DNC-GA-20251014-ABC123";
        Denuncia denuncia = new Denuncia();
        denuncia.setId(1L);
        denuncia.setProtocolo(protocolo);
        denuncia.setDescricao("Teste de denúncia");
        denuncia.setStatus(StatusDenuncia.PENDENTE);
        denuncia.setDataOcorrencia(LocalDateTime.now());
        denuncia.setDataDenuncia(LocalDateTime.now());
        denuncia.setProvincia(new Provincias());

        when(denunciaRepository.findByProtocolo(protocolo)).thenReturn(Optional.of(denuncia));

        // Act
        Denuncia resultado = denunciaService.buscarPorProtocolo(protocolo);

        // Assert
        assertNotNull(resultado);
        assertEquals(protocolo, resultado.getProtocolo());
        verify(denunciaRepository, times(1)).findByProtocolo(protocolo);
    }

    @Test
    void deveLancarExcecaoSeProtocoloNaoForEncontrado() {
        // Arrange
        String protocolo = "DNC-XX-00000000-XXXXXX";
        when(denunciaRepository.findByProtocolo(protocolo)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            denunciaService.buscarPorProtocolo(protocolo);
        });

        assertEquals("Denúncia não encontrada", ex.getMessage());
        verify(denunciaRepository, times(1)).findByProtocolo(protocolo);
    }
}
