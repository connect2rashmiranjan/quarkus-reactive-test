package org.acme.controller;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Doctor;
import org.acme.service.DoctoreService;


import java.util.List;

@Path("/doctor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorController {
    @Inject
    DoctoreService service;

    @GET

    public Uni<List<Doctor>> getAllDoctors() {
        return service.listAll();
    }
    @POST
    public Uni<Response> createDoctor(Doctor doctor) {
        //return service.addDoctor(doctor);
        return service.addDoctor(doctor)
                .onItem().transform(createdDoctor ->
                        Response.status(Response.Status.CREATED).entity("Doctor created successfully: " + createdDoctor.toString()).build()
                )
                .onFailure().recoverWithItem(error ->
                        Response.status(Response.Status.BAD_REQUEST).entity("Error creating doctor: " + error.getMessage()).build()
                );
    }
//all the db operetion
    //test cases for all method of service using mockito
    //

    @GET
    @Path("/{id}")
    public Uni<Doctor> getDoctor(@PathParam("id") Long id) {
        return service.getDoctor(id);
    }
    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteDoctorById(@PathParam("id") Long id){
        return service.deleteDoctor(id);
    }
}
