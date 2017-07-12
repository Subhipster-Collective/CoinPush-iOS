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

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    
    @IBOutlet weak var fromTextField: UITextField!
    
    @IBOutlet weak var toTextField: UITextField!
    
    
    let fromOptions = ["Ethereum (ETH)","Bitcoin (BTC)"]
    
    let toOptions = ["🇺🇸 U.S Dollar (USD)", "🇪🇺 Euro (EUR)"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let fromPickerView = UIPickerView()
        fromPickerView.tag = 1
        
        let toPickerView = UIPickerView()
        toPickerView.tag = 2
        
        fromPickerView.delegate = self
        toPickerView.delegate = self
        
        fromTextField.inputView = fromPickerView
        toTextField.inputView = toPickerView
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1{
            return fromOptions.count
        }
        else if pickerView.tag == 2{
            return toOptions.count
        }
        return 0
    }
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if pickerView.tag == 1{
            return fromOptions[row]
        }
        else if pickerView.tag == 2{
            return toOptions[row]
        }
        return nil
    }
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView.tag == 1{
            fromTextField.text = fromOptions[row]
        }
        else if pickerView.tag == 2{
            toTextField.text = toOptions[row]
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
    }

}

