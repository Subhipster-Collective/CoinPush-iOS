//
//  CurrencyConversion.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/12/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit

class CurrencyConversion : NSObject, NSCoding{
    
    
    //types
    struct PropertyKey {
        static let fromTag = "fromTag"
        static let toTag = "toTag"
        static let pushEnabled = "pushEnabled"
        static let increaseValue = "increaseValue"
        static let decreaseValue = "decreaseValue"
    }
    
    
    //MARK: Archiving Paths
    static let DocumentsDirectory = FileManager().urls(for: .documentDirectory, in: .userDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.appendingPathComponent("currencyPairs")
    
    
    //properties
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
    //MARK: NSCoding
    required convenience init?(coder aDecoder: NSCoder) {
        // The name is required. If we cannot decode a name string, the initializer should fail.
        guard let fromTag = aDecoder.decodeObject(forKey: PropertyKey.fromTag) as? String else {
            print("Unable to decode the fromTag for a pair object.")
            return nil
        }
        guard let toTag = aDecoder.decodeObject(forKey: PropertyKey.toTag) as? String else {
            print("Unable to decode the toTag for a pair object.")
            return nil
        }
        guard let pushEnabled = aDecoder.decodeObject(forKey: PropertyKey.pushEnabled) as? String else {
            print("Unable to decode the pushEnabled for a pair object.")
            return nil
        }
        let increaseValue = aDecoder.decodeObject(forKey: PropertyKey.increaseValue) as? Float32
        let decreaseValue = aDecoder.decodeObject(forKey: PropertyKey.decreaseValue) as? Float32
        
        if pushEnabled == "true"{
            self.init(fromTag: fromTag,toTag: toTag,pushEnabled: true, increaseValue: increaseValue,decreaseValue: decreaseValue)
        } else {
            self.init(fromTag: fromTag,toTag: toTag,pushEnabled: false, increaseValue: increaseValue,decreaseValue: decreaseValue)
        }
        
    }
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(fromTag, forKey: PropertyKey.fromTag)
        aCoder.encode(toTag, forKey: PropertyKey.toTag)
        aCoder.encode("\(pushEnabled)", forKey: PropertyKey.pushEnabled)
        aCoder.encode(increaseValue, forKey: PropertyKey.increaseValue)
        aCoder.encode(decreaseValue, forKey: PropertyKey.decreaseValue)
    }
}
