import SwiftUI

@main
struct iOSApp: App {
    @StateObject private var viewModel = CheckConnectionViewModel()

        var body: some Scene {
            WindowGroup {
                ConnectionStatusView().environmentObject(viewModel)
            }
        }
}
