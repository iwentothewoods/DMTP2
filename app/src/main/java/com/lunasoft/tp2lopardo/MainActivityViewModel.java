package com.lunasoft.tp2lopardo;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.DecimalFormat;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> resultLiveData ;

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

    //Para redondear lo más específicamente posible
    private final DecimalFormat df = new DecimalFormat("0.00");

    /*
    Saqué un valor de conversión de internet. El método recibe un boolean "esDolar" si está
    el radio button de Dolar a Euro checkeado. Dependiendo si es dolar o euro, divide o multiplica
    por el valor de conversión seteado.
     */
    public void convertir(Double cantidad, boolean esDolar) {
        double conversion = 0.92;
        double cantidadFinal = esDolar ? cantidad * conversion : cantidad / conversion;
        double cf = Double.parseDouble(df.format(cantidadFinal));

        if (esDolar){
            resultLiveData.setValue(cantidad + " Dolares = " + cf + " Euros.");
        } else {
            resultLiveData.setValue(cantidad + " Euros = " + cf + " Dolares.");
        }

    }
}

