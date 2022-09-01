import com.pranay.checkconnectivitykmp.connectivityCheck
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
  val container = document.createElement("div")
  document.body!!.appendChild(container)

  connectivityCheck.startListener {
      console.log("Status",it)
      val welcome = ConnectionStatusView.create {
        name = "You are " + if (it) "Connected" else "DisConnected"
          isConnected = it
      }
      createRoot(container).render(welcome)
    }
}