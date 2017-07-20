//
//  CurrencyConversion.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/12/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit

class CurrencyConversion {
    var fromTag: String
    var toTag: String
    
    var pushEnabled: Bool
    
    var increaseValue: Float32?
    var decreaseValue: Float32?
    
    
    //MARK: Initialization
    
    init?(fromTag: String, toTag: String, pushEnabled: Bool, increaseValue: Float32?, decreaseValue: Float32?) {

        self.fromTag = fromTag
        self.toTag = toTag
        self.pushEnabled = pushEnabled
        self.increaseValue = increaseValue
        self.decreaseValue = decreaseValue
        
    }
    
}
