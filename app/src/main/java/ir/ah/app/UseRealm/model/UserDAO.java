package ir.ah.app.UseRealm.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserDAO {
    private Realm realm;

    public UserDAO() {
        realm = Realm.getDefaultInstance();
    }

    public void saveUser(User user) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User realmObject = realm.createObject(User.class, user.getEmail());
                realmObject.setName(user.getName());


            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                Log.e("REALM_TAG", "Success");

            }
        }, new Realm.Transaction.OnError() {

            @Override
            public void onError(Throwable error) {
                Log.e("REALM_TAG", "error");
                Log.e("REALM_TAG", "error :" + error.getMessage());
            }
        });
    }

    public void UpdateUser(User user){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });

    }

    public void close() {
        realm.close();
    }

    public RealmResults<User> getAllUser() {
        RealmResults<User> realmResults = realm.where(User.class).findAll();
        return realmResults;
    }
    public RealmResults<User> getAllUser(String email,String name) {
        RealmResults<User> realmResults =
                realm.where(User.class)
                        .equalTo("email",email)
                        .or()
                        .equalTo("name",name)
                        .findAll();
        return realmResults;
    }
    public RealmResults<User> getAllUserAND(String name,String email) {
        RealmResults<User> realmResults =
                realm.where(User.class)
                        .equalTo("email",email)
                        .equalTo("name",name)
                        .findAll();
        return realmResults;
    }
    public RealmResults<User> getAllUserGroup(String name,String email) {
        RealmResults<User> realmResults =
                realm.where(User.class)
                        .beginGroup()
                        .not()
                        .equalTo("email",email)
                        .or()
                        .equalTo("name",name)
                        .endGroup()
                        .findAll();
        return realmResults;
    }

    public User getUserByEmail(String email) {
        User user = realm.where(User.class).equalTo("email", email).findFirst();
        return user;
    }

    public RealmResults<User> getUsersByName(String name) {
        RealmResults<User> user = realm.where(User.class).equalTo("name", name).findAll();
        return user;
    }

    public void deleteAll() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getAllUser().deleteAllFromRealm();
            }
        });
    }
    public void deleteByEmail(String email){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getUserByEmail(email).deleteFromRealm();
            }
        });
    }

}
