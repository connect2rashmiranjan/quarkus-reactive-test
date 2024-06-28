package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Doctor;

@ApplicationScoped
public class DoctorRepo implements PanacheRepositoryBase<Doctor, Long> {


}
