package com.backend.lakik.service;

import com.backend.lakik.model.KamarModel;

public interface KamarRestService {
    KamarModel createKamar(String username, KamarModel kamar);
    KamarModel updateKamar(Long idKamar, KamarModel kamar);
    List<KamarModel> readKamar(String username);
    void deleteKamar(Long idKamar);
}
