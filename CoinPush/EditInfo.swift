//
//  EditInfo.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/20/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit

struct EditInfo {
    var Pair: CurrencyConversion
    var currentPrice: String
    var currentDelta: String
    
    init(pair:CurrencyConversion, currentPrice: String, currentDelta: String) {
        self.Pair = pair
        self.currentPrice = currentPrice
        self.currentDelta = currentDelta
    }
}
