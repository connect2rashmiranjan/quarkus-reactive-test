package org.acme.service;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.DoctorRepo;
import org.acme.entity.Doctor;

import java.util.List;

@ApplicationScoped
public class DoctoreService {
    @Inject
    DoctorRepo doctorRepo;

    @WithTransaction
    public Uni<List<Doctor>> listAll() {
        return doctorRepo.listAll();
    }

    @WithTransaction
    public Uni<Doctor> getDoctor(Long id) {
        return doctorRepo.findById(id);
    }

    @WithTransaction
    public Uni<Doctor> addDoctor(Doctor doctor) {

        return doctorRepo.persist(doctor);
    }
    @WithTransaction
    public Uni<Boolean> deleteDoctor(Long id){
       // return doctor.delete();
        return doctorRepo.deleteById(id);
    }

}
