import Foundation

@objc public class InfoViewController: UIViewController {
  @objc public var onProfileButtonPressed: (() -> Void)? = nil
  
  @IBAction func onProfileButtonTap() {
    onProfileButtonPressed?()
  }
  
  @objc public static func create() -> InfoViewController {
    return InfoViewController(
      nibName: nil,
      bundle: Bundle(for: InfoViewController.self)
    )
  }
}
