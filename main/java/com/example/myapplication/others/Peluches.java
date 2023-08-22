package com.example.myapplication.others;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Peluches extends ArrayList<Peluches.Peluche> {
    public Peluches(Context context) {
        construireListe(context);
    }

    //////////////////////////////////////////////////
    //////////////// Utilisation JSON ////////////////
    //////////////////////////////////////////////////
    public void construireListe(Context context) {
        // On crée la liste à partir du JSON qu'on va recuperer et de la methode pour recuperer les peluches
        try {
            JSONArray jsonArray = new JSONArray(getJSONFromAsset(context));
            for (int i = 0; i < jsonArray.length(); i++) {
                this.add(getPersonneFromJSONObject(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getJSONFromAsset(Context context) {
        // On va retourner un String à partir d'un JSON se trouvant dans le dossier /assets
        String json = null;
        try {
            InputStream is = context.getAssets().open("peluches.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static Peluche getPersonneFromJSONObject(JSONObject jsonObject) throws JSONException {
        // On va recuperer les champs dans le JSON pour renvoyer une peluche
        int id = jsonObject.getInt("id");
        String nom = jsonObject.getString("nom");
        String url = jsonObject.getString("imgurl");
        float prix = jsonObject.getInt("prix");
        String type = jsonObject.getString("type");
        String description = jsonObject.getString("description");
        String taille = jsonObject.getString("taille");

        return new Peluche(id, nom, url, prix, type, description,taille);
    }

    ///////////////////////////////////////////////////
    //////////////// Classe Parcelable ////////////////
    ///////////////////////////////////////////////////
    public static class Peluche implements Parcelable {
        private final int id;
        private final String name;
        private final String image;
        private final float price;
        private final String type;
        private final String description;
        private final String taille;

        private boolean select;

        public Peluche(int id, String name, String image, float price, String type, String desc, String taille) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.image = image;
            this.type = type;
            this.description = desc;
            this.taille = taille;
        }

        public Peluche(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.image = in.readString();
            this.price = in.readFloat();
            this.type = in.readString();
            this.description = in.readString();
            this.taille = in.readString();
        }

        public String getName() {
            return name;
        }

        public float getPrice() {
            return price;
        }

        public String getImageURL() {
            return image;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getDescription() {
            return this.description;
        }

        public String getTaille() {
            return taille;
        }

        public String getType() {
            return type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            boolean result;
            if ((o == null) || (getClass() != o.getClass())) {
                result = false;
            } else {
                Peluche other = (Peluche) o;
                result = name.equals(other.name) && (price == other.price) && image.equals(other.image);
            }
            return result;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(image);
            dest.writeFloat(price);
            dest.writeString(type);
            dest.writeString(description);
            dest.writeString(taille);
        }

        public static final Parcelable.Creator<Peluche> CREATOR = new Parcelable.Creator<Peluche>() {
            @Override
            public Peluche createFromParcel(Parcel source) {
                return new Peluche(source);
            }

            @Override
            public Peluche[] newArray(int size) {
                return new Peluche[size];
            }
        };
    }
}