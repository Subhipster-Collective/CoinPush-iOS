//
//  CurrencyTableViewCell.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 7/12/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit

class CurrencyTableViewCell: UITableViewCell {
    
    //MARK: Properties
    @IBOutlet weak var coinIcon: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBOutlet weak var priceLabel: UILabel!
    @IBOutlet weak var deltaLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
