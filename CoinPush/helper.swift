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

    static var labelDict: [String:String] = ["ETH": "Ethereum","BTC": "Bitcoin", "ETC" :"Ethereum Classic", "DASH": "Dash" ,"LTC": "Litecoin","DNT" :"District0x Token"  ,"USD": "U.S Dollars", "EUR": "Euros", "CNY" : "Chinese Yuan", "GBP" : "Pounds", "OMG" : "OmiseGo Token", "ZRX" : "0x Token"]
    
    static var symbolDict: [String: String] = ["USD" : "$",  "EUR" : "€", "BTC" :"Ƀ", "ETH" : "Ξ","LTC" : "Ł","ETC" : "⟠", "CNY" : "¥", "GBP" : "£"]
    
    
    static func getCurrencyIdentifier(rawText: String) -> String!{
        var returnExpression : String
        switch rawText {
            case "Ethereum (ETH)":
                returnExpression = "ETH"
            case "Bitcoin (BTC)":
                returnExpression = "BTC"
            case "Dash (DASH)":
                returnExpression = "DASH"
            case "Ethereum Classic (ETC)":
                returnExpression = "ETC"
            case "Litecoin (LTC)":
                returnExpression = "LTC"
            case "District0x Network Token (DNT)":
                returnExpression = "DNT"
            case "OmiseGo Token (OMG)":
                returnExpression = "OMG"
            case "0x Token (ZRX)":
                returnExpression = "ZRX"
            case "🇺🇸 U.S Dollar (USD)":
                returnExpression = "USD"
            case "🇪🇺 Euro (EUR)":
                returnExpression = "EUR"
            case "🇨🇳 Chinese Yuan (CNY)":
                returnExpression = "CNY"
            case "🇬🇧 British Pound (GBP)":
                returnExpression = "GBP"
            default:
                return nil
            
            }
        return returnExpression
    }
    static func writeUserData(Data: [String:[String:String]]){
        let ref = Database.database().reference()
        for conversion in Data.keys {
            let pushDecreased = Data[conversion]!["thresholdDecreased"]!.toBool()!
            let pushIncreased = Data[conversion]!["thresholdIncreased"]!.toBool()!
            let thresholdIncreased = Float(Data[conversion]!["pushIncreased"]!)!
            let thresholdDecreased = Float(Data[conversion]!["pushDecreased"]!)!
            
            let input = ["pushDecreased": pushDecreased, "pushIncreased": pushIncreased, "thresholdIncreased": thresholdIncreased, "thresholdDecreased": thresholdDecreased] as [String : Any]
            
            ref.child("users").child(Passwords.userID).child("conversionPrefs").child(conversion).setValue(input)
            ref.child("users").child(Passwords.userID).updateChildValues(["token" : Passwords.userRegistration])
            ref.child("users").child(Passwords.userID).child("timeLastPushed").updateChildValues([conversion : 0])
        }
        
    }
    static func deletePair(pairString: String) {
        let ref = Database.database().reference()
        ref.child("users").child(Passwords.userID).child("conversionPrefs").child(pairString).removeValue()
        ref.child("users").child(Passwords.userID).child("timeLastPushed").child(pairString).removeValue()
  }
    
    
}
