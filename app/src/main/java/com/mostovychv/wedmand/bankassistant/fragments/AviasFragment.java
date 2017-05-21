package com.mostovychv.wedmand.bankassistant.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mostovychv.wedmand.bankassistant.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AviasFragment extends android.app.Fragment implements View.OnClickListener
{
    public class Ceny
    {
        public String price;
        public String type;
        public String region;
        public String regionCode;
    }

    public class Adresy
    {
        public String State;
        public String Address;
    }

    List<String> ids = new ArrayList<>();
    List<String> statesRU = new ArrayList<>();
    List<String> statesUA = new ArrayList<>();
    ArrayAdapter<String> adapter;

    Spinner spinner;
    EditText editTextStreet;
    Button buttonSearch;

    TextView textViewPrices;
    TextView textViewAddresses;

    ArrayList<Ceny> ceny = new ArrayList<>();
    ArrayList<Adresy> adresy = new ArrayList<>();

    class PobierzDane extends AsyncTask<Void, Void, Void>
    {
        private String url;
        private String url2;
        private String answer = "";
        private String answer2 = "";
        private Gson gson = new Gson();		// GSON library object

        PobierzDane(String url, String url2)
        {
            this.url = url;
            this.url2 = url2;
        }

        protected void onPreExecute()
        {

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                URL u1 = new URL(url);
                URLConnection conn = u1.openConnection();
                BufferedInputStream in = new BufferedInputStream(conn.getInputStream());


                byte[] contents = new byte[1024];
                int bytesRead;

                while((bytesRead = in.read(contents)) != -1)
                {
                    answer += new String(contents, 0, bytesRead);
                }

                URL u2 = new URL(url2);
                URLConnection conn2 = u2.openConnection();
                BufferedInputStream in2 = new BufferedInputStream(conn2.getInputStream());

                while((bytesRead = in2.read(contents)) != -1)
                {
                    answer2 += new String(contents, 0, bytesRead);
                }
            }

            catch (IOException e) {
                // TODO: handle exception
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            System.out.println(answer);
            System.out.println(answer2);

            ceny = new ArrayList<>(Arrays.asList(gson.fromJson(answer, Ceny[].class)));
            adresy = new ArrayList<>(Arrays.asList(gson.fromJson(answer2, Adresy[].class)));

            String cenyOdpowiedz = ceny.get(0).region + "\n\n";
            for(int i = 0; i < ceny.size(); i++)
            {
                cenyOdpowiedz = cenyOdpowiedz + ceny.get(i).type + " - " + ceny.get(i).price +"\n";
            }
            textViewPrices.setText(cenyOdpowiedz);

            String adresyOdpowiedz = "";
            for (int i = 0; i<adresy.size(); i++)
            {
                adresyOdpowiedz = adresyOdpowiedz + adresy.get(i).Address +"\n\n";
            }
            textViewAddresses.setText(adresyOdpowiedz);

        }
    }

    public AviasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout)inflater.inflate(R.layout.fragment_avias, container, false);
        spinner = (Spinner)cl.findViewById(R.id.spinner);
        editTextStreet = (EditText)cl.findViewById(R.id.editTextStreet);
        buttonSearch = (Button)cl.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);
        textViewPrices = (TextView)cl.findViewById(R.id.textViewPrices);
        textViewAddresses = (TextView)cl.findViewById(R.id.textViewAddresses);

        ids.add("30");
        ids.add("27");
        ids.add("28");
        ids.add("04");
        ids.add("05");
        ids.add("06");
        ids.add("07");
        ids.add("08");
        ids.add("09");
        ids.add("11");
        ids.add("12");
        ids.add("10");
        ids.add("13");
        ids.add("14");
        ids.add("15");
        ids.add("16");
        ids.add("17");
        ids.add("18");
        ids.add("19");
        ids.add("20");
        ids.add("21");
        ids.add("22");
        ids.add("23");
        ids.add("24");
        ids.add("25");
        ids.add("26");

        statesUA.add("Україна");
        statesUA.add("Вінницька");
        statesUA.add("Волинська");
        statesUA.add("Дніпропетровська");
        statesUA.add("Донецька");
        statesUA.add("Житомирська");
        statesUA.add("Закарпатська");
        statesUA.add("Запоріжська");
        statesUA.add("Івано-Франківська");
        statesUA.add("Київська");
        statesUA.add("Кировоградська");
        statesUA.add("АР Крим");
        statesUA.add("Луганська");
        statesUA.add("Львівська");
        statesUA.add("Миколаївська");
        statesUA.add("Одеська");
        statesUA.add("Полтавська");
        statesUA.add("Рівненська");
        statesUA.add("Сумська");
        statesUA.add("Тернопільська");
        statesUA.add("Харьківська");
        statesUA.add("Херсонська");
        statesUA.add("Хмельницька");
        statesUA.add("Черкаська");
        statesUA.add("Чернігівська");
        statesUA.add("Чернівецька");

        statesRU.add("Украина");
        statesRU.add("Винницкая");
        statesRU.add("Волынская");
        statesRU.add("Днепропетровская");
        statesRU.add("Донецкая");
        statesRU.add("Житомирская");
        statesRU.add("Закарпатская");
        statesRU.add("Запорожская");
        statesRU.add("Ивано-Франковская");
        statesRU.add("Киевская");
        statesRU.add("Кировоградская");
        statesRU.add("Крымская АР");
        statesRU.add("Луганская");
        statesRU.add("Львовская");
        statesRU.add("Николаевская");
        statesRU.add("Одесская");
        statesRU.add("Полтавская");
        statesRU.add("Ровенская");
        statesRU.add("Сумская");
        statesRU.add("Тернопольская");
        statesRU.add("Харьковская");
        statesRU.add("Херсонская");
        statesRU.add("Хмельницкая");
        statesRU.add("Черкасская");
        statesRU.add("Черниговская");
        statesRU.add("Черновицкая");

        final String[] lista = new String[ statesUA.size() ];
        statesUA.toArray(lista);

        adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return cl;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonSearch:
            {
                String regionID = ids.get(spinner.getSelectedItemPosition());
                String regionRU = statesRU.get(spinner.getSelectedItemPosition());
                String address = editTextStreet.getText().toString();
                String url1 = "https://api.privatbank.ua/p24api/aviasstations?json&price&region=" + regionID;
                String url2 = "https://api.privatbank.ua/p24api/aviasstations?json&address=" + address + "&state=" + regionRU;

                PobierzDane serwer = new PobierzDane(url1, url2);
                serwer.execute();
                break;
            }
            default:
            {
                break;
            }
        }
    }
}
