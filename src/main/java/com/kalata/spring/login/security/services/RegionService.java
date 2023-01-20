package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Region;
import com.kalata.spring.login.payload.response.MessageResponse;

import java.util.List;

public interface RegionService {
    MessageResponse creer(Region region);
    List<Region> lire();
    Region modifier(Long id, Region region);
    String supprimer(Long id);
}
