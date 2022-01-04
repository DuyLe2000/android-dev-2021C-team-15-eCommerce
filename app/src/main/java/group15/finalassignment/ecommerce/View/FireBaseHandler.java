package group15.finalassignment.ecommerce.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FireBaseHandler {
    Account account;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FireBaseHandler(){
    }

    public void addAccount(String name, String phoneNum, String email, String address) {
        Account account = new Account(name, phoneNum, email, address);
        db.collection("accounts").document(email).set(account);
    }

//    public void addNewSite(String host, String location, String date, String description, GeoPoint geoPoint) {
//        Site site = new Site(location, host, date, description, geoPoint, ThumbnailPicker.randomPick());
//        site.addParticipant(host);
//        db.collection("sites").document(location).set(site);
//    }
//
//    public ArrayList<Task<QuerySnapshot>> retrieveSiteData() {
//        final ArrayList<Task<QuerySnapshot>> data = new ArrayList<Task<QuerySnapshot>>();
//        data.add(db.collection("sites").get());
//        return data;
//    }
//
//    public ArrayList<Task<QuerySnapshot>> retrieveUserData() {
//        final ArrayList<Task<QuerySnapshot>> data = new ArrayList<Task<QuerySnapshot>>();
//        data.add(db.collection("accounts").get());
//        return data;
//    }
//
//    public ArrayList<Task<QuerySnapshot>> retrieveHostSiteData(String email) {
//        final ArrayList<Task<QuerySnapshot>> data = new ArrayList<Task<QuerySnapshot>>();
//        data.add(db.collection("sites").whereEqualTo("host",email).get());
//        return data;
//    }
//
//    public ArrayList<Task<QuerySnapshot>> retrieveJoinSiteData(String email) {
//        final ArrayList<Task<QuerySnapshot>> data = new ArrayList<Task<QuerySnapshot>>();
//        data.add(db.collection("sites").whereNotEqualTo("host",email).whereArrayContains("participants",email).get());
//        return data;
//    }
//
//    public void unregister(String location, String email) {
//        db.collection("sites")
//                .document(location).update("participants", FieldValue.arrayRemove(email));
//    }
//
//
//    public void updateNewUser(String email){
//        db.collection("accounts")
//                .document(email).update("newUser", false);
//    }
//
//    public void updateSiteTrash(String location, int trashCollected){
//        db.collection("sites")
//                .document(location).update("trashCollected", trashCollected);
//    }
//
//    public void updateParticipant(String location, String email) {
//        db.collection("sites")
//                .document(location).update("participants", FieldValue.arrayUnion(email));
//    }
//
//    public void deleteSite(String location){
//        db.collection("sites")
//                .document(location).delete();
//    }

}
