package com.dantv.rating.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dantv.rating.R;

public class RateDialogFeedbackFrag extends RateDialogFrag{

    //variaveis do sharedPreferences para manter o numero de estrelas q sera enviado por email tb
    private static final String RATING_KEY = "rating";
    private float rating;

    //para receber o texto do editText
    private EditText etFeedback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_rate_dialog_feedback, container);

        //se a instancia salva for num, ele vai pegar o valor carregado com o nome de RATING_KEY
        if (savedInstanceState != null){
            rating = savedInstanceState.getFloat(RATING_KEY);
        }

        //relaciona o editText com a variavel editText
        etFeedback = (EditText) view.findViewById(R.id.et_feedback);

        View bt = view.findViewById(R.id.bt_no);
        bt.setOnClickListener(this);

        bt = view.findViewById(R.id.bt_send);
        bt.setOnClickListener(this);

        return view;
    }

    public void setRating(float rating){
        this.rating = rating;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putFloat(RATING_KEY, rating);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        String feedback = etFeedback.getText().toString();

        //se o botao de send for pressionado, montara o email com as informacoes
        if(view.getId() == R.id.bt_send && feedback.length() > 0){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"dan_tv@hotmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Avaliacao aplicativo");
            intent.putExtra(Intent.EXTRA_TEXT,"Estrelas: " +rating+ "\n\nAvaliacao: " + feedback);
            getActivity().startActivity(Intent.createChooser(intent, "Enviar e-mail"));
        }
        else if (view.getId() == R.id.bt_send){
            Toast.makeText(getActivity(), "Entre com o feedback",Toast.LENGTH_SHORT).show();
            return;
        }
        dismiss();
    }
}

//TESTE


// teste 2
