package com.example.registro;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.registro.Models.Paciente;
import com.example.registro.Services.PacienteService;
import com.example.registro.Services.RetrofitClient;
import dmax.dialog.SpotsDialog;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
public class MainActivity extends AppCompatActivity {

    EditText txtId, txtNombres,txtApellidos;
    Button btnRegistrar;
    PacienteService pacienteService;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtId=findViewById(R.id.txtId);
        txtNombres=findViewById(R.id.txtNombres);
        txtApellidos=findViewById(R.id.txtApellidos);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_person_add_white_24dp);
        pacienteService= RetrofitClient.getInstance().create(PacienteService.class);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog=new SpotsDialog.Builder()
                        .setContext(MainActivity.this)
                        .setMessage("Cargando")
                        .build();
                alertDialog.show();
                Paciente paciente= MapUser();
                compositeDisposable.add(
                        pacienteService.registerPaciente(paciente)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                );
            }
        });
    }
    private  Paciente MapUser(){
        Paciente paciente= new Paciente();
        paciente.setNombres(txtNombres.getText().toString());
        paciente.setIdPaciente(Integer.parseInt(txtId.getText().toString()));
        paciente.setApellidos(txtApellidos.getText().toString());
        return paciente;
    }
}
