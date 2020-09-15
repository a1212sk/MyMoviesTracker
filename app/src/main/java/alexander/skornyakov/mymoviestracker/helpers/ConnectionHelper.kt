package alexander.skornyakov.mymoviestracker.helpers

import android.content.Context
import android.net.ConnectivityManager

object ConnectionHelper {
    fun internetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ani = cm.activeNetworkInfo
        return ani?.isConnected == true
    }
}