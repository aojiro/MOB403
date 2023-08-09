package mob403.ph27046.slide8.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mob403.ph27046.slide8.exercise2.models.Items;

public interface MyCallback {
    void handle();
    default void onClickUpdate(Items items, int position){}
    default void onClickDelete( int position){}
}
