import SwiftUI
import shared

struct ConnectionStatusView: View {
    @EnvironmentObject var viewModel: CheckConnectionViewModel

    var body: some View {
        VStack {
            if(viewModel.state){
                Text("You are Connected")
                    .foregroundColor(Color.green)
            }else {
                Text("You are DisConnected")
                    .foregroundColor(Color.red)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ConnectionStatusView()
    }
}
