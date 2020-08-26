package alexander.skornyakov.mymoviestracker.ui

import alexander.skornyakov.mymoviestracker.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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