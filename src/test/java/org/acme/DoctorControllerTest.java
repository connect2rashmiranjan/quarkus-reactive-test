package org.acme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import jakarta.ws.rs.core.Response;
import org.acme.controller.DoctorController;
import org.acme.entity.Doctor;
import org.acme.service.DoctoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.smallrye.mutiny.Uni;

@ExtendWith(MockitoExtension.class) // Use MockitoExtension for JUnit 5
public class DoctorControllerTest {

    @Mock
    private DoctoreService mockService;

    @InjectMocks
    private DoctorController doctorController;

    @Test
    public void testGetAllDoctors() {
        // Prepare mock behavior
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        when(mockService.listAll()).thenReturn(Uni.createFrom().item(doctors));

        // Call the method under test
        Uni<List<Doctor>> result = doctorController.getAllDoctors();

        // Verify the result
        List<Doctor> actualDoctors = result.await().indefinitely();
        assertThat(actualDoctors).isEqualTo(doctors);
    }

    @Test
    public void testCreateDoctor_Success() {
        // Prepare mock behavior
        Doctor doctor = new Doctor();
        when(mockService.addDoctor(any(Doctor.class))).thenReturn(Uni.createFrom().item(doctor));

        // Call the method under test
        jakarta.ws.rs.core.Response response = doctorController.createDoctor(doctor).await().indefinitely();

        // Verify the response status
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(response.getEntity().toString()).contains("Doctor created successfully");
    }

    @Test
    public void testCreateDoctor_Failure() {
        // Prepare mock behavior for failure
        Doctor doctor = new Doctor();
        when(mockService.addDoctor(any(Doctor.class))).thenReturn(Uni.createFrom().failure(new RuntimeException("Simulated error")));

        // Call the method under test
        jakarta.ws.rs.core.Response response = doctorController.createDoctor(doctor).await().indefinitely();

        // Verify the response status
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(response.getEntity().toString()).contains("Error creating doctor");
    }
}
