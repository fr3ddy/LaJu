package de.dhbw_loerrach.laju;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Receiver {
    RequestQueue queue;
    private InfoTabFragment infoTabFragment;
    private EventTabFragment eventTabFragment;
    private NewInfoFragment newInfoFragment;
    private Login login;
    private Register register;
    private OffersTabFragment offersTabFragment;
    private RequestsTabFragment requestsTabFragment;
    private Exchange exchange;
    private NewExchangeFragment newExchangeFragment;
    private EditUser editUser;
    private String basic = "http://laju.frederik-frey.de/lajuapp/";
    private String infourl = basic +"gibAlleNeuigkeiten/"+R.string.appkey;
    private String eventurl = basic +"gibVeranstaltungen/"+R.string.appkey;
    private String newnewsurl = basic +"NeuigkeitEinreichen";
    private String loginurl = basic +"einloggen";
    private String registerurl = basic +"registriereBenutzer";
    private String userdataurl = basic +"gibUserDaten";
    private String offersurl = basic +"gibAlleAngebote/"+R.string.appkey;
    private String commentsurl = basic +"gibKommentare";
    private String newcommenturl = basic +"eintragKommentieren";
    private String requesturl = basic +"gibAlleNachfragen/"+R.string.appkey;
    private String newexchangeurl = basic +"eintragEinreichen";
    private String closeexchangeurl = basic +"eintragSchliessen";
    private String edituserurl = basic +"aendereUserdaten";

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

    public Receiver(OffersTabFragment offersTabFragment) {
        queue = Volley.newRequestQueue(offersTabFragment.getActivity());
        this.offersTabFragment = offersTabFragment;
    }

    public Receiver(RequestsTabFragment requestsTabFragment) {
        queue = Volley.newRequestQueue(requestsTabFragment.getActivity());
        this.requestsTabFragment = requestsTabFragment;
    }

    public Receiver(Exchange exchange) {
        queue = Volley.newRequestQueue(exchange);
        this.exchange = exchange;
    }

    public Receiver(NewExchangeFragment newExchangeFragment) {
        queue = Volley.newRequestQueue(newExchangeFragment.getActivity());
        this.newExchangeFragment = newExchangeFragment;
    }

    public Receiver(EditUser editUserinstance) {
        queue = Volley.newRequestQueue(editUserinstance);
        this.editUser = editUserinstance;
    }

    public void fillInfos() {
        final ArrayList<InfoItem> infolist = new ArrayList<InfoItem>();
        StringRequest infoListRequest = new StringRequest(Request.Method.GET, infourl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                response = response.substring(3);
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
                infoTabFragment.mSwipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                infoTabFragment.mSwipeRefreshLayout.setRefreshing(false);
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
                response = response.substring(3);
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
                        eventTabFragment.getActivity().startActivityForResult(intent, 1337);
                    }
                };
                lv.setOnItemClickListener(eventTtemClickListener);
                eventTabFragment.mSwipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                eventTabFragment.mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(eventTabFragment.getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(eventListRequest);
    }

    public void sendNewInfo(HashMap<String, String> params) {
        JsonObjectRequest newInfoRequest = new JsonObjectRequest(Request.Method.POST, newnewsurl, new JSONObject(params), new Response.Listener<JSONObject>() {
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
                    case 3:
                        Toast.makeText(login, "Der Benutzername existiert nicht! Melde dich einfach kostenlos neu an!", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Toast.makeText(login, "Dein Account ist noch nicht aktiv! Bitte überprüfe deine Emails!", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        Toast.makeText(login, "Dein Account wurde gesperrt! Wende dich an die Zentrale der Badischen Landjugend!", Toast.LENGTH_LONG).show();
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
//                            JSONObject data = (JSONObject) response.get("daten");
                            String u = (String) response.get("benutzer");
                            String firstname = (String) response.get("vorname");
                            String lastname = (String) response.get("nachname");
                            String email = (String) response.get("email");
                            User.login(login , u, firstname, lastname, email);
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

    public void fillOffers() {
        final ArrayList<ExchangeItem> offerlist = new ArrayList<ExchangeItem>();
        StringRequest offerListRequest = new StringRequest(Request.Method.GET, offersurl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                response = response.substring(3);
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
                    int tauschid = 0;
                    String title = "";
                    String text = "";
                    String username = "";
                    String userfirstname = "";
                    String userlastname = "";
                    int userid = 0;
                    boolean done = false;
                    boolean open = true;
                    String erdat = "";
                    String type = "";
                    try {
                        tauschid = Integer.parseInt(jObject.getString("tauschid"));
                        title = jObject.getString("titel");
                        text = jObject.getString("besch");
                        username = jObject.getJSONObject("user").getString("benutzer");
                        userfirstname = jObject.getJSONObject("user").getString("vorname");
                        userlastname = jObject.getJSONObject("user").getString("nachname");
                        userid = Integer.parseInt(jObject.getJSONObject("user").getString("userid"));
                        done = jObject.getBoolean("geloest");
                        open = jObject.getBoolean("offen");
                        erdat = jObject.getString("erdat");
                        type = jObject.getString("art");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ExchangeItem ofitem = new ExchangeItem(tauschid, title, text, username, userfirstname, userlastname, userid, done, open, erdat, type);
                    offerlist.add(ofitem);
                } // End Loop
                ExchangeListAdapter cla = new ExchangeListAdapter(offersTabFragment.getActivity(), R.layout.exchangelistlayout, offerlist);
                ListView lv = (ListView) offersTabFragment.getActivity().findViewById(R.id.offersTabList);
                lv.setAdapter(cla);
                // Item Click Listener for the listview
                AdapterView.OnItemClickListener eventTtemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                        ExchangeItem o = offerlist.get(position);
                        Intent intent = new Intent(offersTabFragment.getActivity(), Exchange.class);
                        intent.putExtra("offer", o);
                        offersTabFragment.getActivity().startActivityForResult(intent, 1338);
                    }
                };
                lv.setOnItemClickListener(eventTtemClickListener);
                offersTabFragment.mSwipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                offersTabFragment.mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(offersTabFragment.getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(offerListRequest);
    }

    public void fillRequests() {
        final ArrayList<ExchangeItem> requestlist = new ArrayList<ExchangeItem>();
        StringRequest requestListRequest = new StringRequest(Request.Method.GET, requesturl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                response = response.substring(3);
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
                    int tauschid = 0;
                    String title = "";
                    String text = "";
                    String username = "";
                    String userfirstname = "";
                    String userlastname = "";
                    int userid = 0;
                    boolean done = false;
                    boolean open = true;
                    String erdat = "";
                    String type = "";
                    try {
                        tauschid = Integer.parseInt(jObject.getString("tauschid"));
                        title = jObject.getString("titel");
                        text = jObject.getString("besch");
                        username = jObject.getJSONObject("user").getString("benutzer");
                        userfirstname = jObject.getJSONObject("user").getString("vorname");
                        userlastname = jObject.getJSONObject("user").getString("nachname");
                        userid = Integer.parseInt(jObject.getJSONObject("user").getString("userid"));
                        done = jObject.getBoolean("geloest");
                        open = jObject.getBoolean("offen");
                        erdat = jObject.getString("erdat");
                        type = jObject.getString("art");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ExchangeItem ofitem = new ExchangeItem(tauschid, title, text, username, userfirstname, userlastname, userid, done, open, erdat,type);
                    requestlist.add(ofitem);
                } // End Loop
                ExchangeListAdapter cla = new ExchangeListAdapter(requestsTabFragment.getActivity(), R.layout.exchangelistlayout, requestlist);
                ListView lv = (ListView) requestsTabFragment.getActivity().findViewById(R.id.requestTabList);
                lv.setAdapter(cla);
                // Item Click Listener for the listview
                AdapterView.OnItemClickListener eventTtemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                        ExchangeItem o = requestlist.get(position);
                        Intent intent = new Intent(requestsTabFragment.getActivity(), Exchange.class);
                        intent.putExtra("offer", o);
                        requestsTabFragment.getActivity().startActivityForResult(intent, 1339);
                    }
                };
                lv.setOnItemClickListener(eventTtemClickListener);
                requestsTabFragment.mSwipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                requestsTabFragment.mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(requestsTabFragment.getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(requestListRequest);
    }

    public void fillComments(HashMap<String, String> params) {
        JsonObjectRequest commentsRequest = new JsonObjectRequest(Request.Method.POST, commentsurl, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String comments = "";
                String r = null;
                try {
                    r = response.getJSONArray("kommentare").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(r);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LinearLayout container = (LinearLayout) exchange.findViewById(R.id.offerCommentsList);
                container.removeAllViews();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String erdat = "";
                    String text = "";
                    String firstname = "";
                    String lastname = "";
                    try {
                        erdat = jObject.get("erdat").toString();
                        text = jObject.get("text").toString();
                        firstname = jObject.getJSONObject("autor").get("vorname").toString();
                        lastname = jObject.getJSONObject("autor").get("nachname").toString().substring(0, 1) + ".";

                        //TODO: Styling! http://commonsware.com/blog/Android/2010/05/26/html-tags-supported-by-textview.html

                        LayoutInflater inflater = (LayoutInflater) exchange.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.comments, null);
                        TextView cText = (TextView) view.findViewById(R.id.text);
                        cText.setText(text);

                        TextView date = (TextView) view.findViewById(R.id.date);
                        date.setText(erdat);

                        TextView author = (TextView) view.findViewById(R.id.author);
                        author.setText(firstname + " " + lastname);

                        container.addView(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(exchange, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(commentsRequest);
    }

    public void sendNewComment(HashMap<String, String> params , final HashMap<String, String> paramsC) {
        JsonObjectRequest newInfoRequest = new JsonObjectRequest(Request.Method.POST, newcommenturl, new JSONObject(params), new Response.Listener<JSONObject>() {
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
                        Toast.makeText(exchange, "Erfolgreich Kommentiert", Toast.LENGTH_SHORT).show();
                        EditText offerComment = (EditText) exchange.findViewById(R.id.offerNewComment);
                        offerComment.setText("");
                        fillComments(paramsC);
                        break;
                    case 1:
                        Toast.makeText(exchange, "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(exchange, "Dieser User ist uns nicht bekannt, bitte wenden Sie sich an Ihren Systemadminstrator", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(exchange, "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(exchange, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(newInfoRequest);
    }

    public void sendNewExchange(final HashMap<String, String> params) {
        final JsonObjectRequest newExchangeRequest = new JsonObjectRequest(Request.Method.POST, newexchangeurl, new JSONObject(params), new Response.Listener<JSONObject>() {
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
                        Toast.makeText(newExchangeFragment.getActivity(), params.get("art") + " erfolgreich eingereicht", Toast.LENGTH_SHORT).show();
                        newExchangeFragment.getActivity().finish();
                        break;
                    case 1:
                        Toast.makeText(newExchangeFragment.getActivity(), "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(newExchangeFragment.getActivity(), "Dieser User ist uns nicht bekannt, bitte wenden Sie sich an Ihren Systemadminstrator", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(newExchangeFragment.getActivity(), "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(newExchangeFragment.getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(newExchangeRequest);
    }

    public void closeExchange(HashMap<String, String> params) {
        JsonObjectRequest newCloseExchangeRequest = new JsonObjectRequest(Request.Method.POST, closeexchangeurl, new JSONObject(params), new Response.Listener<JSONObject>() {
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
                        Toast.makeText(exchange, "Erfolgreich abgeschlossen", Toast.LENGTH_SHORT).show();
                        exchange.finish();
                        break;
                    case 1:
                        Toast.makeText(exchange, "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(exchange, "Dieser User ist uns nicht bekannt, bitte wenden Sie sich an Ihren Systemadminstrator", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(exchange, "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(exchange, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(newCloseExchangeRequest);
    }

    public void editUser(final HashMap<String, String> params) {
        final JsonObjectRequest newEditUserRequest = new JsonObjectRequest(Request.Method.POST, edituserurl, new JSONObject(params), new Response.Listener<JSONObject>() {
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
                        Toast.makeText(editUser, params.get("art") + " erfolgreich eingereicht", Toast.LENGTH_SHORT).show();
                        editUser.finish();
                        break;
                    case 1:
                        Toast.makeText(editUser, "AppKey war falsch, bitte wenden Sie sich an Ihren Systemadministrator", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(editUser, "Diese Email Adresse gibt es schon, versuche bitte eine andere!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(editUser, "Da lief was falsch!!!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editUser, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(newEditUserRequest);
    }
    public void clearQueue(){
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}