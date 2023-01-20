package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Election;

import com.kalata.spring.login.repository.ElectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionServiceImpl(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Override
    public List<Election> findAll() {
        return electionRepository.findAll();
    }

    @Override
    public Election findById(Long id) {
        return electionRepository.findById(id).orElse(null);
    }

    @Override
    public Election save(Election election) {
        election.setNbrvote(0);
        return electionRepository.save(election);
    }

        @Override
        public String delete(@PathVariable Long id) {
            electionRepository.deleteById(id);
            return"Election supprimé";

        }
}
