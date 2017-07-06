//
//  ViewController.swift
//  CoinPush
//
//  Created by Austin Conlon on 6/27/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit
import FirebaseDatabase
import GoogleMobileAds

class ViewController: UIViewController {
    @IBOutlet weak var increasedValue: UITextField!
    @IBOutlet weak var decreasedValue: UITextField!
    @IBOutlet weak var bannerView: GADBannerView!
    let device = UIDevice()
    var ref: DatabaseReference!

    @IBAction func submitUserData(_ sender: Any) {
        
        let uid = device.identifierForVendor
        ref = Database.database().reference()

        
        
        var requestJson = "{ \"Type\" : \"update\", \"Coin\" : \"ETH\" ,\"ID\" : \"\(uid!)\", \"Pref\" : [\(increasedValue!.text!), \(decreasedValue!.text!)] , \"Push\" : true }"
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        bannerView.adUnitID = "ca-app-pub-3940256099942544/6300978111"
        bannerView.rootViewController = self
        bannerView.load(GADRequest())
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}

