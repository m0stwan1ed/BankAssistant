package com.mostovychv.wedmand.bankassistant.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mostovychv.wedmand.bankassistant.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ATMFragment extends android.app.Fragment implements View.OnClickListener{

    EditText editTextMiasto;
    EditText editTextUlica;
    Button buttonSzukaj;
    TextView textViewDane;
    Switch switch1;

    public class Tw
    {
        public String mon;
        public String tue;
        public String wed;
        public String thu;
        public String fri;
        public String sat;
        public String sun;
        public String hol;
    }

    public class Device
    {
        public String type;
        public String cityRU;
        public String cityUA;
        public String cityEN;
        public String fullAddressRu;
        public String fullAddressUa;
        public String fullAddressEn;
        public String placeRu;
        public String placeUa;
        public String latitude;
        public String longitude;
        public Tw tw;
    }

    public class PBAtmTso
    {
        public String city;
        public String address;
        public List<Device> devices;
    }

    PBAtmTso pbAtmTso = new PBAtmTso();

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
            pbAtmTso = new Gson().fromJson(answer, PBAtmTso.class);

            if(pbAtmTso.devices.size() == 0) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
            }
            else {
                String miasto = "City: ";
                String fullAdresUa = "Adress(UA): ";
                String fullAdresAn = "Adress(EN): ";
                String miejsce = "Place: ";
                String godzinyPracy = "Working hours: ";
                String mon = "Mon - \t";
                String tue = "Tue - \t\t";
                String wed = "Wed - \t";
                String thu = "Thu - \t\t";
                String fri = "Fri - \t\t";
                String sat = "Sat - \t\t";
                String sun = "Sun - \t\t";
                String hol = "Hol - \t\t";

                String answer = "";
                answer = answer + miasto + pbAtmTso.city + "\n\n";
                for (int i = 0; i < pbAtmTso.devices.size(); i++) {
                    answer = answer + miejsce + pbAtmTso.devices.get(i).placeUa + "\n";
                    answer = answer + fullAdresUa + pbAtmTso.devices.get(i).fullAddressUa + "\n";
                    answer = answer + fullAdresAn + pbAtmTso.devices.get(i).fullAddressEn + "\n";
                    answer = answer + godzinyPracy + "\n";
                    answer = answer + mon + pbAtmTso.devices.get(i).tw.mon + "\n";
                    answer = answer + tue + pbAtmTso.devices.get(i).tw.tue + "\n";
                    answer = answer + wed + pbAtmTso.devices.get(i).tw.wed + "\n";
                    answer = answer + thu + pbAtmTso.devices.get(i).tw.thu + "\n";
                    answer = answer + fri + pbAtmTso.devices.get(i).tw.fri + "\n";
                    answer = answer + sat + pbAtmTso.devices.get(i).tw.sat + "\n";
                    answer = answer + sun + pbAtmTso.devices.get(i).tw.sun + "\n";
                    answer = answer + hol + pbAtmTso.devices.get(i).tw.hol + "\n";
                    answer = answer + "\n";
                }

                textViewDane.setText(answer);
            }
        }
    }

    public ATMFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout)inflater.inflate(R.layout.fragment_atm, container, false);

        editTextMiasto = (EditText)cl.findViewById(R.id.editTextMiasto);
        editTextUlica = (EditText)cl.findViewById(R.id.editTextUlica);
        buttonSzukaj = (Button)cl.findViewById(R.id.buttonSzukaj);
        buttonSzukaj.setOnClickListener(this);
        textViewDane = (TextView)cl.findViewById(R.id.textViewDane);
        switch1 = (Switch)cl.findViewById(R.id.switch1);

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
                String device;
                if(switch1.isChecked()) {
                    device = "tso";
                } else {
                    device = "atm";
                }
                String url = "https://api.privatbank.ua/p24api/infrastructure?json&" + device + "&address=" + ulica + "&city=" + miasto;
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
