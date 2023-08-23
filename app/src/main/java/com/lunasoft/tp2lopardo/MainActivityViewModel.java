package com.lunasoft.tp2lopardo;

import android.app.Application;
import android.content.Context;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.DecimalFormat;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> resultLiveData;
    private MutableLiveData<Boolean> eurosET;
    private MutableLiveData<Boolean> dolaresET;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    // Resultado que se actualiza cuando se aprieta el botón "Convertir"
    public LiveData<String> getResultLiveData() {
        if (resultLiveData == null) {
            resultLiveData= new MutableLiveData<String>();
        }
        return resultLiveData;
    }
    public LiveData<Boolean> getEurosET() {
        if (eurosET == null) {
            eurosET= new MutableLiveData<Boolean>();
        }
        return eurosET;
    }
    public LiveData<Boolean> getDolaresET() {
        if (dolaresET == null) {
            dolaresET= new MutableLiveData<Boolean>();
        }
        return dolaresET;
    }

    //Para redondear lo más específicamente posible
    private final DecimalFormat df = new DecimalFormat("0.00");

    /* El método recibe un boolean "esDolar" si está el radio button de Dolar a Euro checkeado.
    Dependiendo si es dolar o euro, divide o multiplica por el valor de conversión seteado. */
    public void convertir(String dol, String eur, boolean esDolar) {
        double conversion = 0.92;

        if (esDolar){
            double cantidadFinal = Double.parseDouble(dol) * conversion;
            double cf = Double.parseDouble(df.format(cantidadFinal));
            resultLiveData.setValue(dol + " Dolares = " + cf + " Euros.");
        } else {
            double cantidadFinal = Double.parseDouble(eur) / conversion;
            double cf = Double.parseDouble(df.format(cantidadFinal));
            resultLiveData.setValue(eur + " Euros = " + cf + " Dolares.");
        }

    }

    // Recibe true si el RB de dolares está checkeado y setea falso para el Edit Text de euros
    // y verdadero para el de dolares
    public void setDolaresChecked(boolean isChecked) {
        eurosET.setValue(!isChecked);
        dolaresET.setValue(isChecked);
    }
    // Acá hace lo mismo con el de euros, si está checkeado, setea falso el de dolares
    public void setEurosChecked(boolean isChecked) {
        eurosET.setValue(isChecked);
        dolaresET.setValue(!isChecked);
    }



}

