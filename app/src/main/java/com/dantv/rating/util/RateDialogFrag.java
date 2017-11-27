package com.dantv.rating.util;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;

import com.dantv.rating.R;

public class RateDialogFrag extends DialogFragment implements RatingBar.OnRatingBarChangeListener, View.OnClickListener{

    public static final String KEY = "fragment_rate";

    //Necessario para remover a barra de titulo em versoes anteriores a versao 5 do android
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    //relaciona os id do interface com as variaveis do codigo
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_rate_dialog, container);

        RatingBar rbStars = (RatingBar) view.findViewById(R.id.rb_stars);
        rbStars.setOnRatingBarChangeListener(this);

        View bt = view.findViewById(R.id.bt_never);
        bt.setOnClickListener(this);

        bt = view.findViewById(R.id.bt_later);
        bt.setOnClickListener(this);

        return view;
    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        //caso o suario escolha 4 ou mais estrelas, nao sera perguntado novamente
        if(rating >= 4){
            RateDialogManager.showRateDialogPlayStore(getActivity());
            RateSPManager.neverAskAgain(getActivity());
            dismiss();

        }
        //caso contrario, resetamos os contadores de tempo e vezes q o app foi inicializado para podermos perguntar novamente
        else if (rating > 0){
            RateDialogManager.showRateDialogFeedback(getActivity(),rating);
            RateSPManager.updateTime(getActivity());
            RateSPManager.updateLaunchTimes(getActivity());
            dismiss();
        }
    }


    @Override
    public void onClick(View view) {
        //caso o botao de mais tarde seja pressionado, resetamos os contadores
        if (view.getId() == R.id.bt_later){
            RateSPManager.updateTime(getActivity());
            RateSPManager.updateLaunchTimes(getActivity());
        }
        //caso a opcao nao obrigado seja seleiconada, o usuario nao sera mais questionado
        else {
            RateSPManager.neverAskAgain(getActivity());
        }
        dismiss();
    }
}