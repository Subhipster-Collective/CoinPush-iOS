//
//  CurrencyConversion.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/12/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit

class CurrencyConversion {
    var fromCurrency: String
    var toCurrency: String
    var pushEnabled: Bool
    
    var increaseValue: Float32?
    var decreaseValue: Float32?
    
    
    //MARK: Initialization
    
    init?(fromCurrency: String, toCurrency: String, pushEnabled: Bool, increaseValue: Float32?, decreaseValue: Float32?) {

        self.fromCurrency = fromCurrency
        self.toCurrency = toCurrency
        self.pushEnabled = pushEnabled
        self.increaseValue = increaseValue
        self.decreaseValue = decreaseValue
        
    }
    
}
