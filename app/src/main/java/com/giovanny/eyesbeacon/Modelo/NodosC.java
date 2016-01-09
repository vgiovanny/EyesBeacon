package com.giovanny.eyesbeacon.Modelo;

/**
 * Created by giovanny on 03/01/16.
 */

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by giovanny on 26/11/15.
 */
public class NodosC {

    private ArrayList<Nodo>nodos;

    private ArrayList<Nodo> ruta;
    private boolean termino=false;

    private ArrayList<Arista> camino;

    private int anterior;


    private ArrayList<String> tarea;

    public ArrayList<String> getTarea() {
        return tarea;
    }

    public void setTarea(ArrayList<String> tarea) {
        this.tarea = tarea;
    }

    public NodosC(ArrayList<Nodo>nodos){
        this.nodos=nodos;
        ruta=new ArrayList<Nodo>();
    }

    public Nodo getNodo(int i){
        return nodos.get(i);
    }

    public int getNodosCount(){
        return nodos.size();
    }


    public ArrayList<Arista> getCamino() {
        return camino;
    }

    /*public void IniciaBusqueda(Nodo origen,String MAC){
        ruta.clear();
        setBanderas();
        ruta.add(origen);
        termino=false;
        anterior=origen.getId();
        obtieneCamino(origen, MAC);
        obtengoPasos();
    }*/
    public void IniciaBusqueda(Nodo origen,int id){
        ruta.clear();
        setBanderas();
        ruta.add(origen);
        termino=false;
        anterior=origen.getId();
        obtieneCamino(origen, id);
        obtengoPasos();
    }

    public void setBanderas(){
        for(int i=0;i<nodos.size();i++)
            nodos.get(i).visitado=false;
    }


    public void obtieneCamino(Nodo origen,int id){
        if(!termino) {
            origen.visitado=true;
            if (origen.getId()==id) {
                Log.d("Caminos", "_ENCONTRE!" + origen.getName());
                termino = true;
            } else {
                Nodo[] hijos = origen.getHjos();
                for (int i = 0; i < origen.getHjos().length; i++) {

                    if (!hijos[i].visitado) {
                        ruta.add(hijos[i]);
                        obtieneCamino(hijos[i], id);
                        if(termino)
                            break;
                        ruta.remove(ruta.size() - 1);
                    }
                }
            }
        }
        return;
    }


    public String pRutaDEF(){
        String text="";
        for(int i=0;i<ruta.size();i++){
            text+=ruta.get(i).getId()+",";
        }
        Log.d("Caminos",text);
        return text;
    }

    public void obtengoAristas(){
        camino = new ArrayList<>();
        Log.d("CAAMINO",pRutaDEF());
        for(int i=0;i<ruta.size()-1;i++){
            Nodo act =ruta.get(i);
            Nodo[] hijo = act.getHjos();
            /*
            for (int j = 0; j < act.getHjos().length; j++) {
                if(hijo[j].getMAC().equals(ruta.get(i+1).getMAC())){
                    camino.add(new Arista(act.getCx(),act.getCy(),hijo[j].getCx(),hijo[j].getCy()));
                    break;
                }
            }
            */
        }
    }

    public void obtengoPasos(){
        tarea = new ArrayList<>();
        Log.d("ruta",pRutaDEF());
        String []giro;
        for(int i=0;i<ruta.size()-1;i++){
            Nodo act =ruta.get(i);
            Nodo[] hijo = act.getHjos();

            for (int j = 0; j < act.getHjos().length; j++) {
                //if(hijo[j].getMAC().equals(ruta.get(i+1).getMAC())){
                if(hijo[j].getId()==ruta.get(i+1).getId()){

                    if(act.getGiro()!=null){
                        for(int k=0;k<act.getGiro().length;k++){///busco para giros
                            giro=act.getGiro()[k].split(" ");

                            if(giro[0].equals(anterior+"") && giro[2].equals(hijo[j].getId()+"")){
                                //if(giro[1].equals("90"))
                                Log.d("ruta",giro[0]+"/"+anterior+"_"+giro[2]+"/"+hijo[j].getId());
                                if(giro[1].charAt(0)=='-')
                                    tarea.add("Gira " + giro[1].substring(1) +" D");
                                else
                                    tarea.add("Gira " + giro[1] +" I");
                                break;
                            }
                        }
                    }
                    tarea.add("Dar " + act.getPasos()[j] + " pasos");
                    //camino.add(new Arista(act.getCx(),act.getCy(),hijo[j].getCx(),hijo[j].getCy()));
                    break;
                }
            }
            anterior=act.getId();
        }
    }

    public String tareaspe(){
        String tar="";
        for(int i=0;i<tarea.size();i++){
            tar+=tarea.get(i)+"??";
        }
        return tar;
    }
}