//
//  EditCurrencyViewController.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/20/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit

class EditCurrencyViewController: UIViewController, UITextFieldDelegate {
    //MARK: attributes
    
    @IBOutlet weak var identiferLabel: UILabel!
    @IBOutlet weak var conversionLabel: UILabel!
    @IBOutlet weak var pushSwitch: UISwitch!
    
    @IBOutlet weak var increaseLabel: UILabel!
    @IBOutlet weak var increaseValue: UITextField!
    
    @IBOutlet weak var decreaseLabel: UILabel!
    @IBOutlet weak var decreaseValue: UITextField!
    
    var pair: EditInfo?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let pair = pair {
            //initialize top labels
            identiferLabel?.text = "\(pair.Pair.fromTag) to \(pair.Pair.toTag)"
            conversionLabel?.text = "\(helper.symbolDict[(pair.Pair.fromTag)]!)1 = \(pair.currentPrice)"
            let currency = pair.Pair.fromTag
            
            //deal with advanced push setup
            increaseLabel.text! = "When \(currency) increases by "
            decreaseLabel.text! = "When \(currency) decreases by "
            
            initializeTextBoxs(pair: pair)
            
            increaseValue.delegate = self
            decreaseValue.delegate = self
            
            increaseLabel.tag = 1
            increaseValue.tag = 1
            decreaseLabel.tag = 2
            decreaseValue.tag = 2
            
            //pushswitch setup
            pushSwitch.isOn = (pair.Pair.pushEnabled)
            if (pushSwitch.isOn){
                revealAdvancedPush()
            } else {
                hideAdvancedPush()
            }
        }
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
        
    }
    
    
    
    //MARK: Actions
    
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
    private func initializeTextBoxs(pair: EditInfo) {
        if let increase = pair.Pair.increaseValue {
            increaseValue.text? = "\(increase)%"
            increaseLabel.isEnabled = true
        } else {
            increaseValue.placeholder = "0.00%"
            increaseLabel.isEnabled = false
        }
        if let decrease = pair.Pair.decreaseValue {
            decreaseValue.text? = "\(decrease)%"
            decreaseLabel.isEnabled = true
        } else {
            decreaseValue.placeholder = "0.00%"
            decreaseLabel.isEnabled = false
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
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        pair?.Pair.pushEnabled = pushSwitch.isOn
        if let val = Float((increaseValue?.text?.components(separatedBy: "%")[0])!) {
            pair?.Pair.increaseValue = val
        }
        if let val = Float((decreaseValue?.text?.components(separatedBy: "%")[0])!) {
            pair?.Pair.decreaseValue = val
        }
        
    }
}
