package de.dhbw_loerrach.laju;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Receiver {
    RequestQueue queue;
    private InfoTabFragment infoTabFragment;
    private EventTabFragment eventTabFragment;
    private NewInfoFragment newInfoFragment;
    private Login login;
    private Register register;
    private String infourl = "http://laju.frederik-frey.de/lajuapp/alleNews/123456";
    private String eventurl = "http://laju.frederik-frey.de/lajuapp/gibVeranstaltungen/123456";
    private String loginurl = "http://laju.frederik-frey.de/lajuapp/einloggen";
    private String registerurl = "http://laju.frederik-frey.de/lajuapp/registriereBenutzer";
    private String userdataurl = "http://laju.frederik-frey.de/lajuapp/gibUserDaten";

    public Receiver(InfoTabFragment infoTabFragment) {
        queue = Volley.newRequestQueue(infoTabFragment.getActivity());
        this.infoTabFragment = infoTabFragment;
    }

    public Receiver(EventTabFragment eventTabFragment) {
        queue = Volley.newRequestQueue(eventTabFragment.getActivity());
        this.eventTabFragment = eventTabFragment;
    }

    public Receiver(NewInfoFragment newInfoFragment) {
        queue = Volley.newRequestQueue(newInfoFragment.getActivity());
        this.newInfoFragment = newInfoFragment;
    }

    public Receiver(Login login) {
        queue = Volley.newRequestQueue(login);
        this.login = login;
    }

    public Receiver(Register register) {
        queue = Volley.newRequestQueue(register);
        this.register = register;
    }

    public void fillInfos() {
        final ArrayList<InfoItem> infolist = new ArrayList<InfoItem>();
        StringRequest infoListRequest = new StringRequest(Request.Method.GET, infourl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String titel = null;
                    try {
                        titel = jObject.getString("titel");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String text = null;
                    try {
                        text = jObject.getString("text");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String autor = null;
                    try {
                        autor = jObject.getString("autor");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String erstelldatum = null;
                    try {
                        erstelldatum = jObject.getString("erdat");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    InfoItem infitem = new InfoItem(titel, autor, erstelldatum, text);
                    infolist.add(infitem);
                } // End Loop
                InfoListAdapter cla = new InfoListAdapter(infoTabFragment.getActivity(), R.layout.infolistlayout, infolist);
                ListView lv = (ListView) infoTabFragment.getActivity().findViewById(R.id.infoTabList);
                lv.setAdapter(cla);
                // Item Click Listener for the listview
                AdapterView.OnItemClickListener eventTtemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                        InfoItem i = infolist.get(position);
                        Intent intent = new Intent(infoTabFragment.getActivity(), Info.class);
                        intent.putExtra("info", i);
                        infoTabFragment.getActivity().startActivity(intent);
                    }
                };
                lv.setOnItemClickListener(eventTtemClickListener);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(infoTabFragment.getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(infoListRequest);
    }

    public void fillEvents() {
        final ArrayList<EventItem> eventlist = new ArrayList<EventItem>();
        StringRequest eventListRequest = new StringRequest(Request.Method.GET, eventurl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String beschreibung = null;
                    try {
                        beschreibung = jObject.getString("beschreibung");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String bild = null;
                    try {
                        bild = jObject.getString("bild");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String datum_bis = null;
                    try {
                        datum_bis = jObject.getString("datum_bis");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String datum_von = null;
                    try {
                        datum_von = jObject.getString("datum_von");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String titel = null;
                    try {
                        titel = jObject.getString("titel");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String untertitel = null;
                    try {
                        untertitel = jObject.getString("untertitel");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url = null;
                    try {
                        url = jObject.getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    EventItem eventitem = new EventItem(beschreibung, bild, datum_bis, datum_von, titel, untertitel, url);
                    eventlist.add(eventitem);
                } // End Loop
                EventListAdapter cla = new EventListAdapter(eventTabFragment.getActivity(), R.layout.eventlistlayout, eventlist);
                ListView lv = (ListView) eventTabFragment.getActivity().findViewById(R.id.eventTabList);
                lv.setAdapter(cla);
                // Item Click Listener for the listview
                AdapterView.OnItemClickListener eventTtemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                        EventItem e = eventlist.get(position);
                        Intent intent = new Intent(eventTabFragment.getActivity(), Event.class);
                        intent.putExtra("event", e);
                        eventTabFragment.getActivity().startActivity(intent);
                    }
                };
                lv.setOnItemClickListener(eventTtemClickListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(eventTabFragment.getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(eventListRequest);
    }

    public void sendNewInfo(String url, HashMap<String, String> params) {
        JsonObjectRequest newInfoRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int responsecode = 0;
                try {
                    responsecode = (int) response.get("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (responsecode) {
                    case 0:
                        Toast.makeText(newInfoFragment.getActivity(), "Info erfolgreich eingereicht", Toast.LENGTH_SHORT).show();
                        newInfoFragment.getActivity().finish();
                        break;
                    case 1:
                        Toast.makeText(newInfoFragment.getActivity(), "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(newInfoFragment.getActivity(), "Dieser User ist uns nicht bekannt, bitte wenden Sie sich an Ihren Systemadminstrator", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(newInfoFragment.getActivity(), "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(newInfoFragment.getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(newInfoRequest);
    }

    public void login(final HashMap<String, String> params) {
        JsonObjectRequest newLoginRequest = new JsonObjectRequest(Request.Method.POST, loginurl, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int responsecode = 0;
                try {
                    responsecode = (int) response.get("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (responsecode) {
                    case 0:
                        performLogin(params.get("appkey"), params.get("benutzername"));
                        break;
                    case 1:
                        Toast.makeText(login, "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(login, "Das Passwort war leider falsch!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(login, "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(newLoginRequest);
    }

    private void performLogin(String appkey, String username) {
        JsonObjectRequest getUserdataRequest = new JsonObjectRequest(Request.Method.GET, userdataurl + "/" + appkey + "/" + username, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int responsecode = 0;
                try {
                    responsecode = (int) response.get("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (responsecode) {
                    case 0:
                        try {
                            JSONObject data = (JSONObject) response.get("daten");
                            String u = (String) data.get("benutzer");
                            String firstname = (String) data.get("vorname");
                            String lastname = (String) data.get("nachname");
                            String email = (String) data.get("email");
                            new User(u, firstname, lastname, email);
                            login.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        Toast.makeText(login, "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(login, "Der Benutzer ist uns nicht bekannt! Da lief wohl was schief!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(login, "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(getUserdataRequest);
    }

    public void register(HashMap<String, String> params) {
        JsonObjectRequest newRegisterRequest = new JsonObjectRequest(Request.Method.POST, registerurl, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int responsecode = 0;
                try {
                    responsecode = (int) response.get("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (responsecode) {
                    case 0:
                        Toast.makeText(register, "Registrierung hat geklappt", Toast.LENGTH_LONG).show();
                        register.finish();
                        break;
                    case 1:
                        Toast.makeText(register, "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(register, "Diese Email wird schon verwendet, versuche es mit einer anderen!", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(register, "Dieser Benutzername existiert bereits, versuche es mit einem anderen!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(register, "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(newRegisterRequest);
    }
}