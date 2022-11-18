package com.backend.lakik.service;

import com.backend.lakik.model.KamarModel;
import com.backend.lakik.repository.KamarDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class KamarRestServiceImpl implements KamarRestService {
    @Autowired
    private UserRestService kosService;
    @Autowired
    private KamarDb kamarRepository;

    @Override
    public KamarModel createKamar(String username, KamarModel kamar) {
        var kos = kosService.getUserByUsername(username);
        kamar.setUserModel(kos);
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
}

