package ar.com.ada.api.adaairlines.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.adaairlines.entities.*;
import ar.com.ada.api.adaairlines.repos.*;

@Service
public class StaffService {

    @Autowired
    StaffRepository repo;

    public void crearStaff(Staff staff) {
        repo.save(staff);
    }
}