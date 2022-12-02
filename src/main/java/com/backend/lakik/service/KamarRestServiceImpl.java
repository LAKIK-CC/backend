package com.backend.lakik.service;

import com.backend.lakik.model.KamarModel;
import com.backend.lakik.model.UserModel;
import com.backend.lakik.repository.KamarDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class KamarRestServiceImpl implements KamarRestService {

    @Autowired
    private KamarDb kamarRepository;

    @Override
    public KamarModel getKamar(Long idKamar) {
        return kamarRepository.findById(idKamar).orElseGet(() -> null);
    }

    @Override
    public KamarModel createKamar(KamarModel kamar) {
        return kamarRepository.save(kamar);
    }

    @Override
    public KamarModel updateKamar(Long idKamar, KamarModel kamar) {
        var kamarFoundOptional = kamarRepository.findById(idKamar);
        if(kamarFoundOptional.isEmpty()) {
            throw new IllegalStateException("Kamar not found");
        }

        var kamarFound = kamarFoundOptional.get();
        kamarFound.setNoKamar(kamar.getNoKamar());
        kamarFound.setWcDalam(kamar.isWcDalam());
        kamarFound.setAc(kamar.isAc());
        kamarFound.setSpringBed(kamar.isSpringBed());
        kamarFound.setListrik(kamar.isListrik());
        kamarFound.setTersedia(kamar.isTersedia());
        kamarFound.setKeterangan(kamar.getKeterangan());

        return kamarFound;
    }

    @Override
    public Boolean verifyKamar(UserModel user, String noKamar) {
        for (KamarModel kamar : user.getListKamar()) {
            if (kamar.getNoKamar().equals(noKamar)) {
                return false;
            }
        }
        return true;
    }
}

