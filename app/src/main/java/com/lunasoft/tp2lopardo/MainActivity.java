package com.lunasoft.tp2lopardo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Toast;

import com.lunasoft.tp2lopardo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel vm;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);

        /*
            Lógica para elegir el edit text correcto según qué radio button sea elegido.
         */
        binding.btConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount;
                if (binding.rbDolares.isChecked()) {
                    amount = Double.parseDouble(binding.etDolares.getText().toString());
                } else {
                    amount = Double.parseDouble(binding.etEuros.getText().toString());
                }
                vm.convertir(amount, binding.rbDolares.isChecked());
            }
        });

        /*
            Manejo de radio buttons:
            - Si el RB de dolar esta tildado, se destilda el de euro y se deshabilita el edit text
            para ingresar euros. De la misma forma, si el RB de euro está tildado, se deshabilita
            el ET de dolar.
            - También se limpian los campos que tienen datos (edit texts y el de resultado) cuando
            se elige un RB diferente)
         */
        binding.rbDolares.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.etDolares.setEnabled(true);
                binding.etEuros.setEnabled(false);
                binding.rbEuros.setChecked(false);
                binding.etEuros.setText("");
                binding.tvResultado.setText("");
            }
        });

        binding.rbEuros.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.etDolares.setEnabled(false);
                binding.etEuros.setEnabled(true);
                binding.rbDolares.setChecked(false);
                binding.etDolares.setText("");
                binding.tvResultado.setText("");
            }
        });

        // Busqué un filtro para no permitir ingresar letras en los ET.
        InputFilter numericInputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        binding.etDolares.setFilters(new InputFilter[]{numericInputFilter});
        binding.etEuros.setFilters(new InputFilter[]{numericInputFilter});

        vm.getResultLiveData().observe(this, result -> {
            binding.tvResultado.setText(result);
        });
    }
}