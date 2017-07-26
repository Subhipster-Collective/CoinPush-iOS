//
//  AppDelegate.swift
//  CoinPush
//
//  Created by Bijan Massoumi on 6/27/17.
//  Copyright © 2017 Goods and Services. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth

import UserNotifications
import FirebaseInstanceID
import FirebaseMessaging

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate,
UNUserNotificationCenterDelegate, MessagingDelegate {
    /// This method will be called whenever FCM receives a new, default FCM token for your
    /// Firebase project's Sender ID.
    /// You can send this token to your application server to send notifications to this device.


 
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions:
        [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Use Firebase library to configure APIs.
        // Initialize the Google Mobile Ads SDK.

        
        if #available(iOS 10.0, *) {
            // For iOS 10 display notification (sent via APNS)
            UNUserNotificationCenter.current().delegate = self
            let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
            UNUserNotificationCenter.current().requestAuthorization(
                options: authOptions,
                completionHandler: {_, _ in })
            // For iOS 10 data message (sent via FCM
            Messaging.messaging().remoteMessageDelegate = self as! MessagingDelegate
        } else {
            let settings: UIUserNotificationSettings =
                UIUserNotificationSettings(types: [.alert, .badge, .sound], categories: nil)
            application.registerUserNotificationSettings(settings)
        }
        
        application.registerForRemoteNotifications()
        FirebaseApp.configure()
        
        GADMobileAds.configure(withApplicationID: Passwords.adMobID)
        
        Auth.auth().signInAnonymously() { (user, error) in
            Passwords.userID = user!.uid
        }

        
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    //MARK: FIRMessagingDelegate
    
    // The callback to handle data message received via FCM for devices running iOS 10 or above.
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable: Any]) {
        
        if let messageID = userInfo["gcm_message_id"] {
            print("Message ID: \(messageID)")
        }
        
        // Print full message.
        print(userInfo)
        
        let alert = UIAlertController(title: "FireBase", message:"didReceiveRemoteNotification", preferredStyle: UIAlertControllerStyle.alert)
        
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
        
        // show the alert
        self.window?.rootViewController?.present(alert, animated: true, completion: nil)
    }
    
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable: Any],
                     fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        
        if let messageID = userInfo["gcm_message_id"] {
            print("Message ID: \(messageID)")
        }
        
        // Print full message.
        print(userInfo)
        
        
        let alert = UIAlertController(title: "FireBase", message:"didReceiveRemoteNotification fetchCompletionHandler", preferredStyle: UIAlertControllerStyle.alert)
        
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
        
        // show the alert
        self.window?.rootViewController?.present(alert, animated: true, completion: nil)
        
        
        completionHandler(UIBackgroundFetchResult.newData)
    }
    func application(received remoteMessage: MessagingRemoteMessage) {
        print(" APNSData \(remoteMessage.appData)")
    }
    
    func messaging(_ messaging: Messaging, didRefreshRegistrationToken fcmToken: String) {
        print("Firebase registration token: \(fcmToken)")
    }
    private func application(application: UIApplication,
                             didRegisterForRemoteNotificationsWithDeviceToken deviceToken: NSData) {
        Messaging.messaging().apnsToken = deviceToken as Data
    }
    

}

