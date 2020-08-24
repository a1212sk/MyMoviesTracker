package alexander.skornyakov.mymoviestracker

import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        if(!isUserSignedIn()){
            signInAnonymously()
        }
    }

    private fun isUserSignedIn() : Boolean{
        return auth?.currentUser != null
    }

    private fun signInAnonymously(){
        auth.signInAnonymously().addOnCompleteListener {
            if(it.isSuccessful){
                println("Logged in ${auth.currentUser?.uid}")
            }
            else{
                println("Something goes wrong! Quitting...")
                finish()
            }
        }
    }

}