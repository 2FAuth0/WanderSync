Participating members: Varun Giridhar, Justin Cruz

Varun Giridhar:
- Broke down calculateTotalPrice method into smaller methods for better readability and maintainability. It also follows the Single Responsibility Principle now.
- Order is tightly coupled with email sender as it directly calls static method of email sender, fixed this by injecting a private email sender in the method which we initialize in the constructor.
- Order.hasGiftCard() has knowledge of GiftCardItem; fixed this by introducing a method in Item interface to check if the item is a gift card.

Justin Cruz
- Refactored addItemsFromAnotherOrder to use the existing addItem method instead of directly accessing another Order instance's items list. This improves encapsulation and reduces redundancy.
