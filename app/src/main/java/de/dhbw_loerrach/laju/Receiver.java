package de.dhbw_loerrach.laju;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Receiver {

    InfoTabFragment infotabfrag = null;
    EventTabFragment eventtabfrag = null;
    NewInfoFragment newinfofragment = null;
    ListView infolv = null;
    private static LayoutInflater inflater;
    private String infourl = "http://laju.frederik-frey.de/lajuapp/alleNews/123456";
    private String eventurl = "http://laju.frederik-frey.de/lajuapp/gibVeranstaltungen/123456";

    public Receiver(InfoTabFragment inftb, ListView lv) {
        infotabfrag = inftb;
        infolv = lv;
        AsyncInfoTask infoTask = new AsyncInfoTask();
        infoTask.execute();
    }

    public Receiver(EventTabFragment evtb, ListView lv) {
        eventtabfrag = evtb;
        infolv = lv;
        AsyncEventTask eventTask = new AsyncEventTask();
        eventTask.execute();
    }

    public Receiver(NewInfoFragment newinfofragment , String url ,  List<NameValuePair> nameValuePairs) {
        this.newinfofragment = newinfofragment;
        AsyncPostTask postTask = new AsyncPostTask();
        postTask.url = url;
        postTask.nameValuePairs = nameValuePairs;
        try {
            Void aVoid = postTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //Async Response, um die Return Value zu erhalten
    public interface AsyncResponse {
        void processFinish(int output);
    }


    class AsyncInfoTask extends AsyncTask<String, String, Void> {
        ArrayList<InfoItem> infolist = new ArrayList<InfoItem>();

        private ProgressDialog progressDialog = new ProgressDialog(infotabfrag.getActivity());
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    AsyncInfoTask.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(infourl);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                try {
                    inputStream = httpEntity.getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e1) {
                Log.e("UnsupportedEncodingException", e1.toString());
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {
                Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
            }
            return null;
        } // protected Void doInBackground(String... params)

        protected void onPostExecute(Void v) {
            //parse JSON data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String titel = jObject.getString("titel");
                    String text = jObject.getString("text");
                    String autor = jObject.getString("autor");
                    String erstelldatum = jObject.getString("erdat");
                    InfoItem infitem = new InfoItem(titel, autor, erstelldatum, text);
                    infolist.add(infitem);
                } // End Loop
                InfoListAdapter cla = new InfoListAdapter(infotabfrag.getActivity(), R.layout.infolistlayout, infolist);
                ListView lv = (ListView) infotabfrag.getActivity().findViewById(R.id.infoTabList);
                lv.setAdapter(cla);
                // Item Click Listener for the listview
                OnItemClickListener eventTtemClickListener = new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                        InfoItem i = infolist.get(position);
                        Intent intent = new Intent(infotabfrag.getActivity(), Info.class);
                        intent.putExtra("info", i);
                        infotabfrag.getActivity().startActivity(intent);
                    }
                };
                lv.setOnItemClickListener(eventTtemClickListener);
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
                this.progressDialog.dismiss();
                Toast.makeText(infotabfrag.getActivity(), "Da hät öbbis nid klappt", Toast.LENGTH_LONG).show();
            } // catch (JSONException e)
        } // protected void onPostExecute(Void v)
    } //class MyAsyncTask extends AsyncTask<String, String, Void>

    class AsyncEventTask extends AsyncTask<String, String, Void> {
        ArrayList<EventItem> eventlist = new ArrayList<EventItem>();

        private ProgressDialog progressDialog = new ProgressDialog(eventtabfrag.getActivity());
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    AsyncEventTask.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(eventurl);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                try {
                    inputStream = httpEntity.getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e1) {
                Log.e("UnsupportedEncodingException", e1.toString());
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line);
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {
                Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
            }
            return null;
        } // protected Void doInBackground(String... params)

        protected void onPostExecute(Void v) {
            //parse JSON data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String beschreibung = jObject.getString("beschreibung");
                    String bild = jObject.getString("bild");
                    String datum_bis = jObject.getString("datum_bis");
                    String datum_von = jObject.getString("datum_von");
                    String titel = jObject.getString("titel");
                    String untertitel = jObject.getString("untertitel");
                    String url = jObject.getString("url");
                    EventItem eventitem = new EventItem(beschreibung, bild, datum_bis, datum_von, titel, untertitel, url);
                    eventlist.add(eventitem);
                } // End Loop
                EventListAdapter cla = new EventListAdapter(eventtabfrag.getActivity(), R.layout.eventlistlayout, eventlist);
                ListView lv = (ListView) eventtabfrag.getActivity().findViewById(R.id.eventTabList);
                lv.setAdapter(cla);
                // Item Click Listener for the listview
                OnItemClickListener eventTtemClickListener = new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                        EventItem e = eventlist.get(position);
                        Intent intent = new Intent(eventtabfrag.getActivity(), Event.class);
                        intent.putExtra("event", e);
                        eventtabfrag.getActivity().startActivity(intent);
                    }
                };
                lv.setOnItemClickListener(eventTtemClickListener);
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
                this.progressDialog.dismiss();
                Toast.makeText(eventtabfrag.getActivity(), "Da hät öbbis nid klappt", Toast.LENGTH_LONG).show();
            } // catch (JSONException e)
        } // protected void onPostExecute(Void v)
    } // protected void onPostExecute(Void v)


    class AsyncPostTask extends AsyncTask<String, String, Void> {
        public AsyncResponse delegate = null;
        private ProgressDialog progressDialog = new ProgressDialog(newinfofragment.getActivity());
        public String url;
        HttpResponse response = null;
        public java.util.List<NameValuePair> nameValuePairs;

        protected void onPreExecute(int resultcode) {
            delegate.processFinish(resultcode);
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    AsyncPostTask.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                response = httpclient.execute(httppost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } // protected Void doInBackground(String... params)

        protected void onPostExecute() {
            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            String theString = writer.toString();
            int result = Integer.parseInt(theString.substring(1, -1));
            progressDialog.dismiss();
        }
    }

} //Receiver