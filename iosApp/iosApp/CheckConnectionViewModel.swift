//
//  ViewModel.swift
//  iosApp
//
//  Created by Pranay Patel on 30/08/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

final class CheckConnectionViewModel: NSObject, ObservableObject {
    private let connectivityCheck: ConnectivityCheck = ConnectivityCheck()
    
    @Published var state: Bool = false
    
    override init() {
        super.init()

        connectivityCheck.startListener(onConnectionStatus: { status in
            self.state = status.boolValue
        })

    }
}

