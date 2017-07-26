//
//  helper.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/16/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import FirebaseDatabase
import Firebase
import UIKit


extension String {
    func toBool() -> Bool? {
        switch self {
        case "True", "true", "yes", "1":
            return true
        case "False", "false", "no", "0":
            return false
        default:
            return nil
        }
    }
}

class helper {
    

    static var labelDict: [String:String] = ["ETH": "Ethereum","BTC": "Bitcoin", "ETC" :"Ethereum Classic", "DGB": "Digital Dash" ,"LTC": "Litecoin","USD": "U.S Dollars", "EUR": "Euros", "CNY" : "Chinese Yuan", "GBP" : "Pounds"]
    
    static var symbolDict: [String: String] = ["USD" : "$",  "EUR" : "€", "BTC" :"Ƀ", "ETH" : "Ξ","LTC" : "Ł","ETC" : "⟠", "CNY" : "¥", "GBP" : "£"]
    
    
    static func getCurrencyIdentifier(rawText: String) -> String{
        var returnExpression : String
        switch rawText {
            case "Ethereum (ETH)":
                returnExpression = "ETH"
            case "Bitcoin (BTC)":
                returnExpression = "BTC"
            case "Dash (DGB)":
                returnExpression = "DGB"
            case "Ethereum Classic (ETC)":
                returnExpression = "ETC"
            case "Litecoin (LTC)":
                returnExpression = "LTC"
            case "🇺🇸 U.S Dollar (USD)":
                returnExpression = "USD"
            case "🇪🇺 Euro (EUR)":
                returnExpression = "EUR"
            case "🇨🇳 Chinese Yuan (CNY)":
                returnExpression = "CNY"
            case "🇬🇧 British Pound (GBP)":
                returnExpression = "GBP"
            default:
                fatalError("passed string had no match")
            
            }
        return returnExpression
    }
    static func writeUserData(Data: [String:[String:String]]){
        let ref = Database.database().reference()
        for conversion in Data.keys {
            let pushDecreased = ["pushDecreased" : Data[conversion]!["thresholdDecreased"]!.toBool()!]
            let pushIncreased = ["pushIncreased" : Data[conversion]!["thresholdIncreased"]!.toBool()!]
            let thresholdIncreased = ["thresholdIncreased" : Float(Data[conversion]!["pushIncreased"]!)!]
            let thresholdDecreased = ["thresholdDecreased" : Float(Data[conversion]!["pushDecreased"]!)!]
            
            let input = ["pushDecreased": pushDecreased, "pushIncreased": pushIncreased, "thresholdIncreased": thresholdIncreased, "thresholdDecreased": thresholdDecreased] as [String : Any]
            
            ref.child("users").child(Passwords.userID).child("conversions").child(conversion).setValue(input)
        }
        
    }
    static func deletePair(pairString: String) {
        let ref = Database.database().reference()
        ref.child("users").child(Passwords.userID).child("conversions").child(pairString).removeValue()
  }
    
    
}
