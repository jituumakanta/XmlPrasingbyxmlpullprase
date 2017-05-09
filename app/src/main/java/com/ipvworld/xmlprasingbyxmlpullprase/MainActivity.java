package com.ipvworld.xmlprasingbyxmlpullprase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://indianexpress.com/section/sports/feed/";
        getData9(url);


    }

    public void getData9(String url) {
        // String url = Config.url;
        // Log.d("nam", url);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String xml = response.toString();
                        // Log.d("nam", xml);
                        // Toast.makeText(c, response, Toast.LENGTH_LONG).show();
                       // getElement(xml);
                        dompraser(xml);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        //return xml;
    }

    public void getElement(String xml) {
        try {
            //InputStream is = getAssets().open("file.xml");
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nl = doc.getElementsByTagName("item");

            for (int i = 0; i < nl.getLength(); i++) {

                //Log.d("hhh","lll");
                HashMap<String, String> map = new HashMap<String, String>();

                Element e = (Element) nl.item(i);
               // String f = e.getAttribute("url");
                //String d=nl.item(i).getAttributes().getNamedItem("url").getNodeValue();
               // Log.d("rrr",f);
                map.put("a", getValue(e, "id"));
                map.put("b", getValue(e, "name"));
                map.put("c", getValue(e, "cost"));
                map.put("d", getValue(e, "description"));

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String thing = entry.getValue();
                    Log.d(key, thing);
                    //Toast.makeText(getApplicationContext(), thing, Toast.LENGTH_LONG).show();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String getValue(Element element, String tag) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }



    public void dompraser(String xml){

        try {
           // File inputFile = new File("input.txt");
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();

           // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("item");

            //System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

               // System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String c=eElement.getAttribute("link");
                   String v= eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                    Log.d("res",v);
                   // System.out.println("Student roll no : " + eElement.getAttribute("rollno"));
                   // System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                   // System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                   // System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                   // System.out.println("Marks : " + eElement.getElementsByTagName("marks").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
