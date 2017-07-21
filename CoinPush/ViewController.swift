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
        
        //setup initial visibility
        pushLabel.isEnabled = false
        pushSwitch.isEnabled = false
        saveButton.isEnabled = false
        increaseLabel.isEnabled = false
        decreaseLabel.isEnabled = false
        
        hideAdvancedPush()
        
        //tag fields for distinguishing later
        let fromPickerView = UIPickerView()
        fromPickerView.tag = 1
        
        let toPickerView = UIPickerView()
        toPickerView.tag = 2
        
        increaseLabel.tag = 1
        increaseValue.tag = 1
        decreaseLabel.tag = 2
        decreaseValue.tag = 2
        
        //assign delegate
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
    
    
    @IBAction func cancel(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
        
    }
    
    
    // This method lets you configure a view controller before it's presented.
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        // Configure the destination view controller only when the save button is pressed.
        guard let button = sender as? UIBarButtonItem, button === saveButton else {
            print("The save button was not pressed, cancelling")
            return
        }
        
    
        
        
        
        let fromTag = helper.getCurrencyIdentifier(rawText: fromTextField!.text!)
        let toTag = helper.getCurrencyIdentifier(rawText: toTextField!.text!)
        
        let pushEnabled1 = pushSwitch.isOn
        var increase : Float32!
        var decrease : Float32!
        
        if let val = Float((increaseValue?.text?.components(separatedBy: "%")[0])!) {
            increase = val
        }
        if let val = Float((decreaseValue?.text?.components(separatedBy: "%")[0])!) {
            decrease = val
        }
        
        // Set the meal to be passed to MealTableViewController after the unwind segue.
        conversion = CurrencyConversion(fromTag: fromTag, toTag: toTag, pushEnabled: pushEnabled1, increaseValue: increase, decreaseValue: decrease)

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
            saveButton.isEnabled = enableBool
            let currency = helper.getCurrencyIdentifier(rawText: fromTextField!.text!)
            increaseLabel.text? = "When \(currency) increases by "
            decreaseLabel.text? = "When \(currency) decreases by "
        }
        
    }
    
    
    //MARK: UITextField Delegate
    
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        let value = textField.text!.components(separatedBy: "%")
        let numVal =  Float(value[0])
        
        if (numVal == 0 || numVal == nil) {
            if (textField.tag == 1) {
                increaseLabel.isEnabled = false
            } else {
                decreaseLabel.isEnabled = false
            }
            textField.placeholder? = "0.00%"
            
        } else {
            if (textField.tag == 1) {
                increaseLabel.isEnabled = true
            } else {
                decreaseLabel.isEnabled = true
            }
            textField.text? += "%"
        }
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

