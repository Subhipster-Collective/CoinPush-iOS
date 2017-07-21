//
//  CurrencyTableViewController.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/13/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit
import Alamofire

class CurrencyTableViewController: UITableViewController {
    
    var currencyPairs = [CurrencyConversion]() {
        didSet {
            if (currencyPairs.count > 0){
                let request = structureRequest()
                updatePriceLabels(request: request)
            }
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.leftBarButtonItem = editButtonItem

        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false
        
        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    //MARK: Actions
    
    @IBAction func refreshPrices(_ sender: UIBarButtonItem) {
        if (currencyPairs.count > 0){
            let request = structureRequest()
            updatePriceLabels(request: request)
        }
    }
    
    @IBAction func unwindToCurrencyList(sender: UIStoryboardSegue) {
        
        if let source = sender.source as?
            ViewController, let conversionData = source.conversion {
            
            for pair in currencyPairs {
                if (conversionData.fromTag == pair.fromTag && conversionData.toTag == pair.toTag){
                    return
                }
            }
            //add a new conversion to list
            let newIndexPath = IndexPath(row: currencyPairs.count, section: 0)
            currencyPairs.append(conversionData)
            tableView.insertRows(at: [newIndexPath], with: .automatic)
            
        } else if let source = sender.source as? EditCurrencyViewController, let conversionData = source.pair {
            let selectedIndexPath = tableView.indexPathForSelectedRow
            // Update an existing meal.
            currencyPairs[(selectedIndexPath?.row)!] = conversionData.Pair
            //tableView.reloadRows(at: [selectedIndexPath!], with: .none)
            
        }
    }
    
    // MARK: - Table view data source and delegate

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return currencyPairs.count
    }
    
    
   override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let identifier = "CurrencyTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: identifier, for: indexPath) as? CurrencyTableViewCell else {
            fatalError("The dequeued cell is not an instance of CurrencyTableViewCell.")
        }
    
    
        let conversionData = currencyPairs[indexPath.row]
        
        cell.coinIcon.image =  UIImage(named: conversionData.fromTag)
        cell.titleLabel.text = helper.labelDict[conversionData.fromTag]
        cell.priceLabel.text = helper.symbolDict[conversionData.toTag]! + "0.00"
        cell.deltaLabel.text = "0.00" + "%"
        
        return cell
    }
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            currencyPairs.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }
    }
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    
    //MARK: private methods
    private func updatePriceLabels(request: String){
        Alamofire.request(request, method: .post, parameters: nil, encoding: JSONEncoding.default)
            .responseJSON { response in
                print(response)
                //to get status code
                if let status = response.response?.statusCode {
                    switch(status){
                    case 200:
                        print("success")
                    default:
                        print("error with response status: \(status)")
                    }
                }
                //to get JSON return value
                if let result = response.result.value {
                    let JSON = result as! NSDictionary
                    let dataTree = JSON["RAW"] as! NSDictionary
                    
                    for (i, pair) in self.currencyPairs.enumerated(){
                        let cell = self.tableView.cellForRow(at: IndexPath(row: i, section: 0)) as? CurrencyTableViewCell
                        let toValues = (dataTree[pair.fromTag] as? NSDictionary)?[pair.toTag] as? NSDictionary!
                        
                         //update the labels
                        cell?.priceLabel.text = helper.symbolDict[pair.toTag]! + "\(toValues!["PRICE"]!)"
                        
                        let pct = "\(toValues!["CHANGEPCT24HOUR"]!)"
                        var index = pct.index(pct.startIndex, offsetBy: 5)
                        cell?.deltaLabel.text =  pct.substring(to: index) + "%"
                        
                        index = pct.index(pct.startIndex, offsetBy: 1)
                        if (pct.substring(to: index) == "-"){
                            cell?.deltaLabel.textColor = UIColor.red
                        } else {
                            cell?.deltaLabel.textColor = UIColor.green
                        }
                        
                    }
                
                }
        }
        
    }
    private func structureRequest() -> String {
        var fromString = ""
        var toString = ""
        
        for (i, pair) in currencyPairs.enumerated() {
            if (i < currencyPairs.count - 1 ) {
                fromString += pair.fromTag + ","
                toString += pair.toTag + ","
            } else {
                fromString += pair.fromTag
                toString += pair.toTag
            }
        }
        let requestUrl = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=\(fromString)&tsyms=\(toString)"
        return requestUrl
    }
    

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        switch(segue.identifier ?? "") {
            case "AddCurrency":
                print("adding new item")
            case "ShowDetail":
                guard let editCurrencyView = segue.destination as? EditCurrencyViewController else {
                    fatalError("Unexpected destination: \(segue.destination)")
                }
                guard let selectedCell = sender as? CurrencyTableViewCell else {
                    fatalError("Unexpected sender: \(sender)")
                }
                guard let index = tableView.indexPath(for: selectedCell) else {
                    fatalError("The Selected cell ain't in the table m8")
                }
                
                let conversion  = currencyPairs[index.row]
                let price = selectedCell.priceLabel.text!
                let delta = selectedCell.deltaLabel.text!
                
                let passingPair = EditInfo(pair: conversion, currentPrice: price, currentDelta: delta)
                editCurrencyView.pair = passingPair
            
        default:
            fatalError("Unexpected Segue Identifier; \(segue.identifier)")
        
        }
    }
 

}
