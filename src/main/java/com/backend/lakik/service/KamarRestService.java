package com.backend.lakik.service;

import com.backend.lakik.model.KamarModel;
import com.backend.lakik.model.UserModel;

public interface KamarRestService {
    KamarModel getKamar(Long idKamar);

    KamarModel createKamar(KamarModel kamar);

    KamarModel updateKamar(Long idKamar, KamarModel kamar);

    Boolean verifyKamar(UserModel user, String noKamar); 
}
