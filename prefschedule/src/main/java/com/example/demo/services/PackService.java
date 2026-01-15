package com.example.demo.services;

import com.example.demo.model.Pack;
import com.example.demo.repository.PackRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PackService {

    private final PackRepository packRepository;

    public PackService(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }

    public Pack getPackById(Long id) {
        return packRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pack not found with id: " + id));
    }

    public Pack createPack(Pack pack) {
        return packRepository.save(pack);
    }

    public Pack updatePack(Long id, Pack packDetails) {
        Pack pack = getPackById(id);
        
        pack.setName(packDetails.getName());
        pack.setYear(packDetails.getYear());
        pack.setSemester(packDetails.getSemester());
        
        return packRepository.save(pack);
    }

    public void deletePack(Long id) {
        Pack pack = getPackById(id);
        packRepository.delete(pack);
    }
}
