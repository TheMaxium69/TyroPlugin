package fr.tyrolium.tyroplugin.skin;

import fr.tyrolium.tyroplugin.Global;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

public class SkinApi {

    public static ArrayList<Object> getSkinInfo(String pseudo) {

        String request = Global.api_url + "&pseudo=" + pseudo;
        ArrayList<Object> stateSkin = new ArrayList<>();

        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Tout fermer
            in.close();
            conn.disconnect();


            // Resultat
            String jsonResult = content.toString();

//            System.out.println("Reponse du serveur : " + jsonResult);

            JSONObject jsonObject = new JSONObject(jsonResult);
            String status = jsonObject.getString("status");
            String why = jsonObject.getString("why");

//            System.out.println("Reponse du serveur : " + status);
//            System.out.println("Reponse du serveur : " + why);

            if (status.equals("true") && why.equals("successfully request")) {

                JSONObject resultObject = jsonObject.getJSONObject("result");

                String skin = resultObject.getString("skin");
                int slim = resultObject.getInt("slim");

                stateSkin.add(skin);
                stateSkin.add(slim);

                return stateSkin;

            } else {

//                System.out.println("[TyroPlugin] Erreur de pseudo");

                stateSkin.add("err");
                stateSkin.add("err");

                return stateSkin;
            }


        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("[TyroPlugin] Erreur Java");

            stateSkin.add("err");
            stateSkin.add("err");

            return stateSkin;
        }


    }




}
