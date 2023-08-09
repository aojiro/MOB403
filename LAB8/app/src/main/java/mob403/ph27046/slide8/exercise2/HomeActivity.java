package mob403.ph27046.slide8.exercise2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mob403.ph27046.slide8.R;
import mob403.ph27046.slide8.exercise1.SettingsActivity;
import mob403.ph27046.slide8.exercise1.SignInActivity;
import mob403.ph27046.slide8.exercise2.adapters.ItemAdapter;
import mob403.ph27046.slide8.exercise2.models.Items;
import mob403.ph27046.slide8.helpers.MyCallback;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rev_item;

    void initUi() {
        rev_item = findViewById(R.id.rev_item);
    }

    private FirebaseAuth.AuthStateListener authStateListener;
    private ChildEventListener childEventListener;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private ItemAdapter adapter;
    private static List<Items> list;
    private List<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUi();

        //  _firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        authListener();
        childListener();

        //  _ui
        list = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new ItemAdapter(list, new MyCallback() {
            @Override
            public void handle() {

            }

            @Override
            public void onClickUpdate(Items items, int position) {
                MyCallback.super.onClickUpdate(items, position);
                items.setDone(false);
                updateItem(items, position);
            }

            @Override
            public void onClickDelete(int position) {
                MyCallback.super.onClickDelete(position);
                deleteItem(position);
            }
        });
        rev_item.setHasFixedSize(true);
        rev_item.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        refreshRecyclerView();


    }

    //
    void authListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                checkSignedIn(user);
            }
        };
    }

    void childListener() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("__onChildAdded", snapshot.getKey() + ":" + snapshot.getValue().toString());
                String addedKey = snapshot.getKey();
                if (!keys.contains(addedKey)) {
                    Items items = snapshot.getValue(Items.class);
                    list.add(items);
                    keys.add(addedKey);
                    refreshRecyclerView();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("__onChildChanged", snapshot.getKey() + ":" + snapshot.getValue().toString());
                String changedKey = snapshot.getKey();
                int changedIndex = keys.indexOf(changedKey);
                if (changedIndex > -1) {
                    Items items = snapshot.getValue(Items.class);
                    list.set(changedIndex, items);
                    refreshRecyclerView();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.e("__onChildRemoved", snapshot.getKey() + ":" + snapshot.getValue().toString());
                String removedKey = snapshot.getKey();
                int removedIndex = keys.indexOf(removedKey);
                if (removedIndex > -1) {
                    list.remove(removedIndex);
                    refreshRecyclerView();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    void checkSignedIn(FirebaseUser user) {
        if (user == null) {
            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            finish();
        } else {
            //tv_user.setText("User: " + user.getEmail());
        }
    }

    //
    void refreshRecyclerView() {
        adapter.notifyDataSetChanged();
        rev_item.setAdapter(adapter);
    }

    //
    void addItem(Items items) {
        //  _create new item at /user/$userId/$itemKey
        String key = databaseReference.child("users").child(user.getUid()).push().getKey();
        Map<String, Object> itemValues = items.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + user.getUid() + "/" + key, itemValues);
        databaseReference.updateChildren(childUpdates);
    }

    void deleteItem(int position) {
        String clickedKey = keys.get(position);

        databaseReference.child("users").child(user.getUid()).child(clickedKey).removeValue();
    }

    void updateItem(Items items, int position) {
        String clickedKey = keys.get(position);

        databaseReference.child("users").child(user.getUid()).child(clickedKey).setValue(items);
    }

    //Request for auth on start
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    //Request for stop auth on stop
    @Override
    public void onStop() {
        super.onStop();
        if (auth != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    //Request to attach listener for database on resume
    @Override
    public void onResume() {
        super.onResume();
        if (user != null && databaseReference != null)

            databaseReference.child("users").child(user.getUid()).addChildEventListener(childEventListener);
    }

    //Request to detach listener for database on pause
    @Override
    public void onPause() {
        super.onPause();
        if (databaseReference != null)
            databaseReference.removeEventListener(childEventListener);
    }

    public void addItemDemo(View view) {
        for (int i = 1; i <= 10; i++) {
            addItem(new Items("uid_" + i, "title_" + i, "author_" + i, i % 2 == 0));
        }
    }
}