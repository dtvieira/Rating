package com.dantv.rating.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class RateSPManager {

    //variaveis para calculo de vezes que o app foi inicializado
    private static final String LAUNCH_TIMES_KEY = "launch_times_key";
    private static final int LAUNCH_TIMES = 3;

    //variaveis para verificar ha quanto tempo o app foi inicializado pela primeira vez
    private static final String TIME_KEY = "time_key";
    private static final int DAYS_DELAY = 3 * (24 * 60 * 60 * 1000);

    //variavel para nao perguntar novamente se o usuario quer avaliar ou n
    private static final String NEVER_ASK_KEY = "never_ask_key";

    //receber o valor do sharedPreferences
    private static SharedPreferences getSP(Context c){
        return c.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    //sempre invocado no onCreate da activity principal, para contabilizar a abertura do app
    public static void updateLaunchTimes(Context c){
        SharedPreferences sp = getSP(c);
        sp.edit().putInt(LAUNCH_TIMES_KEY, 0).apply();
    }

    //recebe o Bundle para verificar se eh a primeira vez que o app eh aberto
    public static void updateLaunchTimes(Context c, Bundle savedInstanceState){
        //se houver bundle, o app ja estava aberto antes, entao apenas retorna
        if (savedInstanceState != null){
            return;
        }
        //se n houver bundle, cria o contador no sharedePreferences e adiciona 1
        SharedPreferences sp = getSP(c);
        int launchTimes = sp.getInt(LAUNCH_TIMES_KEY, 0);
        sp.edit().putInt(LAUNCH_TIMES_KEY, launchTimes + 1).apply();
    }

    //reset do contador, para os casos do usuario nao fazer uma avaliacao com 4 ou 5 estrelas ou escolher fornecer a avaliacao mais tarde
    private static boolean isLaunchTimesValid(Context c){
        SharedPreferences sp = getSP(c);
        int launchTimes = sp.getInt(LAUNCH_TIMES_KEY, 0);

        return launchTimes > 0 && launchTimes % LAUNCH_TIMES == 0;
    }

    //atualiza o contador de quanto tempo faz que o app foi inicializado pela primeira vez
    public static void updateTime(Context c){
        SharedPreferences sp = getSP(c);
        sp.edit()
                .putLong(TIME_KEY, System.currentTimeMillis()+ DAYS_DELAY)
                .apply();
    }

    public static boolean isTimeValid(Context c){
        SharedPreferences sp = getSP(c);
        Long time = sp.getLong(TIME_KEY, 0);

        //se o time == 0, eh a primeira vez q o app esta inicializando, se n, vai fazer o update do tempo,
        // o time vai receber o valor atualizado e o metodo vai retornar esse valor
        if (time == 0){
            updateTime(c);
            time = sp.getLong(TIME_KEY, 0);
        }

        return time < System.currentTimeMillis();
    }

    //caso o usuario escolha q n seja perguntado novamente, altera a chave pra true
    public static void neverAskAgain(Context c){
        SharedPreferences sp = getSP(c);
        sp.edit().putBoolean(NEVER_ASK_KEY, true).apply();
    }

    //caso contrario, retorna a chave como false, e ira perguntar novamente uma hora
    public static boolean isNeverAskAgain(Context c){
        SharedPreferences sp = getSP(c);
        return sp.getBoolean(NEVER_ASK_KEY, false);
    }

    //para verificar pode ser mostrado, se o eh tempo e o momento certo para mostrar o dialog
    public static boolean canShowDialog (Context c){
        return !isNeverAskAgain(c)
                && (isTimeValid(c)
                        || isLaunchTimesValid(c));
    }
}
