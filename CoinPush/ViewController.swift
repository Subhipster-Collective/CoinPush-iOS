//
//  ViewController.swift
//  CoinPush
//
//  Created by Austin Conlon on 6/27/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var increasedValue: UITextField!
    @IBOutlet weak var decreasedValue: UITextField!
    @IBAction func submitUserData(_ sender: Any) {
        
        let device = UIDevice()
        let uid = device.identifierForVendor
        
        var requestJson = "{ \"Type\" : \"update\", \"Coin\" : \"ETH\" ,\"ID\" : \"\(uid!)\", \"Pref\" : [\(increasedValue!.text!), \(decreasedValue!.text!)] , \"Push\" : true }"
        
        print(requestJson)
        var request = URLRequest(url: URL(string: "http://bijanm.me/handle_request")!)
        request.httpMethod = "POST"
        request.httpBody = requestJson.data(using: .utf8)
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                // Check for fundamental networking error
                print("error=\(String(describing: error))")
                return
            }
            if let httpStatus = response as? HTTPURLResponse , httpStatus.statusCode != 200 {
                // Check for HTTP errors
                print("statusCode should be 200, but is \(httpStatus.statusCode)")
                print("response = \(String(describing: response))")
            }
            let responseString = String(data: data, encoding: .utf8)
            print("responseString = \(String(describing: responseString))")
        }
        task.resume()
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}

