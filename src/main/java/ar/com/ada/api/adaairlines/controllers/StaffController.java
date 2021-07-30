/*package ar.com.ada.api.adaairlines.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.*;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.services.*;

@RestController
public class StaffController {

    @Autowired
    StaffService staffService;

    @PostMapping("api/staffs")
    public ResponseEntity<GenericResponse> crearStaff(@RequestBody Staff staff) {
        
        staffService.crearStaff(staff);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.message = "Staff creado con exito";
        r.id = staff.getStaffId();

        return ResponseEntity.ok(r);
    }    

    @GetMapping("/api/staffs/{id}")
    public ResponseEntity<Staff> buscarPorIdStaff(@PathVariable Integer id) {
        Staff staff = staffService.buscarStaffPorId(id);
        if (staff == null)

            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(staff);
    }

   
    @GetMapping("/api/staffs")
    public ResponseEntity<List<Staff>> traerStaffs() {
        final List<Staff> lista = staffService.traerStaffs();
        return ResponseEntity.ok(lista);
    }
    
        
    
}
*/