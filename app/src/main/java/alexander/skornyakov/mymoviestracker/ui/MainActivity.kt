package alexander.skornyakov.mymoviestracker.ui

import alexander.skornyakov.mymoviestracker.R
import alexander.skornyakov.mymoviestracker.repository.FbRepository
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var fbRepo: FbRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavView.setupWithNavController(findNavController(R.id.navHost))
        bottomNavView.setOnNavigationItemReselectedListener { /* NOP */ }
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