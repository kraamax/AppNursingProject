package com.example.registro.Services;

import com.example.registro.Models.Paciente;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PacienteService {
    @POST("api/paciente")
    Observable<String> registerPaciente(@Body Paciente paciente);

    @GET("api/paciente")
    Observable<Paciente[]> getPacientes();


}
