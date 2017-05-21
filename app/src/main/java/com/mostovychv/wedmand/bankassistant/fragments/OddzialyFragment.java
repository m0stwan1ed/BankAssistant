package com.mostovychv.wedmand.bankassistant.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mostovychv.wedmand.bankassistant.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class OddzialyFragment extends android.app.Fragment implements View.OnClickListener{

    EditText editTextMiasto;
    EditText editTextUlica;
    Button buttonSzukaj;
    TextView textViewDane;

    public class PBOddzialy
    {
        public String name;
        public String state;
        public String id;
        public String country;
        public String city;
        public String index;
        public String phone;
        public String email;
        public String address;
    }

    ArrayList<PBOddzialy> pbOddzialy = new ArrayList<>();

    class PobierzDane extends AsyncTask<Void, Void, Void>
    {
        private String url;
        private String answer = "";
        private Gson gson = new Gson();		// GSON library object

        PobierzDane(String url)
        {
            this.url = url;
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
            pbOddzialy = new ArrayList<>(Arrays.asList(gson.fromJson(answer, PBOddzialy[].class)));

            String nazwa = "Nazwa: ";
            String region = "Region: ";
            String id = "Numer: ";
            String miasto = "Miasto: ";
            String indeks = "Kod pocztowy: ";
            String numerTelefonu = "Numer telefonu: ";
            String email = "Email: ";
            String adres = "Adres: ";

            String dane = "";

            if(pbOddzialy.size() == 0) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
            } else {
                for(int i = 0; i < pbOddzialy.size(); i++) {
                    dane = dane + nazwa + pbOddzialy.get(i).name+"\n";
                    dane = dane + adres + pbOddzialy.get(i).address + "\n";
                    dane = dane + region + pbOddzialy.get(i).state + "\n";
                    dane = dane + miasto + pbOddzialy.get(i).city + "\n";
                    dane = dane + indeks + pbOddzialy.get(i).index + "\n";
                    dane = dane + numerTelefonu + pbOddzialy.get(i).phone + "\n";
                    dane = dane + email + pbOddzialy.get(i).email + "\n";
                    dane = dane + "\n";
                }
                textViewDane.setText(dane);
            }
        }
    }

    public OddzialyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout cl = (ConstraintLayout)inflater.inflate(R.layout.fragment_oddzialy, container, false);

        editTextMiasto = (EditText)cl.findViewById(R.id.editTextMiasto);
        editTextUlica = (EditText)cl.findViewById(R.id.editTextUlica);
        buttonSzukaj = (Button)cl.findViewById(R.id.buttonSzukaj);
        buttonSzukaj.setOnClickListener(this);
        textViewDane = (TextView)cl.findViewById(R.id.textViewDane);

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
            case R.id.buttonSzukaj:
            {
                String miasto = editTextMiasto.getText().toString();
                String ulica = editTextUlica.getText().toString();
                String url = "https://api.privatbank.ua/p24api/pboffice?json&city=" + miasto + "&address=" + ulica;
                PobierzDane dane = new PobierzDane(url);
                dane.execute();
                break;
            }
            default:
            {
                break;
            }
        }
    }
}
