// MainActivity
package com.lunasoft.tp2lopardo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.lunasoft.tp2lopardo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel vm;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);

        //El botón de convertir empieza desactivado para que no se rompa antes de elegir opciones
        binding.btConvertir.setEnabled(false);

        // Envía información de los números ingresados y el estado de los radio buttons
        binding.btConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.convertir(
                        binding.etDolares.getText().toString(),
                        binding.etEuros.getText().toString(),
                        binding.rbDolares.isChecked());
            }
        });

        // Observa el cambio en el estado del RB rbDolares
        binding.rbDolares.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                vm.setDolaresChecked(isChecked);
            }
        });

        // Observa el cambio en el estado del RadioButton rbEuros
        binding.rbEuros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                vm.setEurosChecked(isChecked);
            }
        });

        // Observa el cambio en el estado de los ET desde el viewmodel, si devuelve verdadero,
        // activa el campo para editar
        vm.getEurosET().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEnabled) {
                binding.etEuros.setEnabled(isEnabled);
                binding.btConvertir.setEnabled(true);
            }
        });

        vm.getDolaresET().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEnabled) {
                binding.etDolares.setEnabled(isEnabled);
                binding.btConvertir.setEnabled(true);
            }
        });

        vm.getResultLiveData().observe(this, result -> {
            binding.tvResultado.setText(result);
        });

    }
}
