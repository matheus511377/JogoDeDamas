package com.matheus.jogodedamas.Classes;

import android.icu.text.MessagePattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 07/05/2017.
 */

public class Contabilista {
    private static List<Partida> partidaList = new ArrayList<>();

    public static void setPartida(Partida partida){
        partidaList.add(partida);

    }

    public static List<Partida> getPartidaList() {
        return partidaList;
    }
}
