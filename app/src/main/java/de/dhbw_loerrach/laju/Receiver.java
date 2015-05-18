package de.dhbw_loerrach.laju;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Receiver {

    InfoTabFragment infotabfrag = null;
    EventTabFragment eventtabfrag = null;
    ListView infolv = null;
    private static LayoutInflater inflater;
    private String infourl = "http://frey.cloud4germany.com/lajuapp/alleNews/123456";
    private String eventurl = "http://laju-suedbaden.de/de/veranstaltungen/export.php";

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
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
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
            //parse XML data

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            InputSource is = new InputSource( new StringReader(result) );
            Document doc = null;
            try {
                doc = builder.parse( is );
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            XPathFactory factory2 = XPathFactory.newInstance();
            XPath xpath = factory2.newXPath();
            XPathExpression expr = null;
            try {
                expr = xpath.compile("//src_small/text()");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            Object result = null;
            try {
                result = expr.evaluate(doc, XPathConstants.NODESET);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            NodeList nodes = (NodeList) result;
            List<String> urls = new ArrayList<String>();
            for (int i = 0; i < nodes.getLength(); i++) {
                urls.add (nodes.item(i).getNodeValue());
            }

            /*EventItem eventitem = new EventItem();
            eventlist.add(eventitem);
            this.progressDialog.dismiss();
            EventListAdapter cla = new EventListAdapter(eventtabfrag.getActivity(), R.layout.eventlistlayout, eventlist);
            ListView lv = (ListView) eventtabfrag.getActivity().findViewById(R.id.eventTabList);
            lv.setAdapter(cla); */
        } // protected void onPostExecute(Void v)
    } //class MyAsyncTask extends AsyncTask<String, String, Void>
}