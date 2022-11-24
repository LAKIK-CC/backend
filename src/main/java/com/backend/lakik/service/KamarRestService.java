package com.backend.lakik.service;

import com.backend.lakik.model.KamarModel;

public interface KamarRestService {
    KamarModel getKamar(Long idKamar);

    KamarModel createKamar(KamarModel kamar);

    KamarModel updateKamar(Long idKamar, KamarModel kamar);

    void deleteKamar(Long idKamar);
}
