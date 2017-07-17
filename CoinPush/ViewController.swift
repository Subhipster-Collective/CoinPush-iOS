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
import os.log

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource,UITextFieldDelegate {
    
    
    @IBOutlet weak var fromTextField: UITextField!
    @IBOutlet weak var toTextField: UITextField!
    
    @IBOutlet weak var pushLabel: UILabel!
    @IBOutlet weak var pushSwitch: UISwitch!
    
    @IBOutlet weak var increaseLabel: UILabel!
    @IBOutlet weak var decreaseLabel: UILabel!
    
    @IBOutlet weak var increaseValue: UITextField!
    @IBOutlet weak var decreaseValue: UITextField!
    
    @IBOutlet weak var saveButton: UIBarButtonItem!
    
    
    var conversion: CurrencyConversion?
    
    let fromOptions = ["Ethereum (ETH)","Bitcoin (BTC)"]
    
    let toOptions = ["🇺🇸 U.S Dollar (USD)", "🇪🇺 Euro (EUR)"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        pushLabel.isEnabled = false
        pushSwitch.isEnabled = false
        
        hideAdvancedPush()
        
        let fromPickerView = UIPickerView()
        fromPickerView.tag = 1
        
        let toPickerView = UIPickerView()
        toPickerView.tag = 2
        
        fromPickerView.delegate = self
        toPickerView.delegate = self
        increaseValue.delegate = self
        decreaseValue.delegate = self
        
        fromTextField.inputView = fromPickerView
        toTextField.inputView = toPickerView
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        updatePushStates()
        
    }
    
    //MARK: Navigation
    // This method lets you configure a view controller before it's presented.
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        // Configure the destination view controller only when the save button is pressed.
        guard let button = sender as? UIBarButtonItem, button === saveButton else {
            print("The save button was not pressed, cancelling")
            return
        }
        
        let fromCurrency = fromTextField.text ?? ""
        let toCurrency = toTextField.text ?? ""
        let pushEnabled1 = pushSwitch.isOn
        var increase : Float32?
        var decrease : Float32?
        
        if let val = increaseValue?.text {
            increase = Float(val)
        }
        if let val = decreaseLabel?.text {
            decrease = Float(val)
        }
        
        // Set the meal to be passed to MealTableViewController after the unwind segue.
        conversion = CurrencyConversion(fromCurrency: fromCurrency, toCurrency: toCurrency, pushEnabled: pushEnabled1, increaseValue: increase, decreaseValue: decrease)
    }
    
    
    
    
    
    
    //MARK: Action methods
    

    @IBAction func isSwitched(_ sender: UISwitch) {
        if sender.isOn {
            revealAdvancedPush()
        } else {
            hideAdvancedPush()
        }
    }
    
    
    
    //MARK: Private Functions
    private func hideAdvancedPush(){
        increaseLabel.isHidden = true
        decreaseLabel.isHidden = true
        increaseValue.isHidden = true
        decreaseValue.isHidden = true
    }
    private func revealAdvancedPush(){
        increaseLabel.isHidden = false
        decreaseLabel.isHidden = false
        increaseValue.isHidden = false
        decreaseValue.isHidden = false
    }
    
    private func updatePushStates() {
        let text1 = fromTextField.text ?? ""
        let text2 = toTextField.text ?? ""
        
        let enableBool = !text1.isEmpty && !text2.isEmpty
        if enableBool {
            pushSwitch.isEnabled = enableBool
            pushLabel.isEnabled = enableBool
            var currency = fromTextField.text?.components(separatedBy: " ")
            increaseLabel.text? = "When \(currency![1]) increases by "
            decreaseLabel.text? = "When \(currency![1]) decreases by "
        }
        
    }
    
    //MARK: UITextField Delegate
    
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        textField.text? += "%"
        
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if let data = textField.text{
            var value = data.components(separatedBy: "%")
            textField.text? = value[0]
        }
    }
    
    
    //MARK: UIPickerView Delegate
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
    

}

